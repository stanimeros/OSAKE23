package com.example.letsgo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class live_personal_stats extends Fragment {

    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        id = bundle.getInt("id");

        Match m = MySQL.getMatch(id);

        BottomNavigationView bottomNavigationView;
        first_team first_team = new first_team();
        second_team second_team = new second_team();

        bundle.putString("away",m.getAway());
        first_team.setArguments(bundle);
        second_team.setArguments(bundle);

        TextView t1 = view.findViewById(R.id.team1);
        t1.setText(m.getHome());

        TextView t2 = view.findViewById(R.id.team2);
        t2.setText(m.getAway());

        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView3);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView3,first_team).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.first_team:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView3,first_team).commit();
                        return true;

                    case R.id.second_team:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView3,second_team).commit();
                        return true;
                }
                return false;
            }
        });
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public live_personal_stats() {}

    public static live_personal_stats newInstance(String param1, String param2) {
        live_personal_stats fragment = new live_personal_stats();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_personal_stats, container, false);
    }
}