package com.mr.testauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaCodec;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PatternMatcher;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth auth;
    ProgressDialog dialog;
    DatabaseReference reference;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private Pattern emailPattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUIReg();
    }

    private void setUIReg() {
        getSupportActionBar().hide();

        dialog = new ProgressDialog(RegisterActivity.this);

        TextView tvMoveLogin = (TextView)findViewById(R.id.label_loginDisini);
        tvMoveLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        /* Set Firebase */

        auth = FirebaseAuth.getInstance();

        Button btnSignUp = (Button)findViewById(R.id.btn_signUp);
        EditText inputEmailReg = (EditText)findViewById(R.id.et_email_reg);
        EditText inputPassReg = (EditText)findViewById(R.id.et_password_reg);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmailReg.getText().toString().trim();
                String password = inputPassReg.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Masukan E-Mail dengan benar !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!email.matches(emailPattern.pattern())){
                    Toast.makeText(getApplicationContext(), "Harus mengandung '@' dan berformat E-mail !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Masukan Password dengan benar !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password terlalu pendek, Min.6 Karakter !", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialog.setMessage("Tunggu ya");
                dialog.show();
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                dialog.dismiss();

                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Berhasil membuat akun !", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(), "GAGAL !\nE-mail sudah ada !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}