package com.example.letsgo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQLConnection implements Runnable{
    private boolean select;
    private String sql;
    private String target;

    private ArrayList<String> results = new ArrayList<>();
    private String result;

    public void Select(String sql,String target){
        this.select = true;
        this.sql = sql;
        this.target = target;
    }

    public void Select(String sql){
        this.select = true;
        this.sql = sql;
    }

    public void Insert(String sql){
        this.select = false;
        this.sql = sql;
    }

    public void Update(String sql){
        this.select = false;
        this.sql = sql;
    }

    public void run() {
        Connection con;
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mariadb://"+MySQL.IP+":3306/basket","admin","admin");

            try{
                Statement statement = con.createStatement();
                if (!select){ // INSERT OR UPDATE
                    statement.executeUpdate(sql);
                }else if (select){ // SELECT
                    results.clear();
                    ResultSet rs = statement.executeQuery(sql);

                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();

                    if (!rs.next()) {
                        result = null;
                    }else
                    {
                        do {
                            try
                            {
                                result = rs.getString(target);
                            }catch (Exception e){}

                            int i=1;
                            while (i<=columnCount)
                            {
                                results.add(rs.getString(i++));
                            }
                        } while (rs.next());
                    }
                }
                con.close();
            }
            catch (SQLException s){
                System.out.println("SQL statement is not executed!");
                s.printStackTrace();

            }finally {
                con.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getResult() {return result;}

    public ArrayList<String> getResults() {return results;}
}
