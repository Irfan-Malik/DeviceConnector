package com.example.deviceconnector.conditioner.tabs.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.deviceconnector.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.data_tab_layout, container, false);
        final TextView dataText = v.findViewById(R.id.data_text);

        final Context context = this.getContext();

        return v;
    }

    public static DataFragment newInstance() {
        DataFragment f = new DataFragment();
        return f;
    }
}

