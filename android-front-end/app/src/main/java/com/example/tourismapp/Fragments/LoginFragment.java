package com.example.tourismapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.example.tourismapp.Helpers.CognitoHelper;
import com.example.tourismapp.Helpers.GlobalStorage;
import com.example.tourismapp.Interface.RetrofitApiInterface;
import com.example.tourismapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {

    EditText etEmail, etPassword;
    Button btnLogin;
    NavigationView navigationView;
    String mfaCode = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (email.trim().equals("") || password.trim().equals("")) {
                    Toast.makeText(getActivity(), "All fields are required!", Toast.LENGTH_SHORT).show();
                } else {
                    // Authentication logic
                    CognitoHelper cognitoHelper = new CognitoHelper(getActivity());
                    CognitoUser cognitoUser = cognitoHelper.getUserPool().getUser(email);
                    cognitoUser.getSessionInBackground(loginHandler);
                }
            }
        });

        return view;
    }

    final AuthenticationHandler loginHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            Log.i("Success", userSession.getUsername());
            handleLoginSuccess();
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
            Log.i("getAuthDetails", authenticationContinuation.getParameters());
            AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, etPassword.getText().toString(), null);
            authenticationContinuation.setAuthenticationDetails(authenticationDetails);
            authenticationContinuation.continueTask();
        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation continuation) {
            getMFACodeFromUser(continuation);
        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {
            Log.i("authenticationChallenge", continuation.getChallengeName());
        }

        @Override
        public void onFailure(Exception exception) {
            Log.i("onFailure", exception.getLocalizedMessage());
            Toast.makeText(getActivity(), "Invalid code! Please try again!", Toast.LENGTH_SHORT).show();
        }
    };

    private void getMFACodeFromUser(MultiFactorAuthenticationContinuation continuation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Verification code");

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mfaCode = input.getText().toString();
                continuation.setMfaCode(mfaCode);
                continuation.continueTask();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void handleLoginSuccess() {
        // set auth status with email to global storage
        String email = etEmail.getText().toString();
        ((GlobalStorage) getActivity().getApplication()).setUserEmail(email);
        // fetch user Id from db
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http:192.168.56.1:3000/") // replace your local ip address here (but not localhost/127.0.0.1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApiInterface retrofitApiInterface = retrofit.create(RetrofitApiInterface.class);
        Call<Object> call = retrofitApiInterface.getUserId(email);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }
                List<Object> temp = (List<Object>) response.body();
                LinkedTreeMap map = (LinkedTreeMap) temp.get(0);
                // set user id to global storage
                int userId = (int)(Double.parseDouble(map.get("id").toString()));
                Log.e("msg","value is: "+userId);
                ((GlobalStorage) getActivity().getApplication()).setUserId(userId);
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        // change visibilities of Login, Signup, Logout, and TicketHistory fragments
        changeVisibilities();
        // navigate to search fragment
        SearchFragment fragment = new SearchFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(((ViewGroup) (getView().getParent())).getId(), fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void changeVisibilities() {
        navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();

        nav_Menu.findItem(R.id.nav_history).setVisible(true);
        nav_Menu.findItem(R.id.nav_logout).setVisible(true);
        nav_Menu.findItem(R.id.nav_login).setVisible(false);
        nav_Menu.findItem(R.id.nav_registration).setVisible(false);

    }
}
