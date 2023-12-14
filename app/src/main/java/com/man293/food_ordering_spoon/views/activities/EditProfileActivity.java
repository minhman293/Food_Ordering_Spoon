package com.man293.food_ordering_spoon.views.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.models.User;
import com.man293.food_ordering_spoon.utils.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ShapeableImageView imageView;
    private File selectedFile;
    private EditText firstNameEditText,lastNameEditText,phoneNumberEditText,addressEditText, newPasswordEditText, confirmNewPasswordEditText, currentPasswordEditText;
    private  User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Button bt_update_profile = findViewById(R.id.button_update);
        imageView = findViewById(R.id.profile_picture);
        ImageButton bt_exit = findViewById(R.id.button_back_edit_profile);
        firstNameEditText = findViewById(R.id.edt_first_name);
        lastNameEditText = findViewById(R.id.edt_last_name);
        phoneNumberEditText = findViewById(R.id.edt_phone_number);
        addressEditText = findViewById(R.id.edt_add_edit_profile);
        newPasswordEditText = findViewById(R.id.edt_new_pass_edit_profile);
        confirmNewPasswordEditText = findViewById(R.id.edt_confirm_new_pass_edit_profile);
        currentPasswordEditText = findViewById(R.id.edt_current_new_pass_edit_profile);

        bt_exit.setOnClickListener(v -> {
            super.onBackPressed();
            onBackPressed();
        });
        // choose image
        imageView.setOnClickListener(view -> {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT );
//            intent.setType("image/*");
//            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);

        });

        currentUser = User.getCurrentUser(EditProfileActivity.this);
        if(currentUser == null) return;

        initValue();
        bt_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String firstName =  String.valueOf(firstNameEditText.getText()).trim();
                    String lastName =  String.valueOf(lastNameEditText.getText()).trim();
                    String address =  String.valueOf(addressEditText.getText()).trim();
                    String phoneNumber =  String.valueOf(phoneNumberEditText.getText()).trim();
                    String newPassword =  String.valueOf(newPasswordEditText.getText()).trim();
                    String currentPassword =  String.valueOf(currentPasswordEditText.getText()).trim();

                    if(phoneNumber.isEmpty() || currentPassword.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() ) {
                        Toast.makeText(EditProfileActivity.this, "Enter information before confirming!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String url = getString(R.string.BASE_URL) + getString(R.string.API_USER_UPDATE__POST, currentUser.getId());
                    EditProfileActivity.UpdateProfileTask task = new EditProfileActivity.UpdateProfileTask(
                            firstName,lastName, phoneNumber,address,
                             newPassword, currentPassword,
                            currentUser.getPicture(),selectedFile);
                    task.execute(url);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    private void initValue() {
        firstNameEditText.setText(currentUser.getFirstName());
        lastNameEditText.setText(currentUser.getLastName());
        phoneNumberEditText.setText(currentUser.getPhoneNum());
        addressEditText.setText(currentUser.getAddress());
        String imageSrc = currentUser.getPicture().contains("http")
                ? currentUser.getPicture()
                : getString(R.string.BASE_URL) + getString(R.string.PUBLIC_IMAGES, currentUser.getPicture());
        Glide.with(EditProfileActivity.this).load(imageSrc).into(imageView);
    }
    private class UpdateProfileTask extends AsyncTask<String, Integer, User> {

        private String firstName, lastName, phoneNumber, address, password, imageSrc, currentPassword;
        private File imageFile;

        public UpdateProfileTask( String firstName, String lastName, String phoneNumber, String address, String password, String currentPassword, String imageSrc, File imageFile) {

            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
            this.address = address;
            this.password = password;
            this.currentPassword = currentPassword;
            this.imageSrc = imageSrc;
            this.imageFile = imageFile;
        }
        @Override
        protected User doInBackground(String... strings) {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody;
                // Create a MultipartBody for sending both text and image data
                if(imageFile != null) {
                    requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("firstName", this.firstName)
                            .addFormDataPart("lastName", this.lastName)
                            .addFormDataPart("phone", this.phoneNumber)
                            .addFormDataPart("address", this.address)
                            .addFormDataPart("password", this.password)
                            .addFormDataPart("currentPassword", this.currentPassword)
                            .addFormDataPart("image", "profile.jpg", RequestBody.create(MediaType.parse("image/*"), this.imageFile))
                            .build();
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("firstName", this.firstName);
                    data.put("lastName", this.lastName);
                    data.put("phone", this.phoneNumber);
                    data.put("address", this.address);
                    data.put("password", this.password);
                    data.put("currentPassword", this.currentPassword);
                    data.put("image", this.imageSrc);
                    requestBody = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(data));
                }

                Request req = new Request.Builder()
                        .url(strings[0])
                        .post(requestBody)
                        .build();

                Response res = client.newCall(req).execute();

                // Log the response code for debugging
                Log.d(TAG, "Response Code: " + res.code());

                // Kiểm tra giá trị trước khi xử lý phản hồi từ server
                if (res.body() != null && res.isSuccessful()) {
                    String jsonResponse = res.body().string();
                    Log.d(TAG, "JSON Response: " + jsonResponse);

                    try {
                        JSONObject userJson = new JSONObject(jsonResponse);
                        User user = new User(
                                userJson.getString("_id"),
                                userJson.getString("firstName"),
                                userJson.getString("lastName"),
                                userJson.getString("phone"),
                                userJson.getString("address"),
                                userJson.getInt("role"),
                                userJson.getString("picture")
                        );
                        return user;
                    } catch (JSONException e) {
                        // Xử lý ngoại lệ khi có lỗi trong quá trình đọc JSON
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    if(res.body() != null) {
                        Log.e(TAG, "Error Body: " + res.body().string());
                    }
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            onUpdated(user);
        }
    }
    private void onUpdated(User user) {
        if (user != null) {
            User.saveCurrentUser(EditProfileActivity.this, user);
            setResult(Activity.RESULT_OK);
            finish();
        } else {
            Toast.makeText(EditProfileActivity.this,"Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            selectedFile = FileUtils.uriToFile(EditProfileActivity.this,selectedImageUri);
            Log.d("FILE_SELECTED", String.valueOf(selectedFile));
            imageView.setImageURI(selectedImageUri);
        }
    }

}