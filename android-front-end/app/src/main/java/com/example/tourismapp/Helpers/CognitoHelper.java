package com.example.tourismapp.Helpers;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoHelper {
    private String userPoolId = "us-east-1_tAUvN2DVv";
    private String clientId = "58mahng8emhcqosb4pe8bvec13";
    private Regions region = Regions.US_EAST_1;
    private Context context;
    private String user;
    private boolean isUserLoggedIn;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isUserLoggedIn() {
        return isUserLoggedIn;
    }

    public void setUserLoggedIn(boolean userLoggedIn) {
        isUserLoggedIn = userLoggedIn;
    }

    public CognitoHelper(Context context) {
        this.context = context;
    }

    public String getUserPoolId() {
        return userPoolId;
    }

    public String getClientId() {
        return clientId;
    }

    public Regions getRegion() {
        return region;
    }

    public CognitoUserPool getUserPool() {
        return new CognitoUserPool(context, userPoolId, clientId, "", region);
    }


}
