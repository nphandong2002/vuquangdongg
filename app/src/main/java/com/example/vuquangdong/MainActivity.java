package com.example.vuquangdong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner spinnerGender;
    EditText inputUsername,inputDescription,inputEmail;
    CheckBox ck;
    Button btRegister;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] genders = {"MALE", "FEMALE", "Other"};
        spinnerGender  = (Spinner) findViewById(R.id.spinner);
        inputUsername = (EditText) findViewById(R.id.inputUsername);
        inputDescription = (EditText) findViewById(R.id.inputDescription);
        inputEmail = (EditText) findViewById(R.id.inputEmal);
        ck = (CheckBox) findViewById(R.id.ck);
        db = AppDatabase.getAppDatabase(this);
        btRegister = (Button) findViewById(R.id.btRegister);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputUsername.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter username", Toast.LENGTH_LONG).show();
                    return;
                }
                if (inputEmail.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (inputDescription.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter desciption", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!ck.isChecked ()){
                    Toast.makeText(MainActivity.this,"Please agree rules", Toast.LENGTH_LONG).show();
                    return;
                }
                User user = new User();
                user.name = inputUsername.getText().toString();
                user.type = spinnerGender.getSelectedItem().toString();

                user.description = inputDescription.getText().toString();
                user.email = inputEmail.getText().toString();
                db.UserDao().insertUser(user);
                List<User> list = db.UserDao().getAllUser();
                Toast.makeText(MainActivity.this,"Have " + list.size() + " custom feedback", Toast.LENGTH_LONG).show();
                inputUsername.setText("");
                inputEmail.setText("");
                inputDescription.setText("");
                spinnerGender.setSelection(0);
            }
        });
    }
}