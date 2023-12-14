package com.man293.food_ordering_spoon.utils;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.views.activities.LoginActivity;

public class GoogleSignManager {
    private static GoogleSignManager  instance;
    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;

    private GoogleSignManager(String serverClientId) {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(serverClientId)
                .requestEmail()
                .build();
    }
    public static synchronized  GoogleSignManager getInstance(Context context) {
        if(instance == null) {
            instance  = new GoogleSignManager(context.getString(R.string.SERVER_CLIENT_ID));
        }
        return instance;
    }
    public GoogleSignInClient getClient() {
        return gsc;
    }
    public GoogleSignManager setClient(Activity activity) {
        gsc = GoogleSignIn.getClient(activity, gso);
        return this;
    }
}
