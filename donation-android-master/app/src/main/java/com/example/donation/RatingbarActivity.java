package com.example.donation;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class RatingbarActivity extends AppCompatActivity  { //<==implements RatingBar.OnRatingBarChangeListener

//    private TextView ambilrating;
//    private Button submit;
//    private AppCompatRatingBar Ratingbar;
//
//    double valueRating;
//
//    RatingBar ratingBar;

    int count = 0;
    int customCount = 0;
    float customCurrenRating = 0;
    float currenRating = 0;
    TextView tvDefaultRatingMessage, tvCustomRatingMessage;
    RatingBar rbDefaultRatingIndicator, rbDefault, rbCustom, rbCustomRatingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratingbar);

//        rbDefaultRatingIndicator = (RatingBar) findViewById(R.id.rbDefaultRatingIndicator);
//        rbDefault = (RatingBar) findViewById(R.id.rbDefault);
//        rbDefault.setOnRatingBarChangeListener(this);
//        rbCustom = (RatingBar) findViewById(R.id.rbCustom);
//        rbCustom.setOnRatingBarChangeListener(this);
//        rbCustomRatingIndicator = (RatingBar) findViewById(R.id.rbCustomRatingIndicator);
//
//        tvDefaultRatingMessage = (TextView) findViewById(R.id.tvDefaultRatingMessage);
//        tvCustomRatingMessage = (TextView) findViewById(R.id.tvCustomRatingMessage);
    }

//    public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
//        DecimalFormat decimalFormat = new DecimalFormat("#.#");
//        switch (ratingBar.getId()) {
//            case R.id.rbDefault:
//                currenRating = Float.valueOf(decimalFormat.format((currenRating * count + rating)
//                        / ++count));
//                Toast.makeText(RatingbarActivity.this, "New default rating: " + currenRating, Toast.LENGTH_SHORT).show();
//                rbDefaultRatingIndicator.setRating(currenRating);
//                tvDefaultRatingMessage.setText(count + " Ratings");
//                break;
//            case R.id.rbCustom:
//                customCurrenRating = Float.valueOf(decimalFormat.format((customCurrenRating * customCount + rating)
//                        / ++customCount));
//                Toast.makeText(RatingbarActivity.this, "New custom rating: " + customCurrenRating, Toast.LENGTH_SHORT).show();
//                rbCustomRatingIndicator.setRating(customCurrenRating);
//                tvCustomRatingMessage.setText(customCount + " Ratings");
//                break;
//        }
//
//    }

}
