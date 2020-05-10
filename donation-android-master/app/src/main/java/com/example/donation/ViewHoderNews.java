package com.example.donation;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHoderNews extends RecyclerView.ViewHolder {
    View mview;
    private ViewHoderNews.ClickListener mClickListener;

    public ViewHoderNews(@NonNull View itemView) {
        super(itemView);

        mview = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                mClickListener.onItemClick(view, getAdapterPosition());
                return true;
            }
        });
    }

    public void setDetails(Context ctx, String topic, String detail, String date, String uploader) { //String image ใช้กับคู่ Picasso.get().load(image).into(mImage);, String uploader

        TextView mName = mview.findViewById(R.id.tName);
        TextView mdatail = mview.findViewById(R.id.tDatail);
        TextView mdate = mview.findViewById(R.id.tDate);
        ImageView mImage = mview.findViewById(R.id.tImage);

        mName.setText(topic);
        mdatail.setText(detail);
        mdate.setText(date);

        if (!uploader.isEmpty()) {
            Picasso.get().load(uploader).into(mImage);
        }
    }

    public void setOnClickListener(ViewHoderNews.ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickListener {

        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}
