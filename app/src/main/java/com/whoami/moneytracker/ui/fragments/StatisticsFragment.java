package com.whoami.moneytracker.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.whoami.moneytracker.R;

public class StatisticsFragment extends Fragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "StatisticFragment.onCreate()",
                Toast.LENGTH_LONG).show();
        Log.d("Fragment", "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.statistics_fragment, container, false);
        Toast.makeText(getActivity(), "StatisticFragment.onCreateView()",
                Toast.LENGTH_LONG).show();
        Log.d("Fragment", "onCreateView");
        getActivity().setTitle(R.string.nav_drawer_statistics);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        Toast.makeText(getActivity(), "StatisticFragment.onAttach()",
                Toast.LENGTH_LONG).show();
        Log.d("Fragment", "onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "StatisticFragment.onActivityCreated()",
                Toast.LENGTH_LONG).show();
        Log.d("Fragment", "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Toast.makeText(getActivity(), "StatisticFragment.onStart()",
                Toast.LENGTH_LONG).show();
        Log.d("Fragment", "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Toast.makeText(getActivity(), "StatisticFragment.onResume()",
                Toast.LENGTH_LONG).show();
        Log.d("Fragment", "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getActivity(), "StatisticFragment.onPause()",
                Toast.LENGTH_LONG).show();
        Log.d("Fragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(getActivity(), "StatisticFragment.onStop()",
                Toast.LENGTH_LONG).show();
        Log.d("Fragment", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Toast.makeText(getActivity(), "StatisticFragment.onDestroyView()",
                Toast.LENGTH_LONG).show();
        Log.d("Fragment", "onDestroyView");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getActivity(), "StatisticFragment.onDestroy()",
                Toast.LENGTH_LONG).show();
        Log.d("Fragment", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getActivity(), "StatisticFragment.onDetach()",
                Toast.LENGTH_LONG).show();
        Log.d("Fragment", "onDetach");
    }


}