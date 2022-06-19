package com.example.tes_calon_android_developer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.math.BigDecimal;

public class BuyProduct extends AppCompatActivity implements View.OnClickListener{

    private TextView title, description, category, price, count, rate, freeDelivery, bestSeller;
    private ImageView image;
    private RatingBar rbStar;
    private ImageButton back, chat, chart;
    private Button buyNow;

    Toast message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_product);
        String strTitle = getIntent().getExtras().getString("title");
        String strCategory = getIntent().getExtras().getString("category");
        String strImageURL = getIntent().getExtras().getString("imageURL");
        String strDescription = getIntent().getExtras().getString("description");
        String strRate = getIntent().getExtras().getString("rate");
        String strPrice = getIntent().getExtras().getString("price");
        String strCount = getIntent().getExtras().getString("count");
        Log.d("title", strTitle + ", " + strCategory);

        init();

        title.setText(strTitle);
        description.setText(strDescription);
        category.setText(strCategory);
        price.setText("$ " + strPrice);
        count.setText(strCount);
        Glide.with(this).load(strImageURL).apply(new RequestOptions().override(800, 400)).into(image);
        rbStar.setRating(Float.parseFloat(strRate));
        rate.setText(strRate);
        back.setOnClickListener(this);
        buyNow.setOnClickListener(this);
        chat.setOnClickListener(this);
        chart.setOnClickListener(this);

        //parsing
        BigDecimal parsedPrice = new BigDecimal(strPrice);
        BigDecimal parsedRate = new BigDecimal(strRate);
        int parsedCount = Integer.parseInt(strCount);

        /*Conditions*/
        //Free Delivery
        if(parsedPrice.compareTo(new BigDecimal(100))==1){
            freeDelivery.setVisibility(View.INVISIBLE);
        }
        else if (parsedPrice.compareTo(new BigDecimal(100))==0){
            freeDelivery.setVisibility(View.INVISIBLE);
        }
        else{
            freeDelivery.setVisibility(View.VISIBLE);
        }

        //Best Seller
        if(parsedRate.compareTo(new BigDecimal(4.0))==1 && parsedCount>200){
            bestSeller.setVisibility(View.VISIBLE);
        }
        else if (parsedRate.compareTo(new BigDecimal(4.0))==0 && parsedCount>200){
            bestSeller.setVisibility(View.INVISIBLE);
        }
        else{
            bestSeller.setVisibility(View.INVISIBLE);
        }

        message = Toast.makeText(this, "Donkey ain't got no time to code this", Toast.LENGTH_SHORT);
    }

    void init(){
        title = findViewById(R.id.tv_title);
        description = findViewById(R.id.tv_description);
        category = findViewById(R.id.tv_category);
        price = findViewById(R.id.tv_price);
        count = findViewById(R.id.tv_sold);
        image = findViewById(R.id.iv_image);
        rbStar = findViewById(R.id.rb_star);
        rate = findViewById(R.id.tv_rating_value);
        freeDelivery = findViewById(R.id.tv_free_delivery);
        bestSeller = findViewById(R.id.tv_best_seller);
        back = findViewById(R.id.bt_back);
        buyNow = findViewById(R.id.bt_buy_now);
        chat = findViewById(R.id.bt_chat);
        chart = findViewById(R.id.bt_chart);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_back:
                finish();
                break;

            case R.id.bt_chat:
                message.show();
                break;
            case R.id.bt_chart:
                message.show();
                break;
            case R.id.bt_buy_now:
                message.show();
                break;
        }

    }
}