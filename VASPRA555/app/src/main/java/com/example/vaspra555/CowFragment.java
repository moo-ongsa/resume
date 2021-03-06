package com.example.vaspra555;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.vaspra555.app.AppController;
import com.example.vaspra555.app.DataHolder;
import com.example.vaspra555.app.User;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class CowFragment extends Fragment {

    ImageButton btn_back;
    Button btn_submit;
    TextView txt_cowvatname,txt_cowvatdetail,txt_cowcurrentmoney,txt_cowmaxmoney,txt_contructName;
    String vatName,vatAddress,currentDonation,maxDonation,ProjectID,TempleID;
    int numberCurrentDonation,numberMaxDonation;
    EditText edt_moneyAmount;
    private String url = Server.URL + "vaspraPHP/cow.php";

    public static CowFragment newInstance() {
        return  new CowFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cow, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        txt_cowvatname = (TextView) view.findViewById(R.id.txt_CowVatName) ;
        txt_cowvatdetail= (TextView) view.findViewById(R.id.txt_CowVatDetail);
        txt_cowcurrentmoney= (TextView) view.findViewById(R.id.txt_currentcowmoney);
        txt_cowmaxmoney= (TextView) view.findViewById(R.id.txt_maxcowmoney);
        btn_back = (ImageButton) view.findViewById(R.id.btn_back_cow);
        btn_submit = (Button) view.findViewById(R.id.btn_donatesubmitcow);
        edt_moneyAmount = getView().findViewById(R.id.editmoneyamount);
        // edit text money amount
        final StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    vatName =jObj.getString("vatName");
                    vatAddress=jObj.getString("vatAddress");
                    currentDonation=jObj.getString("currentDonation");
                    maxDonation=jObj.getString("maxDonation");
                    ProjectID=jObj.getString("projectid");
                    TempleID=jObj.getString("Temple_ID");

                    txt_cowcurrentmoney.setText(currentDonation);
                    txt_cowmaxmoney.setText(maxDonation);
                    txt_cowvatdetail.setText(vatAddress);
                    txt_cowvatname.setText(vatName);

                    numberCurrentDonation = Integer.parseInt(currentDonation);
                    numberMaxDonation = Integer.parseInt(maxDonation);
                } catch (JSONException e) {
                    // JSON error
                    Toast.makeText(getActivity().getApplicationContext() ,"ERROR JSON = "+response, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext() ,"URL = "+url , Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext() ,"ERROR2", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "tag_json_obj");
        paypalsumbmit();

        btn_back.setOnClickListener(onCloseClickListener);

    }
    private View.OnClickListener onCloseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager()
                    .popBackStack();
        }
    };
    private void paypalsumbmit() {
        final EditText edt_moneyAmount = getView().findViewById(R.id.editmoneyamount);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberCurrentDonation == 0 && numberMaxDonation == 0) {Toast.makeText(getActivity() ,"No project available now.", Toast.LENGTH_LONG).show();}
                else
                {
                    String amount = edt_moneyAmount.getText().toString();
                    if (amount.isEmpty()) { Toast.makeText(getActivity(), "กรุณาใส่จำนวนเงิน", Toast.LENGTH_SHORT).show();}
                    else if (Long.parseLong(amount) > (numberMaxDonation - numberCurrentDonation)) {
                        edt_moneyAmount.setText(String.valueOf(numberMaxDonation - numberCurrentDonation), TextView.BufferType.EDITABLE);
                        Toast.makeText(getActivity() ,"You amount has over this project limit.", Toast.LENGTH_LONG).show();

                    }else
                    {   Fragment paypalFragment = new Fragment();
                        FragmentTransaction paypal = getActivity().getSupportFragmentManager().beginTransaction();
                        paypal.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        paypal.addToBackStack("tag");


                        DataHolder jObj = new DataHolder();
                        jObj.setUser_ID(User.getUser());
                        jObj.setDonationType("cow");
                        jObj.setamount(amount);
                        jObj.setDonation_ID(ProjectID);
                        jObj.setTempleID(TempleID);
                        paypal.replace(R.id.layout_paypal, PaypalFragment.newInstance(jObj),"paypal");
                        paypal.addToBackStack(null);
                        paypal.commit();
                    }
                }
            }   // onclick
        });
    }

}
