package pl.rikwo.mzkclone;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TicketActivity extends AppCompatActivity {

    TextView id, name, price, dateBuy, activationData, userName, userSurname;
    String ticketId;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        mDatabaseHelper = new DatabaseHelper(this);

        name = (TextView) findViewById(R.id.ticketName);
        price = (TextView) findViewById(R.id.ticketPrice);
        dateBuy = (TextView) findViewById(R.id.ticketDateBuy);
        activationData = (TextView) findViewById(R.id.ticketActivationData);
        id = (TextView) findViewById(R.id.ticketId);
        userName = (TextView) findViewById(R.id.ticketUserName);
        userSurname = (TextView) findViewById(R.id.ticketUserSurname);


        try{
            String intentId = getIntent().getStringExtra("id");


            Cursor data = mDatabaseHelper.getTicket(intentId);

            while(data.moveToNext()) {
                name.setText(data.getString(data.getColumnIndex("name")));
                price.setText(data.getString(data.getColumnIndex("price")));
                dateBuy.setText(data.getString(data.getColumnIndex("dateBuy")));
                activationData.setText(data.getString(data.getColumnIndex("activationData")));
                id.setText(data.getString(data.getColumnIndex("_id")));
                ticketId = data.getString(data.getColumnIndex("_id"));
            }

            data = mDatabaseHelper.getUserData();
            while(data.moveToNext()) {
                userName.setText(data.getString(data.getColumnIndex("name")));
                userSurname.setText(data.getString(data.getColumnIndex("surname")));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        ((Button) findViewById(R.id.closeTicketActivity)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((Button) findViewById(R.id.setAsUsed)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDatabaseHelper.setTicketAsUsed(ticketId)){
                    Toast.makeText(v.getContext(), "The ticket has been set as used", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(v.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}