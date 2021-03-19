package com.homan.homan.ui.logout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.homan.homan.R;


public class LogoutFragment extends Fragment {

    private Button mLogoutBtn;
    private TextView mEmail, mFullName;
    String currentUserEmail;
    String currentUserDisplayName;
    private ProgressBar mProgressBar;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_logout, container, false);

        mLogoutBtn = view.findViewById(R.id.button_logout);
        mEmail = view.findViewById(R.id.textView_email_logout);
        mFullName = view.findViewById(R.id.textView_fullName_logout);
        mProgressBar = view.findViewById(R.id.progressBar_logout);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            currentUserEmail = currentUser.getEmail().toString();
            currentUserDisplayName = currentUser.getDisplayName().toString();
            mFullName.setText(currentUserDisplayName);
            mEmail.setText(currentUserEmail);
        }
        else {
            mFullName.setText("");
            mEmail.setText("");
        }


        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                if (currentUser != null) {
                    mAuth.signOut();
                    Toast.makeText(getContext(), currentUserDisplayName + " Logged out", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.loginFragment);

                } else {
                    Toast.makeText(getContext(), "Unable to logout, there is no logged in user", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }


}