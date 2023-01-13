package com.example.sneakerstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sneakerstore.model.HttpHandler;
import com.example.sneakerstore.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    ImageView arrow, userRegisterImg;
    TextView textView;
    EditText email, password;
    TextView wrongAlert, registerAccount;
    Button cancelButton, registerButton;
    EditText registerName, registerAddress, registerPhone, registerEmail, registerPassword, registerConfirmPassword;
    TextView emailAlert, passwordAlert;
    int val;
    ConstraintLayout registerLayout;
    JSONObject object;
    public static SharedPreferences sharePref;
    String userImgBase64;

    public static ArrayList<String> brands;
    public static HashMap<String, Integer> brandsHashMap;
    public static ArrayList<String> categories;
    public static HashMap<String, Integer> categoriesHashMap;

    public static User appUser;

    public static final String ROOT_API = "https://mappingapi-372807.as.r.appspot.com";
    public static final String ROOT_IMG = "https://ass3-android-bucket.s3.ap-southeast-1.amazonaws.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        appUser = new User(1);
        userImgBase64 = "";
        categoriesHashMap = new HashMap<>();
        categories = new ArrayList<>();
        brands = new ArrayList<>();
        brandsHashMap = new HashMap<>();
        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        registerAccount = findViewById(R.id.registerAccount);
        wrongAlert = findViewById(R.id.wrongAlert);
        wrongAlert.setVisibility(View.INVISIBLE);
        registerLayout = findViewById(R.id.registerLayout);
        cancelButton = findViewById(R.id.cancelRegister);
        registerButton = findViewById(R.id.registerButton);
        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPhone = findViewById(R.id.registerPhone);
        registerConfirmPassword = findViewById(R.id.registerPasswordConfirm);
        registerPassword = findViewById(R.id.registerPasswordConfirm);
        registerAddress = findViewById(R.id.registerAddress);
        emailAlert = findViewById(R.id.emailAlert);
        passwordAlert = findViewById(R.id.passwordAlert);
        userRegisterImg = findViewById(R.id.user_register_img);


        userRegisterImg.setImageResource(R.drawable.user_icon);

        emailAlert.setVisibility(View.INVISIBLE);
        passwordAlert.setVisibility(View.INVISIBLE);

        registerLayout.setY(2000);

        userRegisterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgIntent = new Intent(Intent.ACTION_PICK);
                imgIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imgIntent, 1000);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerLayout.animate().translationYBy(2000).setDuration(1000);
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                wrongAlert.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                wrongAlert.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        registerAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerLayout.setVisibility(View.VISIBLE);
                registerLayout.animate().translationYBy(-2000).setDuration(1000);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registerConfirmPassword.getText().toString().length() != 0 && registerPassword.getText().toString().length() != 0) {
                    if (registerConfirmPassword.getText().toString().equals(registerPassword.getText().toString())) {
                        VerifyEmail verifyEmail = new VerifyEmail();
                        verifyEmail.execute(ROOT_API + "/user/email?email=" + registerEmail.getText().toString());
                    }else {
                        passwordAlert.setVisibility(View.VISIBLE);
                    }
                }else {
                    passwordAlert.setVisibility(View.VISIBLE);
                }
            }
        });

        // get share preferences value
        sharePref = this.getPreferences(Context.MODE_PRIVATE);

        int loginStatus = sharePref.getInt("session", 0); // if 0 => user is not in session, 1 => in session
//        int loginStatus = 0;
        int role = sharePref.getInt("role", 0); // 0: user, 1: admin

        DownloadCategory downloadCategory = new DownloadCategory();
        downloadCategory.execute(ROOT_API + "/product/category");
        DownloadBrand downloadBrand = new DownloadBrand();
        downloadBrand.execute(ROOT_API + "/product/brand");

        initialize();
        setEvent();
        System.out.println("LOgin stat: " + loginStatus);
        if (loginStatus == 0) {
            System.out.println("LO gin page");
            initialize();
            setEvent();
        }else {
            if (role == 0) {
                startActivity(new Intent(MainActivity.this, HomePage.class));
            }else {
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
            }
        }

    }

    private void initialize() {
        seekBar = findViewById(R.id.seekBar);
        arrow = findViewById(R.id.arrow);
        textView = findViewById(R.id.slideText);

    }

    private void setEvent() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                val = i;
                if (i >= 15) {
                    arrow.setVisibility(View.INVISIBLE);
                }
                else {
                    arrow.setVisibility(View.VISIBLE );
                }

                if (i >= 40) {
                    textView.setVisibility(View.INVISIBLE);
                } else {
                    textView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (val < seekBar.getMax()) {
                    seekBar.setProgress(0);
                } else {
                    if ((email.getText().toString().length() == 0) || (password.getText().toString().length() == 0)) {
                        wrongAlert.setText("Please Enter Login Information");
                        wrongAlert.setVisibility(View.VISIBLE);
                    }else {
                        VerifyUser verifyUser = new VerifyUser();
                        verifyUser.execute(ROOT_API + "/user/login?email=" + email.getText().toString() + "&password=" + password.getText().toString());
                    }

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            seekBar.setProgress(0);
                        }
                    }, 300);
                }
            }
        });
    }

    public class DownloadBrand extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                Log.i("Re", urls[0]);
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            }catch (Exception e) {
                e.printStackTrace();

                return null;
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                System.out.println(s);
                brands.clear();
                JSONArray jsonArr = new JSONArray(s);
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject brandObj = jsonArr.getJSONObject(i);
                    brands.add(brandObj.getString("NAME"));
                    brandsHashMap.put(brandObj.getString("NAME") , brandObj.getInt("ID"));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class DownloadCategory extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                Log.i("Re", urls[0]);
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

            }catch (Exception e) {
                e.printStackTrace();

                return null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                System.out.println(s);
                JSONArray jsonArr = new JSONArray(s);
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject categoryObj = jsonArr.getJSONObject(i);
                    categories.add(categoryObj.getString("NAME"));
                    categoriesHashMap.put(categoryObj.getString("NAME") , categoryObj.getInt("ID"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class VerifyUser extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                Log.i("Re", urls[0]);
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

            }catch (Exception e) {
                e.printStackTrace();

                return null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                System.out.println(s);
                JSONObject userVerify = new JSONObject(s);
                if (userVerify.getInt("verify") == 1) {
                    SharedPreferences sharePref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharePref.edit();
                    editor.putInt("session", 1);
                    if (userVerify.getString("role").equals("USER")) {
                        startActivityForResult(new Intent(MainActivity.this, HomePage.class), 109);
                        editor.putInt("role", 0);
                    }else {
                        startActivityForResult(new Intent(MainActivity.this, AdminActivity.class), 109);
                        editor.putInt("role", 1);
                    }
                    editor.apply();
                }else {
                    wrongAlert.setText("Wrong email or password");
                    wrongAlert.setVisibility(View.VISIBLE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class VerifyEmail extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                Log.i("Re", urls[0]);
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

            }catch (Exception e) {
                e.printStackTrace();

                return null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                System.out.println(s);
                JSONObject emailVerify = new JSONObject(s);
                if (emailVerify.getInt("has") ==  1) {
                    emailAlert.setVisibility(View.VISIBLE);

                }else {
                    if ((registerName.getText().toString().length() > 0) && (registerAddress.getText().toString().length() > 0) && (registerPhone.getText().toString().length() > 0) && (registerPassword.getText().toString().length() > 0)) {
                        object = new JSONObject();
                        object.put("name", registerName.getText().toString());
                        object.put("address", registerAddress.getText().toString());
                        object.put("phone", registerPhone.getText().toString());
                        object.put("password", registerPassword.getText().toString());
                        object.put("email", registerEmail.getText().toString());
                        object.put("gender", "Male");

                        object.put("img", userImgBase64);
                        RegisterUser registerUser = new RegisterUser();
                        registerUser.execute(ROOT_API + "/user/infomation");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class RegisterUser extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return HttpHandler.postMethod(urls[0], object);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "0";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (Integer.parseInt(s) == 200) {
                registerLayout.animate().translationYBy(2000).setDuration(1000);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 109) {
            if (resultCode == RESULT_OK) {
                email.setText("");
                password.setText("");
                System.out.println("Kill activity-----------------------------------------------------------------------");
                SharedPreferences sharePref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharePref.edit();
                editor.remove("session");
                editor.putInt("session", 0);
                editor.apply();
                editor.commit();
            }
        }else if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                final Uri imageUri = data.getData();
                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    userRegisterImg.setImageBitmap(selectedImage);
                    userImgBase64 = encodeImage(selectedImage);

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