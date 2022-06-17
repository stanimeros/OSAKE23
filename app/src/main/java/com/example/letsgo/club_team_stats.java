package com.example.letsgo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link club_team_stats#newInstance} factory method to
 * create an instance of this fragment.
 */
public class club_team_stats extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public club_team_stats() {}

    public static club_team_stats newInstance(String param1, String param2) {
        club_team_stats fragment = new club_team_stats();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_club_team_stats, container, false);
        Bundle bundle = getArguments();
        String name = bundle.getString("name");

        new Thread(){
            public void run(){
                ArrayList<String> stats = MySQL.getTeamAllStatistics(name);

                //TEAM 1
                TextView t1 = view.findViewById(R.id.tripontaNumTeam2);
                TextView t2 = view.findViewById(R.id.dipontaNumTeam2);
                TextView t3 = view.findViewById(R.id.eleutheraNumTeam2);
                if (stats.get(0)==null)
                {
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                }else
                {
                    t1.setText(stats.get(0)+"/"+stats.get(1));
                    t2.setText(stats.get(2)+"/"+stats.get(3));
                    t3.setText(stats.get(4)+"/"+stats.get(5));
                }

                TextView t4 = view.findViewById(R.id.reboundsNumTeam2);
                t4.setText(stats.get(6));

                TextView t5 = view.findViewById(R.id.assistsNumTeam2);
                t5.setText(stats.get(7));

                TextView t6= view.findViewById(R.id.kopsimataNumTeam2);
                t6.setText(stats.get(8));

                TextView t7= view.findViewById(R.id.klepsimataNumTeam2);
                t7.setText(stats.get(9));

                TextView t8= view.findViewById(R.id.lathoiNumTeam2);
                t8.setText(stats.get(10));

                TextView t9= view.findViewById(R.id.faoulNumTeam2);
                t9.setText(stats.get(11));
            }
        }.start();

        return view;
    }
}