package com.example.poonamtiwarigreenflag;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {
    TextInputLayout layout1_email, layout2_pass, layout3_repeatPass;
    TextInputEditText edMail;
    public static final Pattern PASSWORD = Pattern.compile("^" +
            "(?=.*[0-9])" +              //at least 1 digit
            "(?=.*[a-z])" +              // at least one lower case letter
            "(?=.*[A-Z])" +                 // at least one uppler case letter
            "(?=.*[!@£$%^&*()_+=])" +       //at least one character
            "(?=\\s+$)" +                    // no white space
            ".{8,}" +                         // at leats 8 characters.
            "$");
    Button btn_confirm;
TextView emailHighligther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        edMail = findViewById(R.id.ed_email);
        layout1_email = findViewById(R.id.layout1);
        layout2_pass = findViewById(R.id.layout2_password);
        btn_confirm = findViewById(R.id.button);
emailHighligther=findViewById(R.id.tv_emailHighLigther);


        btn_confirm.setOnClickListener(view -> {
            if (!validationEmail() | validatePassword()) {
                return;
            }
            Toast.makeText(this, "successfully registered", Toast.LENGTH_SHORT).show();
            layout1_email.getEditText().getText().clear();
            layout2_pass.getEditText().getText().clear();
        });
        layout1_email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (validationEmail()) {
                    if(edMail.getText().toString().equalsIgnoreCase("abcdef@gmail.com")){
                        emailHighligther.setVisibility(View.VISIBLE);
                    }

                    layout1_email.setEndIconVisible(true);
                    Toast.makeText(CreateAccountActivity.this, "something", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private boolean validationEmail() {
        String emailInput = layout1_email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            layout1_email.setEndIconVisible(false);

            layout1_email.setBackgroundResource(R.drawable.background);


            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            layout1_email.setEndIconVisible(false);
            layout1_email.setBackgroundResource(R.drawable.background);
            layout1_email.setBoxStrokeErrorColor(ColorStateList.valueOf(Color.RED));
            //layout1_email.setError("not valid!");
            return false;
        } else {
            layout1_email.setEndIconVisible(true);
           edMail.setBackgroundResource(R.drawable.backgroundgreen);
            return true;

        }
    }

    private boolean validatePassword() {
        String passwordInput = layout2_pass.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            layout1_email.setBackgroundResource(R.drawable.background);
            layout2_pass.setError("error");
            layout1_email.setBoxStrokeErrorColor(ColorStateList.valueOf(Color.RED));
            return false;
        } else if (!PASSWORD.matcher(passwordInput).matches()) {
            layout1_email.setBackgroundResource(R.drawable.background);
            //layout1_email.setBoxStrokeErrorColor(ColorStateList.valueOf(Color.RED));
            layout2_pass.setError("weak");

            return false;
        } else {
            layout2_pass.setError(null);
            layout2_pass.setEndIconDrawable(R.drawable.tick);
            layout1_email.setBackgroundResource(R.drawable.backgroundgreen);
            return true;
        }

    }
}