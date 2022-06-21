package pl.rikwo.mzkclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    TextView inputName, inputSurname, inputPhone, inputEmail, inputEmailConfirm, inputPassword, inputPasswordConfirm, inputPersonalId;

    CheckBox checkBox;

    DatabaseHelper mDatabaseHelper;

    boolean declare = false;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mDatabaseHelper = new DatabaseHelper(this);

        inputName = findViewById(R.id.name);
        inputSurname = findViewById(R.id.surname);
        inputPhone = findViewById(R.id.phone);
        inputEmail = findViewById(R.id.email);
        inputEmailConfirm = findViewById(R.id.emailConfirm);
        inputPassword = findViewById(R.id.passwordNumber);
        inputPasswordConfirm = findViewById(R.id.passwordNumberConfirm);
        inputPersonalId = findViewById(R.id.personalId);

        checkBox = findViewById(R.id.checkBox2);


        try (Cursor data = mDatabaseHelper.getUserData()) {
            while (data.moveToNext()) {
                //get the calue from the database in column 0 and 1
                //then add it to the ArrayList
                inputName.setText(data.getString(data.getColumnIndex("name")));
                inputSurname.setText(data.getString(data.getColumnIndex("lastName")));
                inputPhone.setText(data.getString(data.getColumnIndex("telephone")));

                inputEmail.setText(data.getString(data.getColumnIndex("email")));
                inputEmailConfirm.setText(data.getString(data.getColumnIndex("email")));

                //inputPassword.setText(data.getString(data.getColumnIndex("pin")));
                //inputPasswordConfirm.setText(data.getString(data.getColumnIndex("pin")));

                inputPersonalId.setText(data.getString(data.getColumnIndex("personalId")));

            }
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declare = !declare;
            }
        });

        ((Button) findViewById(R.id.update)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!inputName.getText().toString().equals("") && !inputSurname.getText().toString().equals("")
                        && !inputPhone.getText().toString().equals("") && !inputEmail.getText().toString().equals("")
                        && !inputEmailConfirm.getText().toString().equals("") && !inputPassword.getText().toString().equals("")
                        && !inputPasswordConfirm.getText().toString().equals("") && !inputPersonalId.getText().toString().equals("")
                        && declare) {
                    if (mDatabaseHelper.updateProfile(inputPhone.getText().toString(),
                            inputEmail.getText().toString(), inputPassword.getText().toString(),
                            inputPersonalId.getText().toString())) {

                        /* temporary fix to not refreshing */
                        Intent intent = new Intent(v.getContext(), HomeActivity.class);
                        intent.putExtra("frag", "nav_profile");
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "You have not completed all the required data.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* temporary fix to not refreshing */
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                intent.putExtra("frag", "nav_profile");
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        /* temporary fix to not refreshing */
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("frag", "nav_profile");
        startActivity(intent);
        finish();
    }
}