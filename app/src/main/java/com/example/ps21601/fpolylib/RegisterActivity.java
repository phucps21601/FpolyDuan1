package com.example.ps21601.fpolylib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    Button btnDangky;
    EditText edtUser,edtPassword;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnDangky = findViewById(R.id.btnRegis);
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPass);

        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = edtUser.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(user, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this,"Đăng ký thành công",
                                                Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this,"Đăng ký không thành công",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
            }
        });
    }
}