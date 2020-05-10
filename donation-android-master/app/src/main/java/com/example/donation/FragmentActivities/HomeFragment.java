package com.example.donation.FragmentActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.donation.Activities.User.PlaceActivity;
import com.example.donation.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

//    private Button btnWat, btnHospital , btnVictim, btnFoundation;
    private ImageButton btnWat, btnHospital , btnVictim, btnFoundation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);


        btnWat = inflate.findViewById(R.id.btn_wat);
        btnWat.setOnClickListener(this);

        btnHospital = inflate.findViewById(R.id.btn_hospital);
        btnHospital.setOnClickListener(this);

        btnVictim = inflate.findViewById(R.id.btn_victim);
        btnVictim.setOnClickListener(this);

        btnFoundation = inflate.findViewById(R.id.btn_foundation);
        btnFoundation.setOnClickListener(this);

        return inflate;
    }

    @Override
    public void onClick(View view) {
        if (view == btnWat) {
            Intent intent = new Intent(getActivity(), PlaceActivity.class);
            intent.putExtra("filter", "วัดหรือศาสนาสถาน");
            startActivity(intent);
        }

        if (view == btnHospital) {
            Intent intent = new Intent(getActivity(), PlaceActivity.class);
            intent.putExtra("filter", "สถานสงเคราะห์");
            startActivity(intent);
        }

        if (view == btnVictim) {
            Intent intent = new Intent(getActivity(), PlaceActivity.class);
            intent.putExtra("filter", "ครอบครัวผู้ด้อยโอกาสและผู้ประสบอุทกภัย");
            startActivity(intent);
        }

        if (view == btnFoundation) {
            Intent intent = new Intent(getActivity(), PlaceActivity.class);
            intent.putExtra("filter", "มูลนิธิต่าง ๆ");
            startActivity(intent);
        }
    }
}
