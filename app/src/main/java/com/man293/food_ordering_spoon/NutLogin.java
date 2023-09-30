package com.man293.food_ordering_spoon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NutLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        configureNextButton();
    }
    private void configureNextButton() {
        Button nextButton = (Button) findViewById(R.id.changeToSignIn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(NutLogin.this, NutSignIn.class));
            }
        });
    }

}