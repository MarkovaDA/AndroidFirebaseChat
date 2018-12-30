package com.markova.darya.simplechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class DataFormFragment extends Fragment implements View.OnClickListener {
    private boolean isLogin;
    private Button btnAction;
    private TextView txtEmail;
    private TextView txtPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.form_data_fragment,
                container,
                false);


        isLogin = (getArguments() != null) ? getArguments().getBoolean("isLogin") : true;

        btnAction = view.findViewById(R.id.btn_user_action);
        txtEmail = view.findViewById(R.id.txt_user_email);
        txtPassword = view.findViewById(R.id.txt_user_password);
        firebaseAuth = FirebaseAuth.getInstance();

        btnAction.setOnClickListener(this);

        setButtonLabel();
        return view;
    }

    private void setButtonLabel() {
        String label = isLogin
                ? getResources().getString(R.string.login)
                : getResources().getString(R.string.signup);

        btnAction.setText(label);
    }


    private void signUp() {
        String email = getEmail();
        String password = getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            return;
        }

        final Activity context = getActivity();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        context.startActivity(new Intent(getActivity(), MainActivity.class));
                        context.finish();
                    }
                }
            })
            .addOnFailureListener(context, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(context,
                            exception.getMessage().toString(),
                            Toast.LENGTH_LONG)
                            .show();
                }
            });
    }

    private void signIn() {
        String email = getEmail();
        String password = getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            return;
        }

        final Activity context = getActivity();

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        context.startActivity(new Intent(getActivity(), MainActivity.class));
                        context.finish();
                    }
                }
            })
            .addOnFailureListener(context, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(context,
                            exception.getMessage().toString(),
                            Toast.LENGTH_LONG)
                            .show();
                }
            });
    }

    @Override
    public void onClick(View v) {
        if (isLogin) {
            signIn();
        } else {
            signUp();
        }
    }

    private String getEmail() {
        return (txtEmail.getText() != null) ? txtEmail.getText().toString() : "";
    }

    private String getPassword() {
        return (txtPassword.getText() != null) ? txtPassword.getText().toString() : "";
    }
}
