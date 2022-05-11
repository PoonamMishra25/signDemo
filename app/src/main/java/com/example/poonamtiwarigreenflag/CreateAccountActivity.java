package com.example.poonamtiwarigreenflag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

import java.util.ArrayList;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {
    TextInputLayout til_email, til_password, til_rePassword;
    boolean emailvalid, passwordvalid, repasswordvalid;

    ImageView backBtn;
    SqlDataBaseHelper sqlDataBaseHelper;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 4 characters
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
        //user=new User();
        sqlDataBaseHelper=new SqlDataBaseHelper(CreateAccountActivity.this);

        btn_confirm.setOnClickListener(view -> {
            String email =  til_email.getEditText().getText().toString().trim();
            String password = til_password.getEditText().getText().toString().trim();


           if (validationEmail() && validateRePassword() && validateRePassword()) {

                ContentValues contentValues = new ContentValues();
                contentValues.put("email", email);
                contentValues.put("password", password);


                sqlDataBaseHelper.insertUserDetail(contentValues);



                til_email.getEditText().getText().clear();
                til_password.getEditText().getText().clear();
                til_rePassword.getEditText().getText().clear();
                til_email.setEndIconVisible(false);
                til_password.setPasswordVisibilityToggleEnabled(false);
                til_rePassword.setPasswordVisibilityToggleEnabled(false);
                tvPassHighligther.setVisibility(View.GONE);
                btn_confirm.setEnabled(false);
                emailHighligther.setVisibility(View.GONE);
            }
        });
        til_email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                emailHighligther.setVisibility(View.INVISIBLE);
btn_confirm.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (validationEmail()) {

                    til_email.setPasswordVisibilityToggleDrawable(R.drawable.tick);
                    til_email.setEndIconVisible(true);
                    emailvalid = true;


                  String user_email= til_email.getEditText().getText().toString();
                    ArrayList<String> emails_database;
                    emails_database= sqlDataBaseHelper.checkIfRecordExist(user_email);

                    if (emails_database.contains(user_email)){

                        emailHighligther.setVisibility(View.VISIBLE);
                        emailHighligther.setText("this User already Exists!");
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

                til_rePassword.setPasswordVisibilityToggleEnabled(false);
                passwordvalid=false;
                btn_confirm.setEnabled(false);

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
                if(til_password !=til_rePassword) {
                    repasswordvalid=false;
                    btn_confirm.setEnabled(false);
                    til_rePassword.setPasswordVisibilityToggleEnabled(false);
                }

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
            emailHighligther.setVisibility(View.INVISIBLE);

            til_email.setBackgroundResource(R.drawable.background);
            emailvalid = false;

            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailHighligther.setVisibility(View.INVISIBLE);
            til_email.setBackgroundResource(R.drawable.background);
            til_email.setError("Invalid eMail!");
            emailvalid = false;

            return false;
        } else {
            emailHighligther.setVisibility(View.INVISIBLE);
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
            til_password.setPasswordVisibilityToggleEnabled(false);

            tvPassHighligther.setText("Empty Password! ");
            passwordvalid=false;
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            til_password.setPasswordVisibilityToggleEnabled(false);
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

        if( passwordInput.isEmpty()){
            repasswordvalid=false;
            tvPassHighligther.setVisibility(View.VISIBLE);
            til_rePassword.setPasswordVisibilityToggleEnabled(false);
            tvPassHighligther.setText("Password Filed can not be empty!");

            return false;
        }
        if (!passwordInput.equalsIgnoreCase(re_passwordInput) ) {
            repasswordvalid=false;
            tvPassHighligther.setVisibility(View.VISIBLE);
            til_rePassword.setPasswordVisibilityToggleEnabled(false);
            tvPassHighligther.setText("Password did not match!");

            return false;
        } else {
            tvPassHighligther.setVisibility(View.GONE);


            til_rePassword.setPasswordVisibilityToggleEnabled(true);
            til_rePassword.setPasswordVisibilityToggleDrawable(R.drawable.tick);
            tvPassHighligther.setVisibility(View.GONE);
            til_rePassword.setError(null);
            til_rePassword.setErrorEnabled(false);
            repasswordvalid = true;
            return true;

        }

    }

    private void validAll() {


        btn_confirm.setEnabled(passwordvalid && repasswordvalid && emailvalid);

    }
}