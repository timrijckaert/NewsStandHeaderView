package com.example.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MockFragment extends Fragment {

    public MockFragment() {
    }

    public static MockFragment newInstance() {
        return new MockFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mock, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}