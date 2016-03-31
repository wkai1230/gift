package com.wenkai.my.gift.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wenkai.my.gift.BaseFragment;
import com.wenkai.my.gift.R;
import com.zar.CaptureActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileFragment extends BaseFragment {
    @Bind(R.id.rg_profile)
    RadioGroup rgProfile;
    @Bind(R.id.tv_line1_profile)
    TextView tvLine1;
    @Bind(R.id.tv_line2_profile)
    TextView tvLine2;
    @Bind(R.id.ib_qr_code)
    ImageButton ibQrCode;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        initListener();

        return view;
    }

    private void initListener() {
        rgProfile.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_profile_gift:
                        tvLine1.setBackgroundResource(android.R.color.holo_red_light);
                        tvLine2.setBackgroundResource(android.R.color.darker_gray);
                        break;
                    case R.id.rb_profile_strategy:
                        tvLine1.setBackgroundResource(android.R.color.darker_gray);
                        tvLine2.setBackgroundResource(android.R.color.holo_red_light);
                        break;
                }
            }
        });

        ibQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
