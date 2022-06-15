package com.example.letsgo;

public class Match {
    private int id;
    private int round;
    private String home;
    private String away;
    private String timestamp = "00:00";
    private int homePoints;
    private int awayPoints;
    private MySQLConnection mySQLConnection = new MySQLConnection();

    public Match(int id,String home,String away,int round){
        this.id = id;
        this.home=home;
        this.away=away;
        this.round=round;
    }

    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }

    public void addAction(String action,String name,String team){
        int num;
        String temp;

        mySQLConnection.Select("SELECT name FROM statistics WHERE name= '"+name+"' AND team = '"+team+"' AND round= "+round,"name");
        MySQL.ThreadStart(mySQLConnection);

        mySQLConnection.Insert("INSERT INTO flow (match_id,action,name,team,timestamp) VALUES ("+id+",'"+action+"','"+name+"','"+team+"','"+timestamp+"')");
        MySQL.ThreadStart(mySQLConnection);

        temp = mySQLConnection.getResult();

        if (temp==null){
            mySQLConnection.Insert("INSERT INTO statistics (name,team,round,"+action+") VALUES ('"+name+"','"+team+"',"+round+",'"+1+"')");
            MySQL.ThreadStart(mySQLConnection);
        }else{
            mySQLConnection.Select("SELECT "+action+" FROM statistics WHERE name= '"+name+"' AND team = '"+team+"' AND round="+round,action);
            MySQL.ThreadStart(mySQLConnection);

            num= Integer.valueOf(mySQLConnection.getResult());
            num++;

            mySQLConnection.Update("UPDATE statistics SET "+action+" = '"+num+"' WHERE name= '"+name+"' AND team = '"+team+"' AND round="+round);
            MySQL.ThreadStart(mySQLConnection);
        }
    }

    public void insertPoint(int p,boolean homeScored)
    {
        if (homeScored)
        {
            mySQLConnection.Select("SELECT homePoints FROM matches WHERE home='"+home+"' AND round="+round,"homePoints");
            MySQL.ThreadStart(mySQLConnection);
            homePoints = p + Integer.valueOf(mySQLConnection.getResult());

            mySQLConnection.Update("UPDATE matches SET homePoints="+homePoints+" WHERE home='"+home+"' AND round="+round);
            MySQL.ThreadStart(mySQLConnection);
        }else
        {
            mySQLConnection.Select("SELECT awayPoints FROM matches WHERE away='"+away+"' AND round="+round,"awayPoints");
            MySQL.ThreadStart(mySQLConnection);
            awayPoints= p + Integer.valueOf(mySQLConnection.getResult());

            mySQLConnection.Update("UPDATE matches SET awayPoints="+awayPoints+" WHERE away='"+away+"' AND round="+round);
            MySQL.ThreadStart(mySQLConnection);
        }
    }

    public String getHome() {
        return home;
    }
    public String getAway() {
        return away;
    }
    public int getRound() { return round; }
    public int getId() { return id; }
}
