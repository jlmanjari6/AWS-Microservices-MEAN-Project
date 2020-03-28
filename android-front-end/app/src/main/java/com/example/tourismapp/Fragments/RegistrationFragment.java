package com.example.tourismapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;
import com.example.tourismapp.Helpers.CognitoHelper;
import com.example.tourismapp.Helpers.DatePickerFragment;
import com.example.tourismapp.Helpers.DialogBox;
import com.example.tourismapp.Interface.RetrofitApiInterface;
import com.example.tourismapp.Models.User;
import com.example.tourismapp.R;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationFragment extends Fragment {

    Button btnRegister;
    EditText etFirstName;
    EditText etLastName;
    EditText etPhone;
    EditText etEmail;
    EditText etDob;
    EditText etPassword;
    ImageView btnCalender;
    String gender = "";
    User user = null;

    final CognitoUserAttributes userAttributes = new CognitoUserAttributes();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        btnCalender = view.findViewById(R.id.btnCalender);
        btnRegister = view.findViewById(R.id.btnRegister);
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etPhone = view.findViewById(R.id.etPhone);
        etDob = view.findViewById(R.id.etDob);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);

        //for gender
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.genderRadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioFemale:
                        gender = "female";
                        break;
                    case R.id.radioMale:
                        gender = "male";
                        break;
                    case R.id.radioOther:
                        gender = "other";
                        break;
                }
            }
        });

        // for date of birth
        btnCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "date picker");
            }
        });

        // Register button click
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String phone = etPhone.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String dob =etDob.getText().toString();
                String age = calculateAge(dob) + "";

                // validation
                if (firstName.trim().equals("") || lastName.trim().equals("") ||
                        phone.trim().equals("") || gender.trim().equals("") || age.trim().equals("") ||
                        email.trim().equals("") || password.trim().equals("")) {
                    Toast.makeText(getActivity(), "All fields are required!", Toast.LENGTH_SHORT).show();
                } else {
                    // Cognito Registration logic
                    userAttributes.addAttribute("given_name", firstName);
                    userAttributes.addAttribute("family_name", lastName);
                    userAttributes.addAttribute("email", email);
                    userAttributes.addAttribute("gender", gender);
                    userAttributes.addAttribute("phone_number", phone);
                    String selYear = dob.split("/")[2];
                    String selMonth = dob.split("/")[0];
                    String selDayOfMonth = dob.split("/")[1];
                    if(selMonth.length() != 2) {
                        selMonth = "0" + selMonth;
                    }
                    if(selDayOfMonth.length() != 2) {
                        selDayOfMonth = "0" + selDayOfMonth;
                    }
                    dob = selMonth + "/" + selDayOfMonth + "/" + selYear;
                    userAttributes.addAttribute("birthdate", dob);
                    user = new User(firstName, lastName, gender, email, password, age);
                    CognitoHelper cognitoHelper = new CognitoHelper(getActivity());
                    cognitoHelper.getUserPool().signUpInBackground(email, password, userAttributes, null, signupCallback);
                }
            }
        });
        return view;
    }

    // method to calculate age
    public int calculateAge(String dob) {
        // calculate age
        int age;

        Calendar calender = Calendar.getInstance();
        int curYear = calender.get(Calendar.YEAR);
        int curMonth = calender.get(Calendar.MONTH);
        int curDay = calender.get(Calendar.DATE);

        int selYear = Integer.parseInt(dob.split("/")[2]);
        int selMonth = Integer.parseInt(dob.split("/")[0]);
        int selDayOfMonth = Integer.parseInt(dob.split("/")[1]);

        age = curYear - selYear;
        if ((curMonth < selMonth)
                || ((curMonth == selMonth) && (curDay < selDayOfMonth))) {
            --age;
        }
        if (age < 0)
            throw new IllegalArgumentException("Age < 0");

        return age;
    }

    // cognito signup response handler
    final SignUpHandler signupCallback = new SignUpHandler() {
        @Override
        public void onSuccess(CognitoUser user, SignUpResult signUpResult) {
            Boolean regState = signUpResult.getUserConfirmed();
            if (regState) {
                // User is already confirmed
                System.out.println("Confirmed");
            } else {
                // User is not confirmed
                openDialog();
                saveUsertoDB();
            }
        }

        @Override
        public void onFailure(Exception exception) {
            String error = exception.getMessage();
            System.out.println("failed" + exception.getMessage());
            if (error.contains("(")) {
                error = error.substring(0, error.indexOf('('));
            }
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        }
    };

    // Dialog box to display welcome message
    public void openDialog() {
        DialogBox db = new DialogBox();
        db.show(getFragmentManager(), "dialog");
    }

    public void saveUsertoDB() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.16.1:3000/") // replace your local ip address here (but not localhost/127.0.0.1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApiInterface retrofitApiInterface = retrofit.create(RetrofitApiInterface.class);
        Call<User> call = retrofitApiInterface.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }
                User userResponse = response.body();
                System.out.println("API response: " + userResponse);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

}
