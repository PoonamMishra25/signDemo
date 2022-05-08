package com.example.poonamtiwarigreenflag;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {
    TextInputLayout til_email, til_password, til_rePassword;
    boolean emailvalid, passwordvalid, repasswordvalid;
    // TextInputEditText edMail;
    ImageView backBtn;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    Button btn_confirm;
    TextView emailHighligther, tvPassHighligther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // edMail = findViewById(R.id.ed_email);
        backBtn = findViewById(R.id.imageBack);
        til_email = findViewById(R.id.til_email);
        til_password = findViewById(R.id.til_password);
        til_rePassword = findViewById(R.id.til_rePassword);
        btn_confirm = findViewById(R.id.button);
        emailHighligther = findViewById(R.id.tv_emailHighLigther);
        tvPassHighligther = findViewById(R.id.tv_passHighLigther);


        btn_confirm.setOnClickListener(view -> {


            if (!validationEmail() | !validatePassword() | !validateRePassword()) {
                return;
            }

            Toast.makeText(this, "successfully registered", Toast.LENGTH_SHORT).show();
            til_email.getEditText().getText().clear();
            til_password.getEditText().getText().clear();
            til_rePassword.getEditText().getText().clear();
            til_email.setEndIconVisible(false);
            til_password.setPasswordVisibilityToggleEnabled(false);
            til_rePassword.setPasswordVisibilityToggleEnabled(false);
            tvPassHighligther.setVisibility(View.GONE);
            btn_confirm.setEnabled(false);
        });
        til_email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (validationEmail()) {
                    //layout1_email.setPasswordVisibilityToggleEnabled(true);
                    til_email.setPasswordVisibilityToggleDrawable(R.drawable.tick);
                    til_email.setEndIconVisible(true);
                    emailvalid = true;
                   // til_email.setEndIconDrawable(R.drawable.tick);
                    if (til_email.getEditText().getText().toString().equalsIgnoreCase("abcdef@gmail.com")) {
                        emailHighligther.setVisibility(View.VISIBLE);
                    }

                    validAll();
                    Toast.makeText(CreateAccountActivity.this, "Valid EMail", Toast.LENGTH_SHORT).show();
                }
            }
        });
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//                if (validationEmail()) {
//                    layout1_email.setPasswordVisibilityToggleDrawable(R.drawable.tick);
//                    if (edMail.getText().toString().equalsIgnoreCase("abcdef@gmail.com")) {
//                        emailHighligther.setVisibility(View.VISIBLE);
//                    }
//
//                    //layout1_email.setEndIconVisible(true);
//                    Toast.makeText(CreateAccountActivity.this, "something", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        til_password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (validatePassword()) {

                    til_password.setPasswordVisibilityToggleDrawable(R.drawable.tick);
                    passwordvalid = true;
                    validAll();
                }
            }
        });

        til_rePassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (validateRePassword()) {
                    repasswordvalid = true;
                    til_rePassword.setPasswordVisibilityToggleDrawable(R.drawable.tick);
                    validAll();

                }
            }
        });


        backBtn.setOnClickListener(view -> finish());

    }


    private boolean validationEmail() {
        String emailInput = til_email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            til_email.setErrorEnabled(false);
            // layout1_email.setEndIconVisible(false);
            til_email.setBackgroundResource(R.drawable.background);
            emailvalid = false;

            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            til_email.setBackgroundResource(R.drawable.background);
            til_email.setError("Invalid eMail!");
            emailvalid = false;

            return false;
        } else {

            emailvalid = true;
            til_email.setBackgroundResource(R.drawable.backgroundgreen);

            til_email.setErrorEnabled(false);


            return true;

        }
    }

    private boolean validatePassword() {
        String passwordInput = til_password.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            tvPassHighligther.setVisibility(View.VISIBLE);
            tvPassHighligther.setText("Empty Password! ");
            passwordvalid=false;
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            tvPassHighligther.setVisibility(View.VISIBLE);
            tvPassHighligther.setText("Weak Passwords! All conditions not satisfied!");

            passwordvalid=false;
            return false;
        } else {
            passwordvalid = true;
            til_password.setPasswordVisibilityToggleEnabled(true);
            til_password.setPasswordVisibilityToggleDrawable(R.drawable.tick);
            tvPassHighligther.setVisibility(View.GONE);
            til_password.setError(null);
            til_password.setErrorEnabled(false);

            return true;
        }

    }

    private boolean validateRePassword() {
        String re_passwordInput = til_rePassword.getEditText().getText().toString().trim();
        String passwordInput = til_password.getEditText().getText().toString().trim();
        if (passwordInput.equalsIgnoreCase(re_passwordInput) && !passwordInput.isEmpty()) {
            tvPassHighligther.setVisibility(View.GONE);


            til_rePassword.setPasswordVisibilityToggleEnabled(true);
            til_rePassword.setPasswordVisibilityToggleDrawable(R.drawable.tick);
            tvPassHighligther.setVisibility(View.GONE);
            til_rePassword.setError(null);
            til_rePassword.setErrorEnabled(false);
            repasswordvalid = true;
            return true;

        } else {
            repasswordvalid=false;
            tvPassHighligther.setVisibility(View.VISIBLE);
            tvPassHighligther.setText("Password did not match!");
            // layout3_repeatPass.setBackgroundResource(R.drawable.background);

            return false;
        }

    }

    private void validAll() {


        if (passwordvalid && repasswordvalid && emailvalid) {
            btn_confirm.setEnabled(true);
        } else {
            btn_confirm.setEnabled(false);

        }

    }
}