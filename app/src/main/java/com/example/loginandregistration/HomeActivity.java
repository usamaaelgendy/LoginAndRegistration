package com.example.loginandregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import Data.DatabaseHelper;
import Model.Register;

public class HomeActivity extends AppCompatActivity {
    TextView textViewUserName, textViewName, textViewPassword, textViewEmail, textViewLogout;

    Button btnEditDetails, btnChangePassword;

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    String textViewUsernameString;
    String textViewNameString;
    String textViewEmailString;
    String textViewPasswordString;
    int textViewID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textViewUserName = findViewById(R.id.textViewUserName);
        textViewName = findViewById(R.id.textViewName);
        textViewPassword = findViewById(R.id.textViewPassword);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewLogout = findViewById(R.id.textViewLogout);

        final Bundle b = getIntent().getExtras();


        textViewID = Integer.parseInt(b.getString("textViewId"));
        textViewUsernameString = b.getString("textViewUsername");
        textViewNameString = b.getString("textViewUsername");
        textViewEmailString = b.getString("textViewEmail");
        textViewPasswordString = b.getString("textViewPassword");


        Log.d("Data", String.valueOf(textViewID));
        Log.d("Data", "User name: " + textViewUsernameString);
        Log.d("Data", "name: " + textViewNameString);
        Log.d("Data", "Email: " + textViewEmailString);
        Log.d("Data", "password: " + textViewPasswordString);

        textViewUserName.setText("Hello " + textViewUsernameString);
        textViewName.setText(textViewNameString);
        textViewPassword.setText(textViewPasswordString);
        textViewEmail.setText(textViewEmailString);

        textViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });

        btnEditDetails = findViewById(R.id.btnEditDetails);
        btnEditDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUserDidals();
            }
        });

        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    public void changePassword() {
        alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        inflater = LayoutInflater.from(HomeActivity.this);
        final View view = inflater.inflate(R.layout.popup_password, null);
        final EditText editTextPasswordPopup = view.findViewById(R.id.editTextPasswordPopup);
        final EditText editTextConfPasswordPopup = view.findViewById(R.id.editTextConfPasswordPopup);

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        final Register register = new Register();
        register.setId(textViewID);
        register.setUserName(textViewNameString);
        register.setEmail(textViewEmailString);
        register.setPassword(textViewPasswordString);


        final DatabaseHelper db = new DatabaseHelper(HomeActivity.this);

        Button saveButtonPassword = view.findViewById(R.id.saveButtonPassword);

        saveButtonPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("check", editTextConfPasswordPopup.getText().toString());
                Log.d("check", editTextPasswordPopup.getText().toString());

                if (Integer.parseInt(editTextPasswordPopup.getText().toString()) == Integer.parseInt(editTextPasswordPopup.getText().toString()) ) {
                    register.setPassword(editTextPasswordPopup.getText().toString());
                    db.updateUser(register);
                    Toast.makeText(HomeActivity.this, "hi from if condition ", Toast.LENGTH_SHORT).show();
                    Snackbar.make(v, "Details Saved!", Snackbar.LENGTH_LONG).show();
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                } else{
                    Snackbar.make(view, "Don't save ", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(HomeActivity.this, "what else ", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });
    }


    public void editUserDidals() {
        alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        inflater = LayoutInflater.from(HomeActivity.this);
        final View view = inflater.inflate(R.layout.popup, null);
        final EditText editTextUsername = view.findViewById(R.id.editTextUsername);
        final EditText editTextEmail = view.findViewById(R.id.editTextEmail);

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        final Register register = new Register();
        register.setId(textViewID);
        register.setUserName(textViewNameString);
        register.setEmail(textViewEmailString);
        register.setPassword(textViewPasswordString);

        editTextUsername.setText(register.getUserName());
        editTextEmail.setText(register.getEmail());

        final DatabaseHelper db = new DatabaseHelper(HomeActivity.this);

        Button saveButton = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register.setUserName(editTextUsername.getText().toString());
                register.setEmail(editTextEmail.getText().toString());

                if (!editTextUsername.getText().toString().isEmpty()
                        && !editTextEmail.getText().toString().isEmpty()) {
                    db.updateUser(register);
                    Snackbar.make(v, "Details Saved!", Snackbar.LENGTH_LONG).show();
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                } else {
                    Snackbar.make(view, "Don't save ", Snackbar.LENGTH_LONG).show();
                }
                dialog.dismiss();

            }
        });
    }
}
