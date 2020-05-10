package com.example.donation.ViewHoder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donation.R;

public class ViewHolderPlace extends RecyclerView.ViewHolder {

    View mview;

    public ViewHolderPlace(@NonNull View itemView) {
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

    public void setDetails(Context ctx, String name, String adress, String phonenumber, Double avgRating, Integer numRating) { //String image ใช้กับคู่ Picasso.get().load(image).into(mImage);

        TextView mName = mview.findViewById(R.id.rName);
        TextView madress = mview.findViewById(R.id.rAdress);//add New
        TextView mphonenumber = mview.findViewById(R.id.rPhonenumber);//add New
        TextView nurating = mview.findViewById(R.id.rAvg);
        TextView numcomments = mview.findViewById(R.id.rNumberReview);

//        ImageView mImage = mview.findViewById(R.id.rImage);

        mName.setText(name);
        madress.setText(adress);
        mphonenumber.setText(phonenumber);

        if (avgRating == null) {
            nurating.setText("0");
        } else {
            nurating.setText(String.valueOf(avgRating));
        }

        if (numRating == null) {
            numcomments.setText("0");
        } else {
            numcomments.setText(String.valueOf(numRating));
        }


//        Picasso.get().load(image).into(mImage);

    }

    private ViewHolderPlace.ClickListener mClickListener;


    public interface ClickListener {

        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public void setOnClickListener(ViewHolderPlace.ClickListener clickListener) {

        mClickListener = clickListener;
    }

}
