package com.example.letsgo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class second_team extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public second_team() {}

    public static second_team newInstance(String param1, String param2) {
        second_team fragment = new second_team();
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
    Match m;
    int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_team, container, false);

        try {
            Bundle bundle = getArguments();
            id = bundle.getInt("id");
            m = MySQL.getMatch(id);
            players = MySQL.getPlayerNames(m.getAway());

            layoutList = view.findViewById(R.id.layoutList);
            for(int i=0;i<players.size();i++){
                addView(i);
            }
        }catch (Exception e)
        {
            System.out.println(e);
        }
        return view;
    }

    private void addView(int i){
        View playersView = getLayoutInflater().inflate(R.layout.players_view, null, false);
        Player p = MySQL.getPlayer(players.get(i),m.getAway());

        TextView p1 = playersView.findViewById(R.id.player1);
        p1.setText(p.getName());

        TextView p2 = playersView.findViewById(R.id.position1);
        p2.setText(p.getPosition());

        TextView p3 = playersView.findViewById(R.id.rebaunts1);
        TextView p4 = playersView.findViewById(R.id.assists1);
        TextView p5 = playersView.findViewById(R.id.Points1);
        try {
            p3.setText(MySQL.getPlayerMatchStatistics(p.getName(),m.getRound()).get(9));
            p4.setText(MySQL.getPlayerMatchStatistics(p.getName(),m.getRound()).get(10));
            
            int points = 0;
            points += Integer.parseInt(MySQL.getPlayerMatchStatistics(p.getName(), m.getRound()).get(3)) * 3;
            points += Integer.parseInt(MySQL.getPlayerMatchStatistics(p.getName(), m.getRound()).get(5)) * 2;
            points += Integer.parseInt(MySQL.getPlayerMatchStatistics(p.getName(), m.getRound()).get(7));
            p5.setText(String.format("%d", points));
        }catch (Exception e){
            p3.setText("0");
            p4.setText("0");
            p5.setText("0");
        }

        playersView.setId(View.generateViewId());
        layoutList.addView(playersView);
    }
}