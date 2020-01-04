package com.example.loginandregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Data.DatabaseHelper;
import Model.User;

public class MainActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    TextView textViewRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        textViewRegister = findViewById(R.id.textViewRegister);
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();


                Boolean res = db.checkUser(email, password);
                User user = new User();
                Log.d("IDdetails", String.valueOf(user.getId()));


                if (res == true) {
                    Intent HomePage = new Intent(MainActivity.this, HomeActivity.class);
                    Bundle b = new Bundle();
                    b.putString("textViewEmail", editTextEmail.getText().toString());
                    b.putString("textViewPassword", editTextPassword.getText().toString());

                    String y = db.selectOneUserSendUserName(email, password);
                    int x = db.selectOneUserSendId(email,password);
                    Log.d("TAG" , "ID =  " + x);

                    b.putString("textViewUsername",y);
                    b.putString("textViewId", String.valueOf(x));

                    HomePage.putExtras(b);
                    startActivity(HomePage);
                } else {
                    Toast.makeText(MainActivity.this, "Login Error", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
