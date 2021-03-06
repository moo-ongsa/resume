package com.example.vaspra555.Suggestion_Logo;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.vaspra555.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class logo3_Fragment extends Fragment {
    public static logo3_Fragment newInstance() {
        return new logo3_Fragment();
    }



    @Nullable
    Button btn1,btn2,btn4,btn5;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate (R.layout.fragment_logo3_, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        btn1 = (Button)view.findViewById (R.id.btn_smile1_logo3);
        btn1.setOnClickListener(OnClickGoto1);
        btn2 = (Button)view.findViewById (R.id.btn_smile2_logo3);
        btn2.setOnClickListener(OnClickGoto2);
        btn4 = (Button)view.findViewById (R.id.btn_smile4_logo3);
        btn4.setOnClickListener(OnClickGoto4);
        btn5 = (Button)view.findViewById (R.id.btn_smile5_logo3);
        btn5.setOnClickListener(OnClickGoto5);
        Rating.setRating ("3");

    }
    private View.OnClickListener OnClickGoto1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.FrameLayout_mainsuggest, logo1_Fragment.newInstance ())
                    .remove (logo3_Fragment.this)
                    .commit();
        }
    };

    private View.OnClickListener OnClickGoto2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.FrameLayout_mainsuggest, logo2_Fragment.newInstance ())
                    .remove (logo3_Fragment.this)
                    .commit();
        }
    };
    private View.OnClickListener OnClickGoto4 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.FrameLayout_mainsuggest, logo4_Fragment.newInstance ())
                    .remove (logo3_Fragment.this)
                    .commit();
        }
    };
    private View.OnClickListener OnClickGoto5 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.FrameLayout_mainsuggest, logo5_Fragment.newInstance ())
                    .remove (logo3_Fragment.this)
                    .commit();
        }
    };


}
