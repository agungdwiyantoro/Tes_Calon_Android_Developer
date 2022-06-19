package com.example.tes_calon_android_developer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    private final String URL = "https://fakestoreapi.com/users";
    ModelUser usernamePref = SharedPrefManager.getInstance(this).getUser();

    private TextView tv_name_value, tv_email_value, tv_phone_number_value, tv_address;
    private Button bt_logout;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
        parseApiData();
    }

    void init(){
        tv_name_value = findViewById(R.id.tv_name_value);
        tv_email_value = findViewById(R.id.tv_email_value);
        tv_phone_number_value = findViewById(R.id.tv_phone_value);
        tv_address = findViewById(R.id.tv_address_value);

        bt_logout = findViewById(R.id.bt_logout);
        imageButton = findViewById(R.id.bt_back);
    }

    void setValue(String name, String email, String phone, String address){
        tv_name_value.setText(name);
        tv_email_value.setText(email);
        tv_phone_number_value.setText(phone);
        tv_address.setText(address);

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(Profile.this).logout();
                Intent toLogin = new Intent(Profile.this, Login.class);
                startActivity(toLogin);
                finish();
            }
        });

        imageButton.setOnClickListener(view -> finish());
    }
    public void parseApiData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, response -> {
            Log.e("Res : ", response);
            try{
                JSONArray jsonArray  = new JSONArray(response);
                if(jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonData = jsonArray.getJSONObject(i);

                        String username = jsonData.getString("username");
                        String password = jsonData.getString("password");

                        String email = jsonData.getString("email");
                        String phone = jsonData.getString("phone");

                        JSONObject name = jsonData.getJSONObject("name");
                        JSONObject address = jsonData.getJSONObject("address");

                        String realName = name.getString("firstname") + " " + name.getString("lastname");
                        String realAddress = address.getString("city")
                                + ", " + address.getString("street")
                                + " No. " + address.getString("number")
                                + ", Zipcode " + address.getString("zipcode");


                        Log.d("USERNAME" , usernamePref.getUsername() );

                        if(usernamePref.getUsername().equals(username)) {
                            Log.d("Texxx user", email +", " + username + ", " + password + ", " + name + ", " + address + ", " + phone );
                            //datalList.add(new ModelUser(email, username, password, name, address, phone));
                            setValue(realName, email, phone, realAddress);
                        }
                    }

                }


            }catch (Exception e){
                e.printStackTrace();
            }


        }, error -> {

        });
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
       // RequestQueue requestQueue = Volley.newRequestQueue(this);
       // requestQueue.add(stringRequest);
    }
}