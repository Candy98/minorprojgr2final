package com.example.finalproj_minor_gr2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproj_minor_gr2.school.CollegeEditActivity;
import com.example.finalproj_minor_gr2.student.StudentProfileActivity;
import com.example.finalproj_minor_gr2.teacher.TeacherEditActivity;
import com.example.finalproj_minor_gr2.teacher.TeachersHomeActivity;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import de.mateware.snacky.Snacky;

public class LoginACtivity extends AppCompatActivity {
    EditText etEmail, etPwd;
    Button login;
    View fgPwd, vReg, myView;

    String emailFormat = "[[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+]";
    boolean isOkEmail = false;
    boolean isOkPwd = false;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();


        progressDialog = new ProgressDialog(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailValidation(etEmail.getText().toString().trim());
                pwdValidation(etPwd.getText().toString().trim());
                if (isOkEmail && isOkPwd) {
                    parseLogin();

                }

            }
        });
        fgPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  startActivity(new Intent(LoginACtivity.this, ForgotPasswordActivity.class));
            }
        });

        //ParseInstallation.getCurrentInstallation().saveInBackground();
        vReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginACtivity.this, RegistrationActivity.class));

            }
        });
        isParseUserLoggedIn();
    }

    private void isParseUserLoggedIn() {
        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();
        }
    }

    private void parseLogin() {
        if (isOnline()) {
            progressDialog.setMessage("Logging in ");

            progressDialog.show();
            ParseUser.logInInBackground(etEmail.getText().toString(), etPwd.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null && e == null) {
                        FancyToast.makeText(LoginACtivity.this, user.get("username") + " " + "has logged in", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        transitionTo();
                    } else {
                        errorSnackBuilder(e.getMessage(), myView);
                    }
                    progressDialog.dismiss();
                }
            });
        } else {
            errorSnackBuilder("Please Check your internet connection", myView);
        }
    }

    private void transitionTo() {
        String loginType;
        ParseUser parseUser=ParseUser.getCurrentUser();
        loginType=parseUser.get("Regtype").toString();
        Toast.makeText(this, loginType, Toast.LENGTH_SHORT).show();
        switch (loginType){
            case "Student":{
                startActivity(new Intent(LoginACtivity.this, StudentProfileActivity.class));

            }
            break;
            case "Teacher":{
                startActivity(new Intent(LoginACtivity.this, TeachersHomeActivity.class));
            }
            break;
            case "School":{
                startActivity(new Intent(LoginACtivity.this, TeachersHomeActivity.class));

            }
            break;
            case "College":{
                startActivity(new Intent(LoginACtivity.this, CollegeEditActivity.class));

            }
            break;
            default: break;
        }
    }

    private void transitionToSocialMedia() {
       // startActivity(new Intent(LoginACtivity.this,SocialMediaActivity.class));
    }

    private void pwdValidation(String password) {
        if (TextUtils.isEmpty(password)) {

            FancyToast.makeText(this, "Enter a valid password !", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

            isOkPwd = false;
        } else {
            isOkPwd = true;
        }
    }

    private void bindViews() {
        etEmail = findViewById(R.id.etEmail);
        etPwd = findViewById(R.id.etPwd);
        login = findViewById(R.id.logBtn);
        fgPwd = findViewById(R.id.tvFgPwd);
        vReg = findViewById(R.id.vReg);
        myView = findViewById(R.id.logView);

    }

    public void emailValidation(String email) {

        if (TextUtils.isEmpty(email)) {
            FancyToast.makeText(this, "Email feild is empty", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

            isOkEmail = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            FancyToast.makeText(this, "Enter a valid email address", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

            isOkEmail = false;
        } else {
            isOkEmail = true;
        }


    }

    public void successSnackBuilder(String msg, View snackView) {
        Snacky.builder().setView(snackView).setBackgroundColor(Color.parseColor("#FFA726")).setText(msg).setActionText("Ok").setActionTextColor(Color.parseColor("#ffffff"))

                .setTextColor(Color.parseColor("#ffffff"))

                .success()


                .show();
    }


    public void errorSnackBuilder(String msg, View snackView) {
        Snacky.builder().setView(snackView).setBackgroundColor(Color.parseColor("#B71C1C")).setText(msg).setActionText("Ok").setActionTextColor(Color.parseColor("#ffffff"))

                .setTextColor(Color.parseColor("#ffffff"))
                .error()


                .show();
    }


    private void errorsActionTriggered() {

    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
