package com.example.letsgo;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import com.google.android.material.imageview.ShapeableImageView;

public class game_management extends Fragment implements AdapterView.OnItemSelectedListener {

    int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_management, container, false);

        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        Match m = MySQL.getMatch(id);

        ImageView add = view.findViewById(R.id.add1);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), shot_score.class);
                bundle.putString("team",m.getHome());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        add = view.findViewById(R.id.add2);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), shot_miss.class);
                bundle.putString("team",m.getHome());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        add = view.findViewById(R.id.add3);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), action.class);
                bundle.putString("team",m.getHome());
                bundle.putString("action","blocks");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        add = view.findViewById(R.id.add4);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), action.class);
                bundle.putString("team",m.getHome());
                bundle.putString("action","steals");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        add = view.findViewById(R.id.add5);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), action.class);
                bundle.putString("team",m.getHome());
                bundle.putString("action","mistakes");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        add = view.findViewById(R.id.add6);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), action.class);
                bundle.putString("team",m.getHome());
                bundle.putString("action","fouls");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //Team 2
        add = view.findViewById(R.id.add11);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), shot_score.class);
                bundle.putString("team",m.getAway());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        add = view.findViewById(R.id.add21);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), shot_miss.class);
                bundle.putString("team",m.getAway());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        add = view.findViewById(R.id.add31);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), action.class);
                bundle.putString("team",m.getAway());
                bundle.putString("action","blocks");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        add = view.findViewById(R.id.add41);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), action.class);
                bundle.putString("team",m.getAway());
                bundle.putString("action","steals");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        add = view.findViewById(R.id.add51);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), action.class);
                bundle.putString("team",m.getAway());
                bundle.putString("action","mistakes");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        add = view.findViewById(R.id.add61);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), action.class);
                bundle.putString("team",m.getAway());
                bundle.putString("action","fouls");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ShapeableImageView sub;

        sub = view.findViewById(R.id.subteam1);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), substitution.class);
                bundle.putString("team",m.getHome());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        sub = view.findViewById(R.id.subteam2);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), substitution.class);
                bundle.putString("team",m.getAway());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public game_management() {}

    public static game_management newInstance(String param1, String param2) {
        game_management fragment = new game_management();
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}