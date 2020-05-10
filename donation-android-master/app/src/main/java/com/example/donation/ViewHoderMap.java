package com.example.donation;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHoderMap extends RecyclerView.ViewHolder {

    View mview;

    public ViewHoderMap(@NonNull View itemView) {
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
                return true; //false //New Code 14/01/63
            }
        });
    }

    public void setDetails(Context ctx, String name, String latitude, String longitude) { //String image ใช้กับคู่ Picasso.get().load(image).into(mImage);

        TextView mName = mview.findViewById(R.id.rName);
        TextView MLatitude = mview.findViewById(R.id.rLatitude);
        TextView mLongitude = mview.findViewById(R.id.rLongitude);

//        ImageView mImage = mview.findViewById(R.id.rImage);

        mName.setText(name);
        MLatitude.setText(latitude);
        mLongitude.setText(longitude);


//        Picasso.get().load(image).into(mImage);

    }

    private ClickListener mClickListener;


    public interface ClickListener {

        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public void setOnClickListener(ViewHoderMap.ClickListener clickListener) {
        mClickListener = clickListener;
    }

}
