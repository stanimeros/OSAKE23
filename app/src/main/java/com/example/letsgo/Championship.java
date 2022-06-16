package com.example.letsgo;

import java.util.ArrayList;
import java.util.Collections;

public class Championship {
    private ArrayList<String> teams = new ArrayList<>();
    private MySQLConnection mySQLConnection = new MySQLConnection();

    public Championship(){

        ArrayList<String> teams = new ArrayList<>();
        mySQLConnection.Select("SELECT name FROM teams");
        MySQL.ThreadStart(mySQLConnection);
        teams = mySQLConnection.getResults();

        mySQLConnection.Insert("TRUNCATE TABLE scoreboard");
        MySQL.ThreadStart(mySQLConnection);

        for (int i=0;i<teams.size();i++)
        {
            mySQLConnection.Insert("INSERT INTO scoreboard (name) VALUES ('"+teams.get(i)+"')");
            MySQL.ThreadStart(mySQLConnection);
        }

        this.teams = teams;
        Collections.shuffle(teams);

        mySQLConnection.Insert("TRUNCATE TABLE matches");
        MySQL.ThreadStart(mySQLConnection);

        mySQLConnection.Insert("TRUNCATE TABLE settings");
        MySQL.ThreadStart(mySQLConnection);

        mySQLConnection.Insert("TRUNCATE TABLE statistics");
        MySQL.ThreadStart(mySQLConnection);

        mySQLConnection.Insert("TRUNCATE TABLE flow");
        MySQL.ThreadStart(mySQLConnection);

        MySQL.setCurrentRound(1);

        ArrayList<String> temp = new ArrayList<>();

        temp.addAll(teams);
        int size = temp.size()-1;
        for (int i=0;i<size;i++){
            for (int j=0;j<temp.size()-1;j++){
                createRound(temp.get(0), temp.get(j+1));
            }
            temp.remove(0);
        }
    }

    private void createRound(String home,String away){
        int round=1;
        round = checkRound(home,1);
        round = checkRound(away,round);

        if (round % 2 == 0){
            mySQLConnection.Insert("INSERT INTO matches (home,away,round) VALUES ('"+away+"','"+home+"',"+round+")");
            MySQL.ThreadStart(mySQLConnection);
        }else{
            mySQLConnection.Insert("INSERT INTO matches (home,away,round) VALUES ('"+home+"','"+away+"',"+round+")");
            MySQL.ThreadStart(mySQLConnection);
        }
    }

    private int checkRound(String team,int num)
    {
        String temp = "There is a match!";
        while (temp!=null){
            mySQLConnection.Select("SELECT round FROM matches WHERE (home= '"+team+"' OR away= '"+team+"') AND round="+num,"round");
            MySQL.ThreadStart(mySQLConnection);
            temp = mySQLConnection.getResult();
            if (temp!=null){
                num++;
            }
        }
        return num;
    }
}
