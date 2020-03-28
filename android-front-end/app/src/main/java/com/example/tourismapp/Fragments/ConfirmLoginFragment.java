package com.example.tourismapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.services.cognitoidentityprovider.model.ChallengeName;
import com.amazonaws.services.cognitoidentityprovider.model.RespondToAuthChallengeResult;
import com.example.tourismapp.Helpers.CognitoHelper;
import com.example.tourismapp.R;

public class ConfirmLoginFragment extends Fragment {
    EditText etMfaCode;
    Button btnMfaLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login_mfa, container, false);

        etMfaCode = view.findViewById(R.id.etMfaCode);
        btnMfaLogin = view.findViewById(R.id.btnMfaLogin);

        btnMfaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataBundle = getArguments();
                String email = dataBundle.getString("email");
                CognitoHelper cognitoHelper = new CognitoHelper(getActivity());
                CognitoUser cognitoUser = cognitoHelper.getUserPool().getUser(email);
                RespondToAuthChallengeResult respondToAuthChallengeResult = new RespondToAuthChallengeResult();
                respondToAuthChallengeResult.setChallengeName("SMS_MFA");
                cognitoUser.respondToMfaChallenge(etMfaCode.getText().toString(), respondToAuthChallengeResult, confirmLoginHandler, true);
            }
        });

        return view;
    }

    final AuthenticationHandler confirmLoginHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            Log.i("Success", userSession.getUsername());
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
            Log.i("getAuthDetails", authenticationContinuation.getParameters());
        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation continuation) {
            Log.i("getMFACode", "continuation");
        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {
            Log.i("authenticationChallenge", continuation.getChallengeName());
        }

        @Override
        public void onFailure(Exception exception) {
            Log.i("onFailure", exception.getLocalizedMessage());
        }
    };
}
