package com.tr.indodaxdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    View accountFragment;
    TextView mProfile, mName, mFullname, mUsername, mEmail, mPassword;
    ImageView iProfle, iFullname, iUsername, iEmail, iPassword;
    String email, password;

    private FirebaseAuth fAuth;
    private DatabaseReference databaseReference;
    final String TAG = this.getClass().getName().toUpperCase();
    Map<String, String> userMap;
    FirebaseDatabase mDatabase;
    DatabaseReference fAccountsRootRef;


    public AccountFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        accountFragment = inflater.inflate(R.layout.fragment_account, container, false);

        mProfile = accountFragment.findViewById(R.id.profile);
        mName = accountFragment.findViewById(R.id.name_textView);
        mFullname = accountFragment.findViewById(R.id.fullname_textView);
        mUsername = accountFragment.findViewById(R.id.username_textView);
        mEmail = accountFragment.findViewById(R.id.email_textView);
        mPassword = accountFragment.findViewById(R.id.password_textView);
        iFullname = accountFragment.findViewById(R.id.fullname_imageView);
        iUsername = accountFragment.findViewById(R.id.username_imageView);
        iEmail = accountFragment.findViewById(R.id.email_imageView);
        iPassword = accountFragment.findViewById(R.id.password_imageView);

        fAuth = FirebaseAuth.getInstance();
        fAccountsRootRef = FirebaseDatabase.getInstance().getReference("accounts");

        return accountFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fUIDRef = fAccountsRootRef.child(UID);
//
//
//        fUIDRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()){
//                    Account account = ds.getValue(Account.class);
//
//                    mName.setText(account.getFull_name());
//                    mFullname.setText(account.getFull_name());
//                    mUsername.setText(account.getUsername());
//                    mEmail.setText(account.getEmail());
//                    mPassword.setText(account.getPassword());
//                }
//            }

        DatabaseReference fAccountName = fAccountsRootRef.child(UID).child("full_name");
        DatabaseReference fAccountFullname = fAccountsRootRef.child(UID).child("full_name");
        DatabaseReference fAccountUsername = fAccountsRootRef.child(UID).child("username");
        DatabaseReference fAccountEmail = fAccountsRootRef.child(UID).child("email");
        DatabaseReference fAccountPassword = fAccountsRootRef.child(UID).child("password");

        fAccountName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mName.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fAccountFullname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mFullname.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fAccountUsername.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsername.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fAccountEmail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mEmail.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fAccountPassword.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mPassword.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
    }
}

