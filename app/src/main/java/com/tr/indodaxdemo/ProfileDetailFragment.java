package com.tr.indodaxdemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tr.indodaxdemo.model.Account;

public class ProfileDetailFragment extends Fragment {
    private static final String TAG = ProfileDetailFragment.class.getSimpleName();
    private EditText gFullName, gUsername, gEmail, gPassword;
    private Button gUpdateBtn;
    private DatabaseReference mFirebaseDatabase;

    private String userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_detail,container,false);

        Intent intent = getActivity().getIntent();


        gFullName = v.findViewById(R.id.fullName);
        gUsername = v.findViewById(R.id.username);
        gEmail = v.findViewById(R.id.email);
        gPassword = v.findViewById(R.id.password);
        gUpdateBtn = v.findViewById(R.id.updateBtn);



        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("accounts");
        mFirebaseInstance.getReference("app_title").setValue("Account Database");

        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "app title updated");
                String appTitle = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to read app title value.", databaseError.toException());
            }
        });

        gUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String full_name = gFullName.getText().toString();
                String username = gUsername.getText().toString();
                String email = gEmail.getText().toString();
                String password = gPassword.getText().toString();

                if (TextUtils.isEmpty(userId)) {
                    createUser(full_name, username, email, password);
                } else {
                    updateUser(full_name, username, email, password);
                }
            }
        });
        toggleButton();
        return v;
    }

    private void toggleButton() {
        if (TextUtils.isEmpty(userId)) {
            gUpdateBtn.setText("Update");
        }
    }

    private void createUser(String full_name, String username, String email, String password) {
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        Account account = new Account(full_name, username, email, password);

        mFirebaseDatabase.child(userId).setValue(account);

        addUserChangeListener();
    }

    private void addUserChangeListener() {
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Account account = dataSnapshot.getValue(Account.class);
                if (account == null) {
                    Log.e(TAG, "Account data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + account.getFull_name() + ", " + account.getUsername()+ ", " + account.getEmail()+ ", " + account.getPassword());

                gFullName.setText(account.getFull_name());
                gUsername.setText(account.getUsername());
                gEmail.setText(account.getEmail());
                gPassword.setText(account.getPassword());

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(String full_name, String username, String email, String password) {
        if (!TextUtils.isEmpty(full_name))
            mFirebaseDatabase.child(userId).child("full_name").setValue(full_name);

        if (!TextUtils.isEmpty(username))
            mFirebaseDatabase.child(userId).child("username").setValue(username);

        if (!TextUtils.isEmpty(email))
            mFirebaseDatabase.child(userId).child("email").setValue(email);

        if (!TextUtils.isEmpty(password))
            mFirebaseDatabase.child(userId).child("password").setValue(password);
    }

}
