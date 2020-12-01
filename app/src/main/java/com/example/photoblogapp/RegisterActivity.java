package com.example.photoblogapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameRegister, emailRegister, passRegister, passconfirmRegister;
    private Button registerBtn, loginBtn;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameRegister= (EditText)findViewById(R.id.name_reg);
        emailRegister=(EditText)findViewById(R.id.email_reg);
        passRegister=(EditText)findViewById(R.id.password_reg);
        passconfirmRegister=(EditText)findViewById(R.id.passconfirm_reg);
        registerBtn= (Button)findViewById(R.id.reg_btn);
        loginBtn=(Button)findViewById(R.id.login2_btn);
        mAuth= FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent= new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName= nameRegister.getText().toString().trim();
                String userEmail= emailRegister.getText().toString().trim();
                String userPass= passRegister.getText().toString().trim();
                String userPassConf= passconfirmRegister.getText().toString().trim();

                if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPass) && !TextUtils.isEmpty(userPassConf) && !TextUtils.isEmpty(userEmail) ) {
                    if (passRegister.equals(passconfirmRegister)) {
                        mAuth.createUserWithEmailAndPassword(userEmail, userPass).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Intent mainIntent= new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(mainIntent);
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Create Accout: Failed", Toast.LENGTH_LONG).show();
                                        }

                                    }

                                }
                        );
                    }
                }
                    else{
                        Toast.makeText(RegisterActivity.this, "Password and Password Confirm are not match. Check again",Toast.LENGTH_LONG).show();
                    }

                }


            });
        }
}
