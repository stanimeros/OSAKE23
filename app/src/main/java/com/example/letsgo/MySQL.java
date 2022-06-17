package com.example.letsgo;

import java.util.ArrayList;

public class MySQL {

    public static final int START_TIME = 25000;
    public static final String IP = "192.168.2.99";

    public static MySQLConnection ThreadStart (MySQLConnection mySQL)
    {
        Thread t = new Thread(mySQL);
        try {
            t.start();
            t.join(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mySQL;
    }

    public static ArrayList<String> getScoreboard()
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT * FROM scoreboard ORDER BY points DESC");
        MySQL.ThreadStart(mySQLConnection);
        return mySQLConnection.getResults();
    }

    public static int getFromScoreboard(String target,String name)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT "+target+" FROM scoreboard WHERE name='"+name+"'",target);
        MySQL.ThreadStart(mySQLConnection);
        if (mySQLConnection.getResult()==null)
        {
            return -1;
        }else
        {
            return Integer.valueOf(mySQLConnection.getResult());
        }
    }

    public static String getTeamLogo(String name)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT logo_path FROM teams WHERE name='"+name+"'","logo_path");
        MySQL.ThreadStart(mySQLConnection);
        return mySQLConnection.getResult();
    }

    public static ArrayList<String> getPlayerAllStatistics(String name,String team)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT SUM(three_points),SUM(three_points_m),SUM(two_points)," +
                "SUM(two_points_m),SUM(free_shots),SUM(free_shots_m),SUM(rebounds)," +
                "SUM(assists),SUM(blocks),SUM(steals),SUM(mistakes),SUM(fouls) FROM statistics WHERE name='"+name+"' AND team='"+team+"'");
        MySQL.ThreadStart(mySQLConnection);
        return mySQLConnection.getResults();
    }

    public static ArrayList<String> getTeamAllStatistics(String team)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT SUM(three_points),SUM(three_points_m),SUM(two_points)," +
                "SUM(two_points_m),SUM(free_shots),SUM(free_shots_m),SUM(rebounds)," +
                "SUM(assists),SUM(blocks),SUM(steals),SUM(mistakes),SUM(fouls) FROM statistics WHERE team='"+team+"'");
        MySQL.ThreadStart(mySQLConnection);
        return mySQLConnection.getResults();
    }

    public static ArrayList<String> getPlayerNames(String team)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT players.name FROM players INNER JOIN teams on players.team_id = teams.id WHERE teams.name = '"+team+"'");
        MySQL.ThreadStart(mySQLConnection);
        return mySQLConnection.getResults();
    }

    public static ArrayList<String> getPlayers(String team,String option)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT players."+option+" FROM players INNER JOIN teams on players.team_id = teams.id WHERE teams.name = '"+team+"'");
        MySQL.ThreadStart(mySQLConnection);
        return mySQLConnection.getResults();
    }

    public static Player getPlayer(String name,String team)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT * FROM players INNER JOIN teams on players.team_id = teams.id WHERE teams.name = '"+team+"' AND players.name='"+name+"'");
        MySQL.ThreadStart(mySQLConnection);
        ArrayList<String> PlayerInfo = mySQLConnection.getResults();
        Player p = new Player(Integer.valueOf(PlayerInfo.get(0)),Integer.valueOf(PlayerInfo.get(1)),PlayerInfo.get(2),PlayerInfo.get(3),PlayerInfo.get(4));
        return p;
    }

    public static void UpdatePlayer(Player p,int id)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Update("UPDATE players SET name='"+p.getName()+"',position='"+p.getPosition()+"',photo_path='"+p.getPath()+"' WHERE id="+id);
        MySQL.ThreadStart(mySQLConnection);
    }

    public static Team getTeam(String name)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT * FROM teams WHERE name='"+name+"'");
        MySQL.ThreadStart(mySQLConnection);
        ArrayList<String> TeamInfo = mySQLConnection.getResults();
        Team t = new Team(TeamInfo.get(1),TeamInfo.get(2),TeamInfo.get(3));
        return t;
    }

    public static int getTeamId(String name)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT id FROM teams WHERE name = '"+name+"'","id");
        MySQL.ThreadStart(mySQLConnection);
        return Integer.valueOf(mySQLConnection.getResult());
    }

    public static ArrayList<String> getTeams()
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT name FROM teams");
        MySQL.ThreadStart(mySQLConnection);
        return mySQLConnection.getResults();
    }

    public static int getCurrentRound()
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT current_round FROM settings","current_round");
        MySQL.ThreadStart(mySQLConnection);
        return Integer.valueOf(mySQLConnection.getResult());
    }

    public static void setCurrentRound(int round)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Insert("INSERT INTO SETTINGS (current_round) VALUES ("+round+")");
        MySQL.ThreadStart(mySQLConnection);
    }

    public static ArrayList<String> getPlayerMatchStatistics(String name,int round)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT * FROM statistics WHERE round="+round+" AND name='"+name+"'");
        MySQL.ThreadStart(mySQLConnection);
        return mySQLConnection.getResults();
    }

    public static ArrayList<String> getTopPlayers()
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT players.name,players.position,team,rebounds,assists,three_points " +
                "FROM statistics INNER JOIN players on players.name = statistics.name WHERE round="+getCurrentRound()+
                " ORDER BY three_points DESC");
        MySQL.ThreadStart(mySQLConnection);
        return mySQLConnection.getResults();
    }

    public static ArrayList<String> getTeamMatchStatistics(String team,int round)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT SUM(three_points),SUM(three_points_m),SUM(two_points)," +
                "SUM(two_points_m),SUM(free_shots),SUM(free_shots_m),SUM(rebounds)," +
                "SUM(assists),SUM(blocks),SUM(steals),SUM(mistakes),SUM(fouls) FROM statistics WHERE round="+round+" AND team='"+team+"'");
        MySQL.ThreadStart(mySQLConnection);
        return mySQLConnection.getResults();
    }

    public static int getMatchColumn(int id,String target)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT "+target+" FROM matches WHERE id="+id,target);
        MySQL.ThreadStart(mySQLConnection);
        if (mySQLConnection.getResult()==null)
        {
            return -1;
        }else
        {
            return Integer.valueOf(mySQLConnection.getResult());
        }
    }

    public static void setMatchColumn(int id,String target,String value)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Update("UPDATE matches SET "+target+" = '"+value+"' WHERE id="+id);
        MySQL.ThreadStart(mySQLConnection);
    }

    public static Match getMatch(int id)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        ArrayList<String> match = new ArrayList<>();
        mySQLConnection.Select("SELECT * FROM matches WHERE id="+id);
        MySQL.ThreadStart(mySQLConnection);
        match = mySQLConnection.getResults();
        return new Match(Integer.valueOf(match.get(0)),match.get(1),match.get(2),Integer.valueOf(match.get(3)));
    }

    public static boolean AllFinished()
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT isFinished FROM matches WHERE isFinished=0","isFinished");
        MySQL.ThreadStart(mySQLConnection);
        String result = mySQLConnection.getResult();
        if (result==null)
        {
            return true;
        }else
        {
            return false;
        }
    }

    public static ArrayList<String> getMatchIdsFinished(int round)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT id FROM matches WHERE isFinished=1 AND round="+round);
        MySQL.ThreadStart(mySQLConnection);
        return mySQLConnection.getResults();
    }

    public static ArrayList<String> getMatchIdsCurrentRound()
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT id FROM matches WHERE isFinished=0 AND round="+MySQL.getCurrentRound());
        MySQL.ThreadStart(mySQLConnection);
        return mySQLConnection.getResults();
    }

    public static ArrayList<String> getFlow(int id)
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Select("SELECT * FROM flow WHERE match_id="+id);
        MySQL.ThreadStart(mySQLConnection);
        return mySQLConnection.getResults();
    }
}