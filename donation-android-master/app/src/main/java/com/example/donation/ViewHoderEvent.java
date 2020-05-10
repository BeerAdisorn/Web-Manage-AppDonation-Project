package com.example.donation;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHoderEvent extends RecyclerView.ViewHolder {

    View mview;

    public ViewHoderEvent(@NonNull View itemView) {
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

    public void setDetails(Context ctx, String nameevent, String detail, String dt) { //String image ใช้กับคู่ Picasso.get().load(image).into(mImage);

        TextView mNameevent = mview.findViewById(R.id.rName);
        TextView mdatail = mview.findViewById(R.id.rDatail);
        TextView mdate = mview.findViewById(R.id.rDate);

//        ImageView mImage = mview.findViewById(R.id.rImage);

        mNameevent.setText(nameevent);
        mdatail.setText(detail);
        mdate.setText(dt);

//        Picasso.get().load(image).into(mImage);

    }

    private ClickListener mClickListener;



    public interface ClickListener {

        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public void setOnClickListener(ViewHoderEvent.ClickListener clickListener) {
        mClickListener = clickListener;
    }

}