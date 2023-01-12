package com.example.sneakerstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sneakerstore.model.HttpHandler;
import com.example.sneakerstore.model.ProductSizeQuantity;
import com.example.sneakerstore.model.SneakerSize;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductFormActivity extends AppCompatActivity {
    private ImageView imageInput;
    private TextView uploadImageTap, sizeQuantity;
    private EditText nameInput, desInput, priceInput;
    private Spinner categoryInput, brandInput, sizeInput;
    private String productName, productDescription, productBase64Img;
    private int productCategory, productBrand;
    private double productPrice;
    private Button saveFormBtn;
    private ImageButton addBtn, minusBtn;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> categoryAdapter;
    private ArrayAdapter<String> sizeAdapter;
    private ArrayList<String> sizesStr;
    private ArrayList<ProductSizeQuantity> sizeQuantities;
    private int currentSizeSelected;
    private Intent intent;
    private double[] sizes;
    private String url;
    private boolean isImgChange;
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        // image in update mode variable, if image changed add img to json body
        isImgChange = false;

        // size declaration
        sizesStr = new ArrayList<>();
        sizeQuantities = new ArrayList<>();
        currentSizeSelected = 0;
        sizes = new double[]{5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5};

        // check intent
        intent = getIntent();
        if (intent.getIntExtra("status", 0) == 1) {
            for (int i = 0; i < sizes.length; i++) {
                sizesStr.add(Double.toString(sizes[i]));
                sizeQuantities.add(new ProductSizeQuantity(sizes[i]));
            }
        }else {
            for (int i = 0; i < sizes.length; i++) {
                sizesStr.add(Double.toString(sizes[i]));
            }
            // in update method
            url = MainActivity.ROOT_API + "/product/detail?id=" + intent.getIntExtra("product_id", 1);
            new readJSON().execute();
        }

        productName = "";
        productBrand = 1;
        productBase64Img = "";
        productCategory = 1;
        productPrice = 0;
        productDescription = "";

        imageInput = findViewById(R.id.imageInput);
        imageInput.setVisibility(View.INVISIBLE);
        uploadImageTap = findViewById(R.id.uploadImgView);
        backBtn = findViewById(R.id.productFormBackBtn);
        nameInput = findViewById(R.id.productNameInput);
        desInput = findViewById(R.id.productDescriptionInput);
        priceInput = findViewById(R.id.productPriceInput);
        categoryInput = (Spinner) findViewById(R.id.productCategoryInput);
        brandInput = (Spinner) findViewById(R.id.productBrandInput);
        saveFormBtn = findViewById(R.id.saveFormBtn);
        sizeInput = findViewById(R.id.productSizeInput);
        sizeQuantity = findViewById(R.id.productFormSizeQuantity);
        addBtn = findViewById(R.id.productFormAddBtn);
        minusBtn = findViewById(R.id.productFormMinusBtn);

        // category spinner
        categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, MainActivity.categories);
        categoryInput.setAdapter(categoryAdapter);

        // brand spinner
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, MainActivity.brands);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        // size spinner
        sizeAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sizesStr);
        sizeInput.setAdapter(sizeAdapter);

        brandInput.setAdapter(adapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


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

                if (intent.getIntExtra("status", 0) == 1) {
                    uploadProduct.execute(MainActivity.ROOT_API + "/product/newapi");
                }else {
                    uploadProduct.execute(MainActivity.ROOT_API + "/product/detail?id=" + intent.getIntExtra("product_id", 1));
                }

            }
        });

        // action for size input

        sizeInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                for (int j = 0; j < sizeQuantities.size(); j++) {
                    if (sizeQuantities.get(j).getSizes() == Double.parseDouble(adapterView.getItemAtPosition(i).toString())) {
                        // edit size quantity
                        currentSizeSelected = j;
                        sizeQuantity.setText(Integer.toString(sizeQuantities.get(j).getQuantity()));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sizeQuantities.get(currentSizeSelected).setQuantity(sizeQuantities.get(currentSizeSelected).getQuantity() + 1);
                sizeQuantity.setText(Integer.toString(sizeQuantities.get(currentSizeSelected).getQuantity()));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sizeQuantities.get(currentSizeSelected).getQuantity() - 1 > 0) {
                    sizeQuantities.get(currentSizeSelected).setQuantity(sizeQuantities.get(currentSizeSelected).getQuantity() - 1);
                    sizeQuantity.setText(Integer.toString(sizeQuantities.get(currentSizeSelected).getQuantity()));
                }
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
                    if (intent.getIntExtra("status", 0) == 0) { // if in update mode => set img change to true
                        isImgChange = true;
                    }

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
                if (intent.getIntExtra("status", 0) == 1) { // upload mode => post method
                    connection.setRequestMethod("POST");
                }else { // otherwise patch method
                    connection.setRequestMethod("PATCH");
                }

                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                JSONObject sendingObj = new JSONObject();
                JSONObject jsonData = new JSONObject();

                // setting data
                jsonData.put("name", productName);
                jsonData.put("description", productDescription);
                jsonData.put("price", productPrice);
                jsonData.put("category", productCategory);
                if (intent.getIntExtra("status", 0) == 1) {
                    jsonData.put("picture", productBase64Img);
                }else {
                    if (isImgChange) {
                        jsonData.put("picture", productBase64Img);
                    }
                }

                jsonData.put("brand", productBrand);
                sendingObj.put("info", jsonData);


                System.out.println("Size: " + productBase64Img.length());
                String sizeStrs = "";
                for (int i = 0; i < sizeQuantities.size(); i++) {
                    sizeStrs += sizeQuantities.get(i).getQuantity() + ",";
                }
                sendingObj.put("size", sizeStrs);

                DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                os.writeBytes(sendingObj.toString());
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

    public class readJSON extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return HttpHandler.getMethod(url);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONObject object = new JSONObject(s);
                    nameInput.setText(object.getString("Name"));
                    desInput.setText(object.getString("Description"));
                    priceInput.setText(object.getString("Price"));
                    String pic = MainActivity.ROOT_IMG + object.getString("Picture");
                    uploadImageTap.setVisibility(View.INVISIBLE);
                    imageInput.setVisibility(View.VISIBLE);

                    new HttpHandler.DownloadImageFromInternet(imageInput).execute(pic);
                    String[] sizeArr = object.getString("Quantity").split(",");
                    for (int i = 0; i < MainActivity.categories.size(); i++) {
                        if (MainActivity.categories.get(i).equals(object.getString("Category"))) {

                            categoryInput.setSelection(i);
                        }


                    }

                    for (int i = 0; i < MainActivity.brands.size(); i++) {
                        if (MainActivity.brands.get(i).equals(object.getString("Brand"))) {
                            brandInput.setSelection(i);
                        }
                    }
                    imageInput.setVisibility(View.VISIBLE);
                    sizeQuantity.setText(sizeArr[0]);

                    for (double i = 5; i < 10; i += 0.5) {
                        int index = (int) (i - 5) * 2;
                        sizeQuantities.add(new ProductSizeQuantity(i, Integer.parseInt(sizeArr[index])));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}