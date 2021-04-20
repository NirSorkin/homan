package com.homan.homan.Models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserModel {

    public final static UserModel instance = new UserModel();

    private UserModel() { }

    public interface LoginListener {
        void onComplete(boolean isSuccessful);
    }
    public void login(String email, String password, LoginListener listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }

    public interface RegisterListener {
        void onComplete(boolean isSuccessful);
    }
    public void register(String email, String password, RegisterListener listener) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }

    public interface UpdateDisplayNameListener {
        void onComplete();
    }
    public void updateDisplayName(String fullName, UpdateDisplayNameListener listener) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .build();
        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(task1 -> {
            listener.onComplete();
        });
    }

    public boolean isLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public String getEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

    public String getDisplayName() {
        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }
}