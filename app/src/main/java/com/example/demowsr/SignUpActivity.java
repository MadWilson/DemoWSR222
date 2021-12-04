package com.example.demowsr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
    Button SignUp, Acc;
    EditText edEmail, edPassword, edAgainPassword, edFirstName, edLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        SignUp=findViewById(R.id.btnSignUp);
        Acc=findViewById(R.id.btnAcc);
        edEmail=findViewById(R.id.edEmail);
        edPassword=findViewById(R.id.edPassword);
        edAgainPassword=findViewById(R.id.edAgainPassword);
        edFirstName=findViewById(R.id.edFirstName);
        edLastName=findViewById(R.id.edLastName);

        Acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edEmail.getText().toString())||
                TextUtils.isEmpty(edPassword.getText().toString())||
                TextUtils.isEmpty(edAgainPassword.getText().toString())||
                TextUtils.isEmpty(edFirstName.getText().toString())||
                TextUtils.isEmpty(edLastName.getText().toString()))
                {
                    ShowAlertDialogWindow("Заполните пустые поля");
                }
                else if(!edPassword.getText().toString().equals(edAgainPassword.getText().toString()))
                {
                    ShowAlertDialogWindow("Пароли не совпадают");
                }
                /*else if(!emailValid(edEmail.getText().toString())){
                    ShowAlertDialogWindow("Неправильный email");
                }*/
                else {
                    registerUser();
                }
            }

            private boolean emailValid(String email) {
                Pattern emailPattern = Pattern.compile("a-z.+@[a-z]+\\.[a-z]+");
                Matcher emailMatcher = emailPattern.matcher(email);
                return emailMatcher.matches();
            }


            public void registerUser() {
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
                            String message = "Все работает";
                            Toast.makeText(SignUpActivity.this,message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            String message = "Ошибка";
                            Toast.makeText(SignUpActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable throwable) {
                        String message = "Успех";
                        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));

                    }
                });
            }

            public void ShowAlertDialogWindow(String s) {
                AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).setMessage(s).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create();
                alertDialog.show();
            }
        });

    }
}