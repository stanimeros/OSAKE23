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

public class gamecard extends Fragment {

    int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            Bundle bundle = getArguments();
            id = bundle.getInt("id");
        }catch (Exception e)
        {
            System.out.println(e);
        }
        return inflater.inflate(R.layout.fragment_gamecard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ArrayList<String> players = null;
        Match m = null;

        TextView p1 = view.findViewById(R.id.textView2);
        TextView p2 = view.findViewById(R.id.textView3);
        TextView p3 = view.findViewById(R.id.textView4);
        TextView p4 = view.findViewById(R.id.textView5);
        TextView p5 = view.findViewById(R.id.textView6);
        TextView p6 = view.findViewById(R.id.Subplayer1);
        TextView p7 = view.findViewById(R.id.Subplayer2);
        TextView p8= view.findViewById(R.id.Subplayer3);

        TextView p11 = view.findViewById(R.id.player21);
        TextView p12 = view.findViewById(R.id.player22);
        TextView p13 = view.findViewById(R.id.player23);
        TextView p14 = view.findViewById(R.id.player24);
        TextView p15 = view.findViewById(R.id.player25);
        TextView p16 = view.findViewById(R.id.Subplayer21);
        TextView p17 = view.findViewById(R.id.Subplayer22);
        TextView p18= view.findViewById(R.id.Subplayer23);

        try {
            m = MySQL.getMatch(id);
            players = MySQL.getPlayerNames(m.getHome());

            p1.setText(players.get(0));
            p2.setText(players.get(1));
            p3.setText(players.get(2));
            p4.setText(players.get(3));
            p5.setText(players.get(4));
            p6.setText(players.get(5));
            p7.setText(players.get(6));
            p8.setText(players.get(7));
        }catch (Exception e){
            System.out.println(e);
        }

        try {
            players.clear();
            players = MySQL.getPlayerNames(m.getAway());

            p11.setText(players.get(0));
            p12.setText(players.get(1));
            p13.setText(players.get(2));
            p14.setText(players.get(3));
            p15.setText(players.get(4));
            p16.setText(players.get(5));
            p17.setText(players.get(6));
            p18.setText(players.get(7));

        }catch (Exception e){
            System.out.println(e);
        }
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public gamecard() {}

    public static gamecard newInstance(String param1, String param2) {
        gamecard fragment = new gamecard();
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
}