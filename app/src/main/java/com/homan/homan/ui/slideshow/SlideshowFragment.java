package com.homan.homan.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.homan.homan.Models.Model;
import com.homan.homan.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel authenticationViewModel;

    private static int state;
    private static final int REGISTER = 0;
    private static final int LOGIN = 1;

    private TextView mTitleTextView;
    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mSubmitButton;
    private TextView mQuestionTextView;
    private TextView mClickHereTextView;
    private ProgressBar mProgressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        authenticationViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_authentication, container, false);

        initializeViewElements(root);
        initializeViewHandlers();

        return root;
    }

    private void initializeViewElements(View view) {
        mTitleTextView = view.findViewById(R.id.auth_frag_title_textview);
        mNameEditText = view.findViewById(R.id.auth_frag_name_edittext);
        mEmailEditText = view.findViewById(R.id.auth_frag_email_edittext);
        mPasswordEditText = view.findViewById(R.id.auth_frag_password_edittext);
        mSubmitButton = view.findViewById(R.id.auth_frag_submit_button);
        mQuestionTextView = view.findViewById(R.id.auth_frag_question_textview);
        mClickHereTextView = view.findViewById(R.id.auth_frag_clickhere_textview);
        mProgressBar = view.findViewById(R.id.auth_frag_title_progressbar);
    }

    private void initializeViewHandlers() {
        mSubmitButton.setOnClickListener(this::handleSubmit);
        mClickHereTextView.setOnClickListener(this::handleClickHere);
    }

    private void handleSubmit(View view) {
        runLoadingAnimation(true);
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        if (!validUserDetails(email, password)) {
            runLoadingAnimation(false);
            return;
        }
        switch (state) {
            case LOGIN:
                login(view, email, password);
                break;
            case REGISTER:
                register(view, email, password);
                break;
        }
    }

    private boolean validUserDetails(String email, String password) {
        boolean isValid = true;
        if (state == REGISTER && mNameEditText.getText().toString().isEmpty()) {
            mNameEditText.setError("Full Name is Required.");
            isValid = false;
        }
        if (email.isEmpty() || !email.contains("@")) {
            mEmailEditText.setError("Valid email is Required.");
            isValid = false;
        }
        if (password.isEmpty() || password.length() < 6) {
            mPasswordEditText.setError("At least 6 figures password is required.");
            isValid = false;
        }
        return isValid;
    }

    private void login(View view, String email, String password) {
        Model.users.login(email, password, isSuccessful -> {
            runLoadingAnimation(false);
            if (isSuccessful) {
                Toast.makeText(getContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
                updateUI();
                Navigation.findNavController(view).popBackStack();
            } else {
                Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register(View view, String email, String password) {
        Model.users.register(email, password, isSuccessful -> {
            if (isSuccessful) {
                Model.users.updateDisplayName(mNameEditText.getText().toString(), () -> {
                    updateUI();
                    runLoadingAnimation(false);
                    Toast.makeText(getContext(), "Successfully registered", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).popBackStack();
                });
            } else {
                runLoadingAnimation(false);
                Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI() {
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        Menu temp = navigationView.getMenu();
        MenuItem slideshow = temp.findItem(R.id.nav_slideshow);
        slideshow.setTitle("Log Out");
        View headerView = navigationView.getHeaderView(0);
        TextView userEmail = headerView.findViewById(R.id.nav_header_user_email);
        userEmail.setText(Model.users.getEmail());

        TextView userName = headerView.findViewById(R.id.nav_header_user_name);
        userName.setText(Model.users.getDisplayName());

        slideshow.setOnMenuItemClickListener(item -> {
            Model.users.signOut();
            slideshow.setTitle("Login");
            userEmail.setText("Not logged in");
            userName.setText("Anonymous");
            return false;
        });
    }

    private void handleClickHere(View view) {
        switch (state) {
            case REGISTER:
                state = LOGIN;
                mQuestionTextView.setText("Don't have an account? ");
                mTitleTextView.setText("Login");
                mNameEditText.setVisibility(View.GONE);
                break;
            case LOGIN:
                state = REGISTER;
                mQuestionTextView.setText("Already have an account? ");
                mTitleTextView.setText("Register");
                mNameEditText.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    protected void runLoadingAnimation(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        mNameEditText.setEnabled(!isLoading);
        mEmailEditText.setEnabled(!isLoading);
        mPasswordEditText.setEnabled(!isLoading);
        mSubmitButton.setEnabled(!isLoading);
        mClickHereTextView.setEnabled(!isLoading);
    }
}