package com.tr.indodaxdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tr.indodaxdemo.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AccountFragment extends Fragment {

    TextView mProfile, mName, mFullname, mUsername, mEmail, mPassword;
    ImageView iProfle, iFullname, iUsername, iEmail, iPassword;
    String email, password;
    static final String ACCOUNTS = "accounts";
    final String TAG = this.getClass().getName().toUpperCase();
    Map<String, String> userMap;
    FirebaseDatabase mDatabase;
    DatabaseReference databaseReference;
    String userId;
    List<Account> accounts = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra("email ");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference accountRef = rootRef.child(ACCOUNTS);

        Log.v("EMAILADD", accountRef.orderByChild("email").equalTo(email).toString());

        mProfile = v.findViewById(R.id.profile);
        mName = v.findViewById(R.id.name_textView);
        mFullname = v.findViewById(R.id.fullname_textView);
        mUsername = v.findViewById(R.id.username_textView);
        mEmail = v.findViewById(R.id.email_textView);
        mPassword = v.findViewById(R.id.password_textView);
        iFullname = v.findViewById(R.id.fullname_imageView);
        iUsername = v.findViewById(R.id.username_imageView);
        iEmail = v.findViewById(R.id.email_imageView);
        iPassword = v.findViewById(R.id.password_imageView);


        mDatabase = FirebaseDatabase.getInstance();
        accountRef = mDatabase.getReference("accounts");

        accountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("email").getValue().equals(email)) {
                        mName.setText(ds.child("full_name").getValue(String.class));
                        mFullname.setText(ds.child("full_name").getValue(String.class));
                        mUsername.setText(ds.child("username").getValue(String.class));
                        mEmail.setText(email);
                        mEmail.setText(ds.child("email").getValue(String.class));
                        mPassword.setText(ds.child("password").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDetailFragment profileDetailFragment = new ProfileDetailFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, profileDetailFragment);
                transaction.commit();
            }
        });

        return v;
    }
}

