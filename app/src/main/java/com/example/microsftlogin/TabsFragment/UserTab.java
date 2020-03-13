package com.example.microsftlogin.TabsFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.R;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;

import static android.content.Context.MODE_PRIVATE;
import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.USER_EMAIL;
import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.USER_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserTab extends Fragment {

    TextView emailAddress;
    TextView personName;
    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();


    public UserTab() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view =  lf.inflate(R.layout.fragment_user, container, false);
        //sph.mprefrences = getActivity().getSharedPreferences(sph.getSharedfile(),MODE_PRIVATE);
        personName = view.findViewById(R.id.account_name);
        personName.setText(SharedPrefrenceUtil.getInstance(getActivity()).getStringValue(USER_NAME));
        emailAddress = view.findViewById(R.id.emailaddress_acc);
        emailAddress.setText(SharedPrefrenceUtil.getInstance(getActivity()).getStringValue(USER_EMAIL));
        return view;
    }

}
