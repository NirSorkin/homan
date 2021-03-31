package com.homan.homan.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.homan.homan.MainActivity;
import com.homan.homan.R;


public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";

    private TextView mGrushTitle;
    private EditText mEmail, mPassword;
    private ProgressBar mProgressBar;
    private Button mLoginBtn, mRegisterBtn;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        mGrushTitle = view.findViewById(R.id.grush_textView_login);
        mEmail = view.findViewById(R.id.editTexEmail_login);
        mPassword = view.findViewById(R.id.editTextTextPassword_login);
        mRegisterBtn = view.findViewById(R.id.button_register_login);
        mLoginBtn = view.findViewById(R.id.button_login_login);
        mProgressBar = view.findViewById(R.id.progressBar_login);


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (TextUtils.isEmpty(email)){
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

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getContext(), "Logged in", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Logged in");
                            startActivity(new Intent(getContext(), MainActivity.class));
                        } else {
                            Toast.makeText(getContext(), "Error"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.registerFragment);
            }
        });

        return view;
    }
}