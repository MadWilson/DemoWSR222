package com.example.demowsr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    Button signIn, signUp, signSwitch;
    EditText edEmail, edPassword, edFirstName, edLastName, edPassConfirmed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signIn = findViewById(R.id.btnSignIn);
        signUp = findViewById(R.id.btnSignUp);
        signSwitch = findViewById(R.id.btnAcc);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        edFirstName = findViewById(R.id.edFirstName);
        edLastName = findViewById(R.id.edLastName);
        edPassConfirmed = findViewById(R.id.edAgainPassword);


        signSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edEmail.getText().toString())||TextUtils.isEmpty(edPassword.getText().toString())||TextUtils.isEmpty(edFirstName.getText().toString())||TextUtils.isEmpty(edLastName.getText().toString())){
                    String message = "FWAFAWF";
                    ShowAlertDialogWindow(message);
                }
                else {

                    registerUser();
                }
            }
        });
    }


    public void ShowAlertDialogWindow(String s){
        android.app.AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).setMessage(s).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();
        alertDialog.show();
    }


    public void onClickSignIn (View view)
    {
        Intent signUp = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(signUp);
    }


    public void registerUser(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(edEmail.getText().toString());
        registerRequest.setPassword(edPassword.getText().toString());
        registerRequest.setFirstname(edFirstName.getText().toString());
        registerRequest.setLastname(edLastName.getText().toString());
        Call<RegisterResponse> registerResponseCall = ApiClient.getRegister().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()){
                    String message = "Все ок...";
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();

                    finish();
                }else {

                    String message = "Что-то пошло не так";
                    Toast.makeText(SignUpActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message = "Регистрация не прошла";
                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));

            }
        });
    }
    }

