package com.example.sneakerstore.model;

import android.os.AsyncTask;

import com.example.sneakerstore.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String Gender;
    private String DOB;
    private int point;
    private String password;
    private String Role;
    private String Email;
    private String image;
    private JSONObject object;
    private String pictureData;

    public User(int id, String name, String address, String phone, String gender, String DOB, int point, String password, String role, String email, String image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        Gender = gender;
        this.DOB = DOB;
        this.point = point;
        this.password = password;
        Role = role;
        Email = email;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void updateUser(boolean isPictureChange) throws JSONException {
        object = new JSONObject();
        object.put("userid", id);
        object.put("name", name);
        object.put("address", address);
        object.put("phone", phone);
        if (isPictureChange) {
            object.put("picture", pictureData);
        }
        new UpdateUserInformation().execute();
    }

    public void setPictureData(String pictureData) {
        this.pictureData = pictureData;
    }

    public void reload() {
        new ReloadUser().execute();
    }

    public class ReloadUser extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return HttpHandler.getMethod(MainActivity.ROOT_API + "/user/information?id=" + id);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject object = null;
            try {
                object = new JSONObject(s);
                point = object.getInt("POINT");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class UpdateUserInformation extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                return HttpHandler.patchMethod(MainActivity.ROOT_API + "/user/information", object);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}
