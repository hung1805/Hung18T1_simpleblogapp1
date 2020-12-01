package com.example.photoblogapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passText;
    private Button loginBtn;
    private Button registerBtn;
    private FirebaseAuth mAuth;

    private ProgressBar loginProgess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText=(EditText)findViewById(R.id.email);
        passText= (EditText)findViewById(R.id.password);
        loginBtn= (Button)findViewById(R.id.login_btn);
        registerBtn=(Button)findViewById(R.id.login2_btn);
        loginProgess= (ProgressBar) findViewById(R.id.login_progess);
        mAuth= FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginEmail= emailText.getText().toString();
                String loginPass= passText.getText().toString();
                if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)){
                    loginProgess.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(loginEmail, loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendToMain();

                            } else {
                                String errMess = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error: " + errMess, Toast.LENGTH_LONG).show();

                            }
                            loginProgess.setVisibility(View.VISIBLE);

                        }
                    });

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser= mAuth.getCurrentUser();
        if (currentUser!= null){
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent mainIntent= new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}