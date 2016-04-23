package com.example.zhenning.zhenninga2tone;

import android.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    // Create a DetailsFragment that contains the hero data for the correct index
    public static DetailsFragment newInstance(int index) {
        DetailsFragment f = new DetailsFragment();

        // Bundles are used to pass data using a key "index" and a value
        Bundle args = new Bundle();
        args.putInt("index", index);

        // Assign key value to the fragment
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {

        // Returns the index assigned
        return getArguments().getInt("index", 0);
    }

    // LayoutInflator puts the Fragment on the screen
    // ViewGroup is the view you want to attach the Fragment to
    // Bundle stores key value pairs so that data can be saved
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        WebView showLink = new WebView(getActivity());

        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                4, getActivity().getResources().getDisplayMetrics());

        showLink.setPadding(padding, padding, padding, padding);

        showLink.loadUrl(SuperHeroInfo.Url[getShownIndex()]);

        return showLink;

    }
}
