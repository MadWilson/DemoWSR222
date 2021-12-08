package com.example.demowsr;

import static androidx.core.os.LocaleListCompat.create;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    Button SignIn, SignUp1;
    EditText edEmail1, edPassword1;
    SharedPreferences sPref;
    final String saveg = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        SignIn = findViewById(R.id.btnSignIn);
        SignUp1 =findViewById(R.id.btnSignUp1);
        edEmail1 =findViewById(R.id.edEmail1);
        edPassword1=findViewById(R.id.edPassword1);

        SignUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });


    }
    private void loginUser(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(edEmail1.getText().toString());
        loginRequest.setPassword(edPassword1.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getLogin().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                  LoginResponse loginResponse = response.body();

                    sPref = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor ed= sPref.edit();
                    int message = loginResponse.getToken();
                    ed.putString(saveg, String.valueOf(message));
                    ed.apply();

                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(SignInActivity.this, "Успешно вошли", Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    String message = "Ошибка";
                    Toast.makeText(SignInActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
               // ShortAlertDialogWindow("Некорректные данные");
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public void ShortAlertDialogWindow(String s){
        AlertDialog alertDialog = new AlertDialog.Builder(SignInActivity.this).setMessage(s).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).create();
        alertDialog.show();
    }

    }
