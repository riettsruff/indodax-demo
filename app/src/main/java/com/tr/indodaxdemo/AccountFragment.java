package com.tr.indodaxdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Map;


public class AccountFragment extends Fragment {

    private TextView mProfile, mName, mFullname, mUsername, mEmail, mPassword;
    private ImageView iProfle, iFullname, iUsername, iEmail, iPassword;
    private String email, password;
    private final String TAG = this.getClass().getName().toUpperCase();
    private Map<String, String> userMap;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private static final String ACCOUNTS = "accounts";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account,container,false);

        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra("email");

        mProfile = v.findViewById(R.id.profile);
        mName = v.findViewById(R.id.name_textView);
        mFullname = v.findViewById(R.id.fullname_textView);
        mUsername = v.findViewById(R.id.username_textView);
        mEmail = v.findViewById(R.id.email_textView);
        mPassword = v.findViewById(R.id.password_textView);
        iProfle = v.findViewById(R.id.profile_imageView);
        iFullname = v.findViewById(R.id.fullname_imageView);
        iUsername = v.findViewById(R.id.username_imageView);
        iEmail = v.findViewById(R.id.email_imageView);
        iPassword = v.findViewById(R.id.password_imageView);


        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(ACCOUNTS);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    if (ds.child("email").getValue().equals(email)){
                        mName.setText(ds.child("full_name").getValue(String.class));
                        mFullname.setText(ds.child("full_name").getValue(String.class));
                        mUsername.setText(ds.child("username").getValue(String.class));
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
                transaction.replace(R.id.fragment_container,profileDetailFragment);
                transaction.commit();
            }
        });

        return v;
    }

}


