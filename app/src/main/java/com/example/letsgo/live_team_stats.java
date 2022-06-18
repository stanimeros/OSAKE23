package com.example.letsgo;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class live_team_stats extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    int id;

    public live_team_stats() {}

    public static live_team_stats newInstance(String param1, String param2) {
        live_team_stats fragment = new live_team_stats();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        id = bundle.getInt("id");

        return inflater.inflate(R.layout.fragment_live_team_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Match m = MySQL.getMatch(id);
        ArrayList<String> stats = MySQL.getTeamMatchStatistics(m.getHome(),m.getRound());

        //TEAM 1
        TextView t1 = view.findViewById(R.id.tripontaNumTeam1);
        TextView t2 = view.findViewById(R.id.dipontaNumTeam1);
        TextView t3 = view.findViewById(R.id.eleutheraNumTeam1);
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

        TextView t4 = view.findViewById(R.id.reboundsNumTeam1);
        t4.setText(stats.get(6));

        TextView t5 = view.findViewById(R.id.assistsNumTeam1);
        t5.setText(stats.get(7));

        TextView t6= view.findViewById(R.id.kopsimataNumTeam1);
        t6.setText(stats.get(8));

        TextView t7= view.findViewById(R.id.klepsimataNumTeam1);
        t7.setText(stats.get(9));

        TextView t8= view.findViewById(R.id.lathoiNumTeam1);
        t8.setText(stats.get(10));

        TextView t9= view.findViewById(R.id.faoulNumTeam1);
        t9.setText(stats.get(11));

        //TEAM 2
        stats = MySQL.getTeamMatchStatistics(m.getAway(),m.getRound());

        t1 = view.findViewById(R.id.tripontaNumTeam2);
        t2 = view.findViewById(R.id.dipontaNumTeam2);
        t3 = view.findViewById(R.id.eleutheraNumTeam2);
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

        t4 = view.findViewById(R.id.reboundsNumTeam2);
        t4.setText(stats.get(6));

        t5 = view.findViewById(R.id.assistsNumTeam2);
        t5.setText(stats.get(7));

        t6= view.findViewById(R.id.kopsimataNumTeam2);
        t6.setText(stats.get(8));

        t7= view.findViewById(R.id.klepsimataNumTeam2);
        t7.setText(stats.get(9));

        t8= view.findViewById(R.id.lathoiNumTeam2);
        t8.setText(stats.get(10));

        t9= view.findViewById(R.id.faoulNumTeam2);
        t9.setText(stats.get(11));
    }
}