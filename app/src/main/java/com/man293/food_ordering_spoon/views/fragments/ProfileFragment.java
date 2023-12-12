package com.man293.food_ordering_spoon.views.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.imageview.ShapeableImageView;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.models.User;
import com.man293.food_ordering_spoon.utils.GoogleSignManager;
import com.man293.food_ordering_spoon.views.activities.EditProfileActivity;
import com.man293.food_ordering_spoon.views.activities.LoginActivity;

public class ProfileFragment extends Fragment {
    private final int REQUEST_UPDATE_USER_CODE = 0;
    public ProfileFragment() {  }

    private Button bt_edit_profile;
    private TextView tvName, tvUsername, tvAddress;
    ShapeableImageView ivPicture;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_profile, container, false);

        initView(view);

        Button bt_edit = view.findViewById(R.id.button_edit);
        bt_edit.setOnClickListener(v -> {
            startActivityForResult(new Intent(getContext(), EditProfileActivity.class),REQUEST_UPDATE_USER_CODE);
        });

        /* Logout */
        view.findViewById(R.id.button_logout).setOnClickListener(view1 -> {
            if(User.removeCurrentUser(getContext())) {
                GoogleSignInClient client = GoogleSignManager.getInstance(getContext()).getClient();
                if(client != null) {
                    client.signOut();
                }
                Toast.makeText(getContext(), "logged out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
            } else  {
                Toast.makeText(getContext(), "Error when logout!", Toast.LENGTH_SHORT).show();
            }
        });
        return  view;
    }
    @SuppressLint("SetTextI18n")
    private void initView(View view) {
        tvName = view.findViewById(R.id.name);
        tvUsername = view.findViewById(R.id.user_name);
        tvAddress = view.findViewById(R.id._address);
        ivPicture = view.findViewById(R.id.picture);

        User user = User.getCurrentUser(getContext());
        if (user != null) {
            tvName.setText(user.getFirstName() + " " + user.getLastName());
            tvUsername.setText(user.getPhoneNum());
            tvAddress.setText(user.getAddress());
            String imageSrc = user.getPicture().contains("http")
                    ? user.getPicture()
                    : getContext().getString(R.string.BASE_URL) + getContext().getString(R.string.PUBLIC_IMAGES, user.getPicture());
            Glide.with(getContext()).load(imageSrc).circleCrop().into(ivPicture);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_UPDATE_USER_CODE && resultCode == Activity.RESULT_OK) {
            initView(view);
        }
    }
}