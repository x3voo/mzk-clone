package pl.rikwo.mzkclone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "user";
    private static final String COL1 = "name";
    private static final String COL2 = "lastname";

    private static final String TABLE_PROFILE = "profile";
    private static final String P_NAME = "name";
    private static final String P_LASTNAME = "lastName";
    private static final String P_PHONE = "telephone";
    private static final String P_EMAIL = "email";
    private static final String P_PIN = "pin";
    private static final String P_PERSONALID = "personalId";

    private static final String TABLE_TICKETS = "tickets";
    private static final String _ID = "_id";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String TYPE = "type";
    private static final String DAILY_CODE = "dailyCode";
    private static final String DATE_BUY = "dateBuy";
    private static final String DATE_START = "dateStart";
    private static final String DATE_END = "dateEnd";
    private static final String QR = "qr";
    private static final String ACTIVATION_DATA = "activationData";
    private static final String USED = "used";
    private static final String TICKER_DETAILS_JSON = "ticket_details_json";

    public DatabaseHelper(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Log.d(TAG, "onCreate");

        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("+ COL1 +" TEXT, "+ COL2 +" TEXT)";
        db.execSQL(createTable);

        //OFFLINE DATA
        //String createTicketsTable = "CREATE TABLE tickets( _id INTEGER PRIMARY KEY, cancelled INTEGER, cancellation_date TEXT, cancel_possible INTEGER, name TEXT NOT NULL, price INTEGER, type TEXT NOT NULL, dailyCode INTEGER NOT NULL, dateBuy TEXT NOT NULL, dateStart INTEGER, dateEnd INTEGER, hash TEXT, qr TEXT, activationData TEXT, used INTEGER, activationMessage TEXT, messageFields TEXT,explanations TEXT, cemetery_ticket INTEGER, ticket_details_json BLOB);";
        String createTicketsTable = "CREATE TABLE IF NOT EXISTS tickets( _id INTEGER PRIMARY KEY, name TEXT NOT NULL, price INTEGER, type TEXT NOT NULL, dailyCode INTEGER NOT NULL, dateBuy TEXT NOT NULL, dateStart INTEGER, dateEnd INTEGER, qr TEXT, activationData TEXT, used INTEGER, ticket_details_json BLOB);";
        db.execSQL(createTicketsTable);

        //(ideally, it should only be stored in the cloud)
        String createProfileTable = "CREATE TABLE IF NOT EXISTS profile(name TEXT NOT NULL, lastName TEXT, telephone INTEGER, email TEXT, pin INTEGER, personalId INTEGER);";

        db.execSQL(createProfileTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        Log.d(TAG, "onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
        onCreate(db);
    }

    public boolean addData(String item, String item2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, item);
        contentValues.put(COL2, item2);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data is inserted incorrectly "db" will return -1
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean addTicket(String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if(type == "regular"){
            contentValues.put(NAME, "Bilet miejski zwykły jednorazowy 30min");
            contentValues.put(PRICE, 290);
            contentValues.put(TYPE, "REGULAR");
        } else if(type == "discount"){
            contentValues.put(NAME, "Bilet miejski ulgowy jednorazowy 30min");
            contentValues.put(PRICE, 145);
            contentValues.put(TYPE, "DISCOUNT");
        }

        contentValues.put(DAILY_CODE, 1039);
        contentValues.put(DATE_BUY, System.currentTimeMillis());
        contentValues.put(DATE_START, 0);
        contentValues.put(DATE_END, 0);
        contentValues.put(QR, "qr");
        //contentValues.put(ACTIVATION_DATA, null);
        contentValues.put(USED, 0);
        //contentValues.put(TICKER_DETAILS_JSON, );

        long result = db.insert(TABLE_TICKETS, null, contentValues);


        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean newUser(String name, String surname, String phone, String email, String password, String personalId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(P_PERSONALID, personalId);
        contentValues.put(P_EMAIL, email);
        contentValues.put(P_NAME, name);
        contentValues.put(P_PIN, password);
        contentValues.put(P_PHONE, phone);
        contentValues.put(P_LASTNAME, surname);

        db.execSQL("DELETE FROM " + TABLE_PROFILE);
        db.execSQL("DELETE FROM " + TABLE_TICKETS);
        onCreate(db);


        Log.d(TAG, "newUser: Adding new user");

        long result = db.insert(TABLE_PROFILE, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getUserData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PROFILE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getTickets(String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        //INACTIVE SELECT * FROM tickets WHERE used = 0 AND activationData IS NULL

        //ACTIVE SELECT * FROM tickets WHERE used = 0 AND activationData IS NOT NULL

        //USED SELECT * FROM tickets WHERE used = 1

        String query = "SELECT * FROM " + TABLE_TICKETS;

        if(status == "inactive"){
            query = "SELECT * FROM " + TABLE_TICKETS + " WHERE used = 0 AND activationData IS NOT NULL";
        }else if(status == "active"){
            query = "SELECT * FROM " + TABLE_TICKETS + " WHERE used = 0 AND activationData IS NULL";
        }else if(status == "used"){
            query = "SELECT * FROM " + TABLE_TICKETS + " WHERE used = 1";
        }

        Cursor data = db.rawQuery(query, null);
        if (data.getCount() == 0){
            Log.d(TAG, "Error");
        } else {
            Log.d(TAG, "Pog");
        }
        return data;
    }

    public boolean login(String inputPin) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT 1 FROM " + TABLE_PROFILE + " WHERE pin = ?";
        Cursor data = db.rawQuery(query, new String[] {inputPin});
        if(data.getCount() == 0){
            return false;
        } else {
            return true;
        }
    }

    public boolean changePin(String pin){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(P_PIN, pin);

        long result = db.update(TABLE_PROFILE, contentValues, null, null);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
