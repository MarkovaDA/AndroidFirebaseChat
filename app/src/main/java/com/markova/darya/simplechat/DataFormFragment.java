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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DataFormFragment extends Fragment implements View.OnClickListener {
    Boolean isLogin;

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

        isLogin = true;
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
                ? getResources().getString(R.string.signup)
                : getResources().getString(R.string.login);
        btnAction.setText(label);
    }


    private void signUp() {
        String email = (txtEmail.getText() != null) ? txtEmail.getText().toString() : "";
        String password =  (txtPassword.getText() != null) ? txtPassword.getText().toString() : "";

        if (email.isEmpty() || password.isEmpty()) {
            return;
        } else {
            final Activity parent = getActivity();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(parent, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            parent.startActivity(new Intent(getActivity(), MainActivity.class));
                            parent.finish();
                        } else {
                            Toast.makeText(parent,
                                    getResources().getString(R.string.failure_sign_up),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
        }
    }

    private void signIn() {

    }

    @Override
    public void onClick(View v) {
        if (isLogin) {
            signIn();
        } else {
            signUp();
        }
    }
}
