package com.example.tes_calon_android_developer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    ArrayList<ModelProduct> datalList = new ArrayList<ModelProduct>();

    MyRecyclerViewAdapter adapter;
    String URL = "https://fakestoreapi.com/products";

    private RecyclerView recyclerView;
    private ImageView profile;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_recyclerView);
        profile = findViewById(R.id.iv_profile);
        searchView = findViewById(R.id.search_bar);
        profile.setOnClickListener(view -> {
            Intent toProfile = new Intent(MainActivity.this, Profile.class);
            startActivity(toProfile);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText);
                return false;
            }
        });
        parseApiData();
    }

    public void parseApiData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, response -> {
            Log.e("Res : ", response);
            try{
                JSONArray jsonArray  = new JSONArray(response);
                if(jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonData = jsonArray.getJSONObject(i);

                        String id = jsonData.getString("id");
                        String imageURL = jsonData.getString("image");
                        String title = jsonData.getString("title");
                        String description = jsonData.getString("description");
                        String category = jsonData.getString("category");
                        String price = jsonData.getString("price");
                        JSONObject rating = jsonData.getJSONObject("rating");
                        String rate = rating.getString("rate");
                        String count = rating.getString("count");

                        Log.d("Texxx", imageURL +", " + title + ", " + category + ", " + rate + ", " + count );
                        datalList.add(new ModelProduct(id, imageURL, title, description, category, price, rate, count));
                    }

                    recycleViewAdapter(datalList);
                }


            }catch (Exception e){
                e.printStackTrace();
            }


        }, error -> {

        });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        //requestQueue.add(stringRequest);
    }

    public void recycleViewAdapter(ArrayList<ModelProduct> products){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, products);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }



    @Override
    public void onItemClick(View view, int position) {

        Log.d("ID ID ", adapter.getItem(position));
        Intent toBuyNow = new Intent(MainActivity.this, BuyProduct.class);
        Bundle b = new Bundle();

        b.putString("imageURL", datalList.get(position).getImageURL());
        b.putString("title", datalList.get(position).getTitle());
        b.putString("description", datalList.get(position).getDescription());
        b.putString("category", datalList.get(position).getCategory());
        b.putString("price", datalList.get(position).getPrice());
        b.putString("rate", datalList.get(position).getRate());
        b.putString("count", datalList.get(position).getCount());
        toBuyNow.putExtras(b);
        startActivity(toBuyNow);
    }

    private void filter(String text) {
        ArrayList<ModelProduct> filteredlist = new ArrayList<>();
        for (ModelProduct item : datalList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                Log.d("CARI", item.getTitle());
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
    }

}