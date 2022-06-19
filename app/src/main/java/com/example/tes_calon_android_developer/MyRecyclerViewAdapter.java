package com.example.tes_calon_android_developer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ModelProduct> mData, mFilteredList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    MyRecyclerViewAdapter(Context context, ArrayList<ModelProduct> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    public void filterList(ArrayList<ModelProduct> filterlist) {
        mData = filterlist;
        mFilteredList = filterlist;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycle_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String imageURL = mData.get(position).imageURL;
        String title = mData.get(position).title;
        String description = mData.get(position).description;
        String category = mData.get(position).category;
        String price = mData.get(position).price;
        String rate = mData.get(position).rate;
        String count = mData.get(position).count;
        String strCount = count + " pcs";

        Glide.with(context).load(imageURL).apply(new RequestOptions().override(800, 400)).into(holder.image);
        holder.title.setText(title);
        holder.description.setText(description);
        holder.category.setText(category);
        holder.price.setText("$ " + price);
        holder.rbStar.setRating(Float.parseFloat(rate));
        holder.ratingValue.setText(rate);
        holder.sold.setText(strCount);

        //parsing
        BigDecimal parsedPrice = new BigDecimal(price);
        BigDecimal parsedRate = new BigDecimal(rate);
        int parsedCount = Integer.parseInt(count);

        /*Conditions*/
        //Free Delivery
        if(parsedPrice.compareTo(new BigDecimal(100))==1){
            holder.freeDelivery.setVisibility(View.INVISIBLE);
        }
        else if (parsedPrice.compareTo(new BigDecimal(100))==0){
            holder.freeDelivery.setVisibility(View.INVISIBLE);
        }
        else{
            holder.freeDelivery.setVisibility(View.VISIBLE);
        }

        //Best Seller
        if(parsedRate.compareTo(new BigDecimal(4.0))==1 && parsedCount>200){
            holder.bestSeller.setVisibility(View.VISIBLE);
        }
        else if (parsedRate.compareTo(new BigDecimal(4.0))==0 && parsedCount>200){
            holder.bestSeller.setVisibility(View.INVISIBLE);
        }
        else{
            holder.bestSeller.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView title, description, category, price, freeDelivery, bestSeller, ratingValue, sold;
        RatingBar rbStar;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_image);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            category = itemView.findViewById(R.id.tv_category);
            price = itemView.findViewById(R.id.tv_price);
            freeDelivery = itemView.findViewById(R.id.tv_free_delivery);
            bestSeller = itemView.findViewById(R.id.tv_best_seller);
            rbStar = itemView.findViewById(R.id.rb_star);
            ratingValue = itemView.findViewById(R.id.tv_rating_value);
            sold = itemView.findViewById(R.id.tv_sold);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    String getItem(int id) {
        return mData.get(id).id;
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}