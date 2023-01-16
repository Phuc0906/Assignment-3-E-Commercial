package com.example.sneakerstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sneakerstore.adapter.SpinnerAdapter;
import com.example.sneakerstore.model.HttpHandler;
import com.example.sneakerstore.model.User;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private EditText profileName, profileEmail, profilePhone, profileAddress;
    private ImageView profileImg;
    private Button profileSubmitBtn, profileCancelBtn;
    private boolean isImgChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profile_name);
        profileAddress = findViewById(R.id.profile_address);
        profileEmail = findViewById(R.id.profile_email);
        profilePhone = findViewById(R.id.profile_phone);
        profileImg = findViewById(R.id.userProfileImageView);
        profileSubmitBtn = findViewById(R.id.profileSubmitButton);
        profileCancelBtn = findViewById(R.id.profileCancelButton);
        isImgChange = false;

        profileEmail.setEnabled(false);
        profileEmail.setText(MainActivity.user.getEmail());
        profileAddress.setText(MainActivity.user.getAddress());
        profileName.setText(MainActivity.user.getName());
        profilePhone.setText(MainActivity.user.getPhone());
        System.out.println("CUrrent id: " + MainActivity.user.getId());
        new HttpHandler.DownloadImageFromInternet(profileImg).execute(MainActivity.ROOT_IMG + MainActivity.user.getImage());

        // if user press image => change profile image
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgIntent = new Intent(Intent.ACTION_PICK);
                imgIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imgIntent, 1000);
            }
        });

        profileSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.user.setAddress(profileAddress.getText().toString());
                MainActivity.user.setName(profileName.getText().toString());
                MainActivity.user.setPhone(profilePhone.getText().toString());

                try {
                    MainActivity.user.updateUser(isImgChange);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        profileCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                // convert image to URI
                final Uri imageUri = data.getData();
                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    profileImg.setImageBitmap(selectedImage);
                    MainActivity.user.setPictureData(encodeImage(selectedImage));
                    isImgChange = true;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }
}