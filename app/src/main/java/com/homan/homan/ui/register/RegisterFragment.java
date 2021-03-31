package com.homan.homan.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.homan.homan.MainActivity;
import com.homan.homan.R;

public class RegisterFragment extends Fragment {

    private static final String TAG = "RegisterFragment";

    private TextView mGrushTitle;
    private EditText mFullName, mEmail, mPassword;
    private ProgressBar mProgressBar;
    private Button mRegisterBtn;

    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mAuth = FirebaseAuth.getInstance();
        mGrushTitle = view.findViewById(R.id.grush_textView_register);
        mFullName = view.findViewById(R.id.editTextTextFullName_register);
        mEmail = view.findViewById(R.id.editTextTextEmail_register);
        mPassword = view.findViewById(R.id.editTextTextPassword_register);
        mRegisterBtn = view.findViewById(R.id.button_register_register);
        mProgressBar = view.findViewById(R.id.progressBar_register);


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String name = mFullName.getText().toString().trim();


                if (password.isEmpty()){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (email.isEmpty()){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (password.length() < 6){
                    mPassword.setError("Password must be at least 6 characters.");
                    return;
                }

                if (!email.contains("@")){
                    mEmail.setError("Invalid email.");
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            currentUser.updateProfile(profileUpdates);
                            startActivity(new Intent(getContext(), MainActivity.class));
                        } else {
                            Toast.makeText(getContext(), "Error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }


}