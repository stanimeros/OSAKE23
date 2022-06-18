package com.example.letsgo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class club_personal_stats extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public club_personal_stats() {}

    public static club_personal_stats newInstance(String param1, String param2) {
        club_personal_stats fragment = new club_personal_stats();
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

    ArrayList<String> players;
    LinearLayout layoutList;
    String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_club_personal_stats, container, false);
    }

    private void addView(int i){
        View playersView = getLayoutInflater().inflate(R.layout.players_view, null, false);
        Player p = MySQL.getPlayer(players.get(i),name);

        TextView p0 = playersView.findViewById(R.id.number1);
        p0.setText((int)Math.floor(Math.random()*(99-1+1)+1)+"");

        TextView p1 = playersView.findViewById(R.id.player1);
        p1.setText(p.getName());

        TextView p2 = playersView.findViewById(R.id.position1);
        p2.setText(p.getPosition());

        TextView p3 = playersView.findViewById(R.id.losses);
        TextView p4 = playersView.findViewById(R.id.rounds);
        TextView p5 = playersView.findViewById(R.id.points1);
        try {
            p3.setText(MySQL.getPlayerAllStatistics(p.getName(),name).get(6));
            p4.setText(MySQL.getPlayerAllStatistics(p.getName(),name).get(7));

            int points = 0;
            points += Integer.parseInt(MySQL.getPlayerAllStatistics(p.getName(),name).get(0)) * 3;
            points += Integer.parseInt(MySQL.getPlayerAllStatistics(p.getName(),name).get(2)) * 2;
            points += Integer.parseInt(MySQL.getPlayerAllStatistics(p.getName(),name).get(4));
            p5.setText(String.format("%d", points));
        }catch (Exception e){
            p3.setText("0");
            p4.setText("0");
            p5.setText("0");
        }

        playersView.setId(View.generateViewId());
        layoutList.addView(playersView);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutList = view.findViewById(R.id.layoutList);
        try {
            Bundle bundle = getArguments();
            name = bundle.getString("name");
            players = MySQL.getPlayerNames(name);

            for(int i=0;i<players.size();i++){
                addView(i);
            }
        }catch (Exception e)
        {
            System.out.println(e);
        }

    }
}