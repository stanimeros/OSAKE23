package com.example.letsgo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;

public class live_flow extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ArrayList<String> flow;
    ArrayList<String> temp;
    Match m;
    int id;

    ScrollView sv;

    public live_flow() {}

    public static live_flow newInstance(String param1, String param2) {
        live_flow fragment = new live_flow();
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

    LinearLayout layoutList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_live_flow, container, false);
        layoutList = view.findViewById(R.id.layoutList);
        sv = view.findViewById(R.id.fragment_live_flow);

        try {
            Bundle bundle = getArguments();
            id = bundle.getInt("id");
            m = MySQL.getMatch(id);

            flow = MySQL.getFlow(m.getId());
            for(int i=0;i<flow.size();i=i+5){
                addView(i,flow);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return view;
    }

    private void addView(int i,ArrayList<String> flow){
        View live_flow_view = getLayoutInflater().inflate(R.layout.liveflow_view, null, false);

        TextView t1 = live_flow_view.findViewById(R.id.Team1action);
        TextView t2 = live_flow_view.findViewById(R.id.Team1actionPlayer);
        TextView t3 = live_flow_view.findViewById(R.id.timeView);
        TextView t4 = live_flow_view.findViewById(R.id.Team2action);
        TextView t5 = live_flow_view.findViewById(R.id.Team2actionPlayer);

        String cap = flow.get(i+1).substring(0, 1).toUpperCase() + flow.get(i+1).substring(1);
        cap = cap.substring(0,cap.length() - 1);
        cap = cap.replaceAll("_"," ");

        if (cap.endsWith("s "))
        {
            cap = cap.substring(0,cap.length() - 2);
            cap += " missed";
        }

        if (flow.get(i+2)==null){
            t3.setText(flow.get(i+1));
            t1.setText("");
            t2.setText("");
            t4.setText("");
            t5.setText("");
        }else if (flow.get(i+3).equals(m.getHome())){
            t1.setText(cap);
            t2.setText(flow.get(i+2));
            t3.setText((flow.get(i+4)));
            t4.setText("");
            t5.setText("");
        }else{
            t1.setText("");
            t2.setText("");
            t4.setText(cap);
            t5.setText(flow.get(i+2));
            t3.setText((flow.get(i+4)));
        }

        live_flow_view.setId(View.generateViewId());
        layoutList.addView(live_flow_view);
    }

    private void updateFlow()
    {
        try {
            Bundle bundle = getArguments();
            id = bundle.getInt("id");
            m = MySQL.getMatch(id);

            temp = MySQL.getFlow(m.getId());

            for (int i=0;i<flow.size();i++)
            {
                temp.remove(0);
            }

            for(int i=0;i<temp.size();i=i+5){
                addView(i,temp);
                for (int j=0;j<5;j++)
                {
                    flow.add(temp.get(i+j));
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 2000;

    @Override
    public void onResume() {
        try{
            handler.postDelayed(runnable = new Runnable() {
                public void run() {
                    handler.postDelayed(runnable, delay);
                    updateFlow();
                }
            }, delay);
            super.onResume();
        }catch (Exception e){}}
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }
}