package pl.rikwo.mzkclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TicketActivity extends AppCompatActivity {

    TextView id, name, price, dateBuy, activationData, userName, userSurname, dailyCode;
    String ticketId;
    DatabaseHelper mDatabaseHelper;

    int used = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        mDatabaseHelper = new DatabaseHelper(this);

        dailyCode = (TextView) findViewById(R.id.ticketDailyCode);

        name = (TextView) findViewById(R.id.ticketName);
        price = (TextView) findViewById(R.id.ticketPrice);
        dateBuy = (TextView) findViewById(R.id.ticketDateBuy);
        activationData = (TextView) findViewById(R.id.ticketActivationData);
        id = (TextView) findViewById(R.id.ticketId);

        userName = (TextView) findViewById(R.id.ticketUserName);
        userSurname = (TextView) findViewById(R.id.ticketUserSurname);


        //DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());

        try{
            String intentId = getIntent().getStringExtra("id");


            Cursor data = mDatabaseHelper.getTicket(intentId);

            while(data.moveToNext()) {

                dailyCode.setText(data.getString(data.getColumnIndex("dailyCode")));
                name.setText(data.getString(data.getColumnIndex("name")));
                price.setText(data.getString(data.getColumnIndex("price")));


                dateBuy.setText(getDate(data.getString(data.getColumnIndex("dateBuy"))));
                activationData.setText(data.getString(data.getColumnIndex("activationData")));
                id.setText(data.getString(data.getColumnIndex("_id")));
                ticketId = data.getString(data.getColumnIndex("_id"));

                used = data.getInt(data.getColumnIndex("used"));
            }

            data = mDatabaseHelper.getUserData();
            while(data.moveToNext()) {
                userName.setText(data.getString(data.getColumnIndex("name")));
                userSurname.setText(data.getString(data.getColumnIndex("lastName")));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        if(used == 0){
            ((Button) findViewById(R.id.setAsUsed)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mDatabaseHelper.setTicketAsUsed(ticketId)){
                        Toast.makeText(v.getContext(), "The ticket has been set as used", Toast.LENGTH_SHORT).show();

                        /* temporary fix to not refreshing */
                        Intent intent = new Intent(v.getContext(), HomeActivity.class);
                        intent.putExtra("frag", "nav_my_tickets");
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(v.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            View b = ((View) findViewById(R.id.setAsUsed));
            ((ViewManager)b.getParent()).removeView(b);



            Button b2 = (Button) findViewById(R.id.closeTicketActivity);

            ConstraintLayout layout = ((ConstraintLayout) findViewById(R.id.layoutOfTicket));
            ConstraintSet set = new ConstraintSet();

            set.constrainWidth(b2.getId(), 0);
            set.constrainHeight(b2.getId(), ConstraintSet.WRAP_CONTENT);

            set.connect(b2.getId(), ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.START);
            set.connect(b2.getId(), ConstraintSet.END,
                    ConstraintSet.PARENT_ID, ConstraintSet.END);
            set.connect(b2.getId(), ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
            set.applyTo(layout);
        }


        ((Button) findViewById(R.id.closeTicketActivity)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* temporary fix to not refreshing */
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                intent.putExtra("frag", "nav_my_tickets");
                startActivity(intent);
                finish();
            }
        });

    }

    private String getDate(String timeStamp){

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            //Long.
            Date netDate = (new Date(Long.parseLong(timeStamp)));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }
}