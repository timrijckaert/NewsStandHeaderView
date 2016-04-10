package com.example.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import be.vrt.mobile.android.sporza.voetbal.ui.widget.slf.ImageProvidable;

public class MockFragment extends Fragment implements ImageProvidable {

    public static MockFragment newInstance() {
        return new MockFragment();
    }

    public MockFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mock, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public List<String> getBackgroundImages(int position) {
        final ArrayList<String> imageURLS = new ArrayList<>();
        imageURLS.add("http://sporza.be/polopoly_fs/1.2625324!image/39925912.jpg_gen/derivatives/landscape670/39925912.jpg");
        imageURLS.add("http://sporza.be/polopoly_fs/1.2623823!image/2572576936.jpg_gen/derivatives/landscape670/2572576936.jpg");
        imageURLS.add("http://sporza.be/polopoly_fs/1.2601419!image/2968067869.jpg_gen/derivatives/landscape670/2968067869.jpg");
        return imageURLS;
    }
}