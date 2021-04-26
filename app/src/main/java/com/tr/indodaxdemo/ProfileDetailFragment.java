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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tr.indodaxdemo.model.Account;

import java.util.HashMap;
import java.util.Map;

public class ProfileDetailFragment extends Fragment {
    private static final String TAG = ProfileDetailFragment.class.getSimpleName();
    EditText gFullName, gUsername, gEmail, gPassword;
    private Button gUpdateBtn;
    private String userId;
    private FirebaseAuth fAuth;
    DatabaseReference fAccountsRootRef;
//    private DatabaseReference mFirebaseDatabase;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_detail, container, false);

        gFullName = v.findViewById(R.id.fullName);
        gUsername = v.findViewById(R.id.username);
        gEmail = v.findViewById(R.id.email);
        gPassword = v.findViewById(R.id.password);
        gUpdateBtn = v.findViewById(R.id.updateBtn);

        fAuth = FirebaseAuth.getInstance();
        fAccountsRootRef = FirebaseDatabase.getInstance().getReference("accounts");

        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        fAccountsRootRef = FirebaseDatabase.getInstance().getReference().child("accounts");

        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fUIDRef = fAccountsRootRef.child(UID);

        fUIDRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    Map<String,Object> map = (Map<String, Object>) dataSnapshot.getValue();
//
//                    Object id = map.get("id");
//                    String full_name = (String) map.get("full_name");
//                }
//                Log.e(TAG, "app title updated");
//                String appTitle = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to read app title value.", databaseError.toException());
            }
        });

        gUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UID = fAuth.getCurrentUser().getUid();
                String full_name = gFullName.getText().toString();
                String username = gUsername.getText().toString();
                String email = gEmail.getText().toString();
                String password = gPassword.getText().toString();

//                HashMap hashMap = new HashMap();
//                hashMap.put("full_name", full_name);
//                hashMap.put("username", username);
//                hashMap.put("email", email);
//                hashMap.put("password", password);
//
//                fAccountsRootRef.child(UID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
//                    @Override
//                    public void onSuccess(Object o) {
////                        Toast.makeText(this, "your data is updated successfully!", Toast.LENGTH_SHORT).show();
//                    }
//                });


                if (TextUtils.isEmpty(UID)) {
                    createUser(full_name, username, email, password);
                } else {
                    updateUser(full_name, username, email, password);
                }
            }
        });
        toggleButton();
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fUIDRef = fAccountsRootRef.child(UID);
        DatabaseReference fAccountFullname = fAccountsRootRef.child(UID).child("full_name");
        DatabaseReference fAccountUsername = fAccountsRootRef.child(UID).child("username");
        DatabaseReference fAccountEmail = fAccountsRootRef.child(UID).child("email");
        DatabaseReference fAccountPassword = fAccountsRootRef.child(UID).child("password");

        fAccountFullname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gFullName.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fAccountUsername.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gUsername.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fAccountEmail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gEmail.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fAccountPassword.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gPassword.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void toggleButton() {
        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fUIDRef = fAccountsRootRef.child(UID);
        if (TextUtils.isEmpty(UID)) {
            gUpdateBtn.setText("Update");
        }
    }

    private void createUser(String full_name, String username, String email, String password) {
        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fUIDRef = fAccountsRootRef.child(UID);
        if (TextUtils.isEmpty(UID)) {
            UID = fAccountsRootRef.push().getKey();
        }

        Account account = new Account(full_name, username, email, password);

        fAccountsRootRef.child(UID).setValue(account);

        addUserChangeListener();
    }

    private void addUserChangeListener() {
        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fUIDRef = fAccountsRootRef.child(UID);
        fUIDRef.addValueEventListener(new ValueEventListener() {
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
        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fUIDRef = fAccountsRootRef.child(UID);
        if (!TextUtils.isEmpty(full_name))
            fAccountsRootRef.child(UID).child("full_name").setValue(full_name);

        if (!TextUtils.isEmpty(username))
            fAccountsRootRef.child(UID).child("username").setValue(username);

        if (!TextUtils.isEmpty(email))
            fAccountsRootRef.child(UID).child("email").setValue(email);

        if (!TextUtils.isEmpty(password))
            fAccountsRootRef.child(UID).child("password").setValue(password);
    }

}
