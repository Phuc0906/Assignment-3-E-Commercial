package com.example.sneakerstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sneakerstore.sneaker.Sneaker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class ProductFormActivity extends AppCompatActivity {
    ImageView imageInput;
    TextView uploadImageTap;
    EditText nameInput, desInput, priceInput;
    Spinner categoryInput, brandInput;
    String productName, productDescription, productBase64Img;
    int productCategory, productBrand;
    double productPrice;
    Button saveFormBtn;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        productName = "";
        productBrand = 1;
        productBase64Img = "";
        productCategory = 1;
        productPrice = 0;
        productDescription = "";

        imageInput = findViewById(R.id.imageInput);
        imageInput.setVisibility(View.INVISIBLE);
        uploadImageTap = findViewById(R.id.uploadImgView);

        nameInput = findViewById(R.id.productNameInput);
        desInput = findViewById(R.id.productDescriptionInput);
        priceInput = findViewById(R.id.productPriceInput);
        categoryInput = (Spinner) findViewById(R.id.productCategoryInput);
        brandInput = (Spinner) findViewById(R.id.productBrandInput);
        saveFormBtn = findViewById(R.id.saveFormBtn);


        categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, MainActivity.categories);

        categoryInput.setAdapter(categoryAdapter);


        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, MainActivity.brands);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);


        brandInput.setAdapter(adapter);


        // set action for image getting from gallery
        uploadImageTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgIntent = new Intent(Intent.ACTION_PICK);
                imgIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imgIntent, 1000);
            }
        });

        imageInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgIntent = new Intent(Intent.ACTION_PICK);
                imgIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imgIntent, 1000);
            }
        });

        // set action for each edit text
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                productName = editable.toString();
            }
        });

        desInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                productDescription = editable.toString();
            }
        });

        priceInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {
                    productPrice = Double.parseDouble(editable.toString());
                }

            }
        });

        categoryInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                productCategory = MainActivity.categoriesHashMap.get(adapterView.getItemAtPosition(i).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        brandInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                productBrand = MainActivity.brandsHashMap.get(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        saveFormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productName = nameInput.getText().toString();
                productDescription = desInput.getText().toString();
                productPrice = Double.parseDouble(priceInput.getText().toString());

                UploadProduct uploadProduct = new UploadProduct();
                uploadProduct.execute(MainActivity.ROOT_API + "/product/newapi");
            }
        });

    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                uploadImageTap.setVisibility(View.INVISIBLE);
                imageInput.setVisibility(View.VISIBLE);

                // convert image to URI
                final Uri imageUri = data.getData();
                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imageInput.setImageBitmap(selectedImage);
                    productBase64Img = encodeImage(selectedImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class UploadProduct extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String status = "0";
            URL url = null;
            try {
                url = new URL(urls[0]);

                // Uploading process
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                JSONObject jsonData = new JSONObject();

                // setting data
                jsonData.put("name", productName);
                jsonData.put("description", productDescription);
                jsonData.put("price", productPrice);
                jsonData.put("category", productCategory);
                jsonData.put("picture", productBase64Img);
                jsonData.put("brand", productBrand);

                System.out.println(jsonData);
                System.out.println("Size: " + productBase64Img.length());

                DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                os.writeBytes(jsonData.toString());
                status = Integer.toString(connection.getResponseCode());
                os.flush();
                os.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Log.i("INFO", status);
            return status;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            nameInput.setText("");
            priceInput.setText("0.0");
            desInput.setText("");

        }
    }
}