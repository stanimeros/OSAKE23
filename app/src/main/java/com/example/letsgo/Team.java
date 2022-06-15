package com.example.letsgo;

public class Team {
    private String name;
    private String city;
    private String path;

    public Team(String name,String city,String logo_path) {
        this.name = name;
        this.city = city;
        this.path = logo_path;
    }

    public void createSQL() {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Insert("INSERT INTO teams VALUES (null,'"+name+"','"+city+"','"+path+"')");
        MySQL.ThreadStart(mySQLConnection);
    }

    public void addMatchResult(boolean Win){
        MySQLConnection mySQLConnection = new MySQLConnection();
        int rounds=0;
        int wins=0;
        int losses=0;
        int points=0;

        rounds++;
        if (!Win)
        {
            losses++;
            points++;
        }else
        {
            wins++;
            points+=2;
        }
        rounds+=MySQL.getFromScoreboard("rounds",name);

        wins+=MySQL.getFromScoreboard("wins",name);

        losses+=MySQL.getFromScoreboard("losses",name);

        points+=MySQL.getFromScoreboard("points",name);

        mySQLConnection.Update("UPDATE scoreboard SET wins='"+wins+"',losses='"+losses+"',rounds='"+rounds+"',points='"+points+"' WHERE name='"+name+"'");
        MySQL.ThreadStart(mySQLConnection);
    }
}
