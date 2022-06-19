package com.example.tes_calon_android_developer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText email, password;
    private Button login;
    private TextView forgotPassword;

    private final String URL = "https://fakestoreapi.com/auth/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        init();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()){
                    userLogin();
                }
                else
                {
                    showMessageDialog("Error", "Check your Internet Connection..!");
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Donkey ain't got no time to code this", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void init(){
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        login = findViewById(R.id.bt_login);
        forgotPassword = findViewById(R.id.tv_forgot_password);
    }



    private void userLogin() {
        final String str_username = email.getText().toString();
        final String str_password = password.getText().toString();

        if (TextUtils.isEmpty(str_username)) {
            email.setError("Please enter your username");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(str_password)) {
            password.setError("Please enter your password");
            password.requestFocus();
            return;
        }

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                response -> {
                    Log.e("anyText", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d("Token", jsonObject.getString("token"));
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(str_username);
                        Intent i = new Intent(Login.this,MainActivity.class);
                        startActivity(i);
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error" + e, Toast.LENGTH_LONG).show();
                    }
                }, error -> {
                    // pdDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Wrong username or password!", Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", str_username);
                params.put("password", str_password);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    public void showMessageDialog(String title , String Message)
    {
        AlertDialog dialog = new AlertDialog.Builder(Login.this)
                .setTitle(title)
                .setMessage(Message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();

                    }
                })

                .show();


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
/*
    private void postDataUsingVolley(String name, String job) {
        // url to post our data
        String url = "https://reqres.in/api/users";
        //loadingPB.setVisibility(View.VISIBLE);

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Login.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // inside on response method we are
                // hiding our progress bar
                // and setting data to edit text as empty
                //loadingPB.setVisibility(View.GONE);
                nameEdt.setText("");
                jobEdt.setText("");

                // on below line we are displaying a success toast message.
                Toast.makeText(Login.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String name = respObj.getString("name");
                    String job = respObj.getString("job");

                    // on below line we are setting this string s to our text view.
                    responseTV.setText("Name : " + name + "\n" + "Job : " + job);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(MainActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("name", name);
                params.put("job", job);

                // at last we are
                // returning our params.
                return params;
            }
        };




    public void userLogin(){

        final String str_email = email.getText().toString();
        final String str_password = password.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(str_email)) {
            email.setError("Please enter your username");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(str_password)) {
            password.setError("Please enter your password");
            password.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, response -> {
            Log.e("Res : ", response);
            try{
                JSONArray jsonArray  = new JSONArray(response);
                if(jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonData = jsonArray.getJSONObject(i);

                        String email = jsonData.getString("email");
                        String username = jsonData.getString("username");
                        String password = jsonData.getString("password");
                        String name = jsonData.getString("name");
                        String address = jsonData.getString("address");
                        String phone = jsonData.getString("phone");
                        //JSONObject rating = jsonData.getJSONObject("rating");
                        //String rate = rating.getString("rate");
                        //String count = rating.getString("count");



                        Log.d("Texxx user", email +", " + username + ", " + password + ", " + name + ", " + address + ", " + phone );
                        datalList.add(new ModelUser(email, username, password, name, address, phone));
                    }

                    //recycleViewAdapter(datalList);
                }


            }catch (Exception e){
                e.printStackTrace();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", str_email);
                params.put("password", str_password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
}


*/



