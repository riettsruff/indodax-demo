package com.tr.indodaxdemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tr.indodaxdemo.R;
import com.tr.indodaxdemo.service.Asset;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText mFullname, mUsername, mPassword, mEmail;
    Button mSignUpBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    DatabaseReference fRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullname = findViewById(R.id.fullName);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mEmail = findViewById(R.id.email);
        mSignUpBtn = findViewById(R.id.signUpBtn);
        mLoginBtn = findViewById(R.id.createText);

        fRootRef = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }
    }

    public void signUpBtnClicked(View v) {
        String fullname = mFullname.getText().toString().trim();
        String username = mUsername.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(fullname)) {
            mFullname.setError("Fullname can't be empty!");
            return;
        }

        if (TextUtils.isEmpty(username)){
            mUsername.setError("Username can't be empty!");
            return;
        }

        if (TextUtils.isEmpty(email)){
            mEmail.setError("Email can't be empty!");
            return;
        }

        if (TextUtils.isEmpty(password)){
            mPassword.setError("Password can't be empty!");
            return;
        }

        if(password.length() < 8) {
            mPassword.setError("Password minimal 8 characters long!");
            return;
        }

        registerUser(fullname, username, email, password);
    }

    private void registerUser(String fullname, String username, String email, String password) {
        progressBar.setVisibility(View.VISIBLE);

        fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("full_name", fullname);
                map.put("username", username);
                map.put("email", email);
                map.put("total_asset", Asset.get());
                map.put("wallets", null);
                map.put("transactions", null);

                fRootRef.child("accounts").child(fAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(RegisterActivity.this,
                              "Register Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void loginTextClicked(View v) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}