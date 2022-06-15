package com.example.letsgo;

public class Player {
    private int id;
    private int team_id;
    private String name;
    private String position;
    private String path;

    public Player(int id,int team_id,String name,String position,String photo_path)
    {
        this.id = id;
        this.team_id = team_id;
        this.name = name;
        this.position = position;
        this.path = photo_path;
    }

    public Player(int team_id,String name,String position,String photo_path)
    {
        this.team_id = team_id;
        this.name = name;
        this.position = position;
        this.path = photo_path;
    }

    public void createSQL()
    {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.Insert("INSERT INTO players VALUES (null,'"+team_id+"','"+name+"','"+position+"','"+path+"')");
        MySQL.ThreadStart(mySQLConnection);
    }

    public String getName(){return name;}

    public String getPosition(){return position;}

    public String getPath(){return path;}

    public int getId(){
        return id;
    }

    public int getTeamId(){
        return team_id;
    }
}