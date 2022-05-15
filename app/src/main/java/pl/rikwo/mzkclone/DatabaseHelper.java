package pl.rikwo.mzkclone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "user";
    private static final String COL1 = "name";
    private static final String COL2 = "lastname";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE " + TABLE_NAME + "("+ COL1 +" TEXT, "+ COL2 +" TEXT)";

        //OFFLINE DATA
        //String createTicketsTable = "CREATE TABLE tickets( _id INTEGER PRIMARY KEY, cancelled INTEGER, cancellation_date TEXT, cancel_possible INTEGER, name TEXT NOT NULL, price INTEGER, type TEXT NOT NULL, dailyCode INTEGER NOT NULL, dateBuy TEXT NOT NULL, dateStart INTEGER, dateEnd INTEGER, hash TEXT, qr TEXT, activationData TEXT, used INTEGER, activationMessage TEXT, messageFields TEXT,explanations TEXT, cemetery_ticket INTEGER, ticket_details_json BLOB);";
        String createTicketsTable = "CREATE TABLE tickets( _id INTEGER PRIMARY KEY, name TEXT NOT NULL, price INTEGER, type TEXT NOT NULL, dailyCode INTEGER NOT NULL, dateBuy TEXT NOT NULL, dateStart INTEGER, dateEnd INTEGER, qr TEXT, activationData TEXT, used INTEGER, ticket_details_json BLOB);";
        //db.execSQL(createTicketsTable);

        //(ideally, it should only be stored in the cloud)
        String createProfileTable = "CREATE TABLE profile(name TEXT NOT NULL, lastName TEXT, telephone INTEGER, email TEXT, pin INTEGER, personalId INTEGER);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
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

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
