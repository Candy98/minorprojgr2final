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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import de.mateware.snacky.Snacky;

public class RegistrationActivity extends AppCompatActivity {
    View vLog, myView;
    EditText etFullName, etEmail, etPwd, etRePwd, etPhNo, etLevName, etPinCode, etActualLocation, etAffilation, etWebsite;
    Button regBtn;
    boolean isUname = false, isEmail = false, isPhnNo = false, isPwd = false, isRePwdMach = false;
    boolean isUpper = false, isNumber = false, isActualLocation = false, isValidWebsite = false;


    LoginACtivity sucSnack;
    ProgressDialog progressDialog;
    Intent intent;
    Spinner spinnerSetInstReg, spinnerSetInstSubCatReg, spinnerAffilation;
    String selectedInst, selectedInstSubCatLevel;
    String selectedRegType = null, selectedRegSubType = null;
    String[] reginstcat = {"Select Reg Type", "Admin", "School", "College", "Teacher", "Student"};
    String[] levelCollege = {"Select College Type", "Engg", "Hons", "Hm"};
    String[] levelTeacher = {"Select Teacher Type", "Primary School", "High School", "Higher Secondary School", "Btech", "BCA"};
    String[] levelStudent = {"Select Student Level", "Primary School Student", "Secondary School Student", "HigherSecondary School Student", "Btech", "BCA"};
    String[] levelSchool = {"Select School Type", "Primary", "Secondary", "Higher Secondary"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        viewBinder();
        objInitializer();
        final ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, reginstcat);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSetInstReg.setAdapter(spinnerAdapter);

        spinnerSetInstReg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    selectedRegType = spinnerSetInstReg.getItemAtPosition(i).toString();
                    spinnerSetInstSubCatReg.setVisibility(View.VISIBLE);
                    switch (i) {


                        case 1: {
                            selectedRegType = spinnerSetInstReg.getItemAtPosition(1).toString();
                            etAffilation.setText("N/A");
                            etAffilation.setVisibility(View.GONE);
                            spinnerSetInstSubCatReg.setVisibility(View.GONE);

                        }
                        break;
                        case 2: {
                            ArrayAdapter<String> spinnerAdaptersel = new ArrayAdapter(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, levelSchool);
                            spinnerSetInstSubCatReg.setAdapter(spinnerAdaptersel);
                            etAffilation.setText("");
                            etAffilation.setVisibility(View.VISIBLE);

                        }
                        break;
                        case 3: {
                            ArrayAdapter spinnerAdaptersel = new ArrayAdapter(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, levelCollege);
                            spinnerSetInstSubCatReg.setAdapter(spinnerAdaptersel);
                            etAffilation.setText("");
                            etAffilation.setVisibility(View.VISIBLE);

                        }
                        break;
                        case 4: {

                            ArrayAdapter spinnerAdaptersel = new ArrayAdapter(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, levelTeacher);
                            spinnerSetInstSubCatReg.setAdapter(spinnerAdaptersel);
                            etAffilation.setText("N/A");
                            etAffilation.setVisibility(View.GONE);
                        }
                        break;
                        case 5: {
                            ArrayAdapter spinnerAdaptersel = new ArrayAdapter(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, levelStudent);
                            spinnerSetInstSubCatReg.setAdapter(spinnerAdaptersel);
                            etAffilation.setText("N/A");
                            etAffilation.setVisibility(View.GONE);
                        }
                        break;
                        default:
                            break;
                    }


                } else if (i == 0) {
                    selectedRegType = null;

                }
                spinnerSetInstSubCatReg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i > 0) {
                            selectedRegSubType = spinnerSetInstSubCatReg.getItemAtPosition(i).toString();

                        } else {
                            selectedRegSubType = null;

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                if (i == 0) {
                    spinnerSetInstSubCatReg.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        intent = new Intent(RegistrationActivity.this, LoginACtivity.class);
        vLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameValidator(etFullName.getText().toString());
                emailValidator(etEmail.getText().toString());
                phoneNoValidator(etPhNo.getText().toString());
                passwordValidator(etPwd.getText().toString());
                reEnterPwdValidator(etRePwd.getText().toString());
                AffilationVAlidator(etAffilation.getText().toString());
                PinCodeValidator(etPinCode.getText().toString());
                ActualLocationValidator(etActualLocation.getText().toString());
                WebsiteValidator(etWebsite.getText().toString());

                if (isUname && isEmail && isPhnNo && isPwd && isRePwdMach && selectedRegType != null && selectedRegSubType != null && isUpper && isNumber && isActualLocation && isValidWebsite) {
                    parseConnect();
                }
            }
        });
        isParseUserSignedUpChecker();


    }

    private void WebsiteValidator(String website) {
        if (website.equals("")) {
            FancyToast.makeText(this, "Please enter a website", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
            isValidWebsite = false;
        } else {
            isValidWebsite = true;
        }
    }

    private void ActualLocationValidator(String actualLocation) {
        if (actualLocation.equals("")) {
            FancyToast.makeText(this, "Please enter your valid location", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
            isActualLocation = false;
        } else {
            isActualLocation = true;
        }
    }

    private void PinCodeValidator(String pincode) {
        try {
            Double.parseDouble(pincode);
            isNumber = true;
        } catch (Exception e) {
            isNumber = false;
            FancyToast.makeText(this, "Pincode must be a number", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

        }
    }

    public void AffilationVAlidator(String affilation) {
        if (!affilation.equals("")) {
            for (int i = 0; i < affilation.length(); i++) {
                isUpper = Character.isUpperCase(affilation.charAt(i));

            }
            if (!isUpper) {
                FancyToast.makeText(this, "Affilation must be in capital letters", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();


            }

        }
    }


    private void isParseUserSignedUpChecker() {
        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();
        }
    }

    private void objInitializer() {
        sucSnack = new LoginACtivity();
        progressDialog = new ProgressDialog(this);
    }

    private void parseConnect() {
        if (isOnline()) {

            ParseUser appUser = new ParseUser();
            appUser.setUsername(etFullName.getText().toString());
            appUser.setPassword(etPwd.getText().toString());
            appUser.setEmail(etEmail.getText().toString());
            appUser.put("Phone", etPhNo.getText().toString());
            appUser.put("Regtype", selectedRegType);
            appUser.put("level", selectedRegSubType);
            appUser.put("affilation", etAffilation.getText().toString());
            appUser.put("pincode", etPinCode.getText().toString());
            appUser.put("actuallocation", etActualLocation.getText().toString());
            appUser.put("website",etWebsite.getText().toString());
            progressDialog.setMessage("Registering " + etFullName.getText());

            progressDialog.show();
            appUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        successSnackBuilderLengthLong("RegistrationActivity success " + etFullName.getText().toString(), myView);


                    } else {
                        sucSnack.errorSnackBuilder(e.getMessage(), myView);
                    }
                    progressDialog.dismiss();
                }
            });

        } else {
            sucSnack.errorSnackBuilder("Please check your internet connection and try again", myView);
        }

    }


    public void reEnterPwdValidator(String repwd) {
        String prvpwd = null;
        if (isPwd) {
            prvpwd = etPwd.getText().toString();
            pwdChecker(prvpwd, repwd);
        } else if (repwd.length() == 0) {
            FancyToast.makeText(this, "Please re-enter your password for confirmation ", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
            isRePwdMach = false;
        }
    }

    public void pwdChecker(String prvpwd, String repwd) {
        if (prvpwd.equals(repwd)) {
            isRePwdMach = true;
        } else {
            isRePwdMach = false;
            FancyToast.makeText(this, "Entered password doesnot match ", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

        }

    }

    public void passwordValidator(String pwd) {
        if (pwd.length() == 0) {
            FancyToast.makeText(this, "Please enter password ", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
            isPwd = false;
        } else {
            isPwd = true;
        }
    }

    public void phoneNoValidator(String phno) {
        if (phno.length() == 10) {
            isPhnNo = true;
        } else if (phno.length() == 0) {
            FancyToast.makeText(this, "Phone no feield is empty ", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

            isPhnNo = false;
        } else {
            FancyToast.makeText(this, "Please enter a valid phone no ", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

            isPhnNo = false;
        }
    }

    public void emailValidator(String email) {
        if (TextUtils.isEmpty(email)) {
            FancyToast.makeText(this, "Email feild is empty", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

            isEmail = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            FancyToast.makeText(this, "Enter a valid email address", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

            isEmail = false;
        } else {
            isEmail = true;
        }


    }


    public void nameValidator(String name) {
        if (name.length() == 0) {
            FancyToast.makeText(this, "Please enter Username", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
            isUname = false;
        } else {
            isUname = true;
        }

    }

    private void viewBinder() {
        vLog = findViewById(R.id.vLog);
        etFullName = findViewById(R.id.etFullname);
        etEmail = findViewById(R.id.etEmail);
        etPwd = findViewById(R.id.etPwd);
        etRePwd = findViewById(R.id.etRePwd);
        etPhNo = findViewById(R.id.etPhno);
        regBtn = findViewById(R.id.regBtn);
        myView = findViewById(R.id.regLayout);
        spinnerSetInstReg = findViewById(R.id.spinnerSetInstReg);
        spinnerSetInstSubCatReg = findViewById(R.id.spinnerSetInstSubCatReg);
        etActualLocation = findViewById(R.id.etActualLocation);
        etPinCode = findViewById(R.id.etPinCode);
        etLevName = findViewById(R.id.etLevName);
        etAffilation = findViewById(R.id.etAffiliation);
        etWebsite = findViewById(R.id.etWebsite);


    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void successSnackBuilderLengthLong(String msg, View snackView) {
        Snacky.builder().setView(snackView).setBackgroundColor(Color.parseColor("#FFA726")).setText(msg).setActionText("Ok").setActionTextColor(Color.parseColor("#ffffff"))
                .setActionClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        successActionTriggered();
                    }
                })
                .setTextColor(Color.parseColor("#ffffff"))
                .setDuration(Snacky.LENGTH_INDEFINITE)
                .success()


                .show();
    }

    private void successActionTriggered() {
        startActivity(new Intent(RegistrationActivity.this, LoginACtivity.class));
        RegistrationActivity.this.finish();
    }


}
