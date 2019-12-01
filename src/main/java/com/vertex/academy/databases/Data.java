package com.vertex.academy.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Simple Java program to connect to MySQL database running on localhost and
 * running SELECT and INSERT query to retrieve and add data.
 * @author Javin Paul
 */
public class Data {

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:postgresql://77.108.90.172:10005/transport";
    private static final String user = "utm";
    private static final String password = "mMM920i-0f";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static Statement stmt1;
    private static Statement stmt2;
    private static Statement stmt3;
    private static ResultSet rout_path;
    private static ResultSet route;
    private static ResultSet stop;
    private static ResultSet path_stop;

    public static void main(String args[]) {
        String pathid = "ad8fb052-2c6b-4380-9b51-16adddfb9f79";
        String rout_path_string = "SELECT id, route_id, path_geometry FROM route_path WHERE id = '" + pathid + "'";

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt  = con.createStatement();
            stmt1 = con.createStatement();
            stmt2 = con.createStatement();
            stmt3 = con.createStatement();

            // executing SELECT query
            rout_path = stmt.executeQuery(rout_path_string);

            rout_path.next();
                String id_rout_path = rout_path.getString(1);
                String Route_id = rout_path.getString(2);
                String path_geometry = rout_path.getString(3);
                System.out.println(id_rout_path + " " + Route_id + " " + path_geometry);

                String rout_string = "SELECT id, number, type FROM route WHERE id = '" + Route_id + "'";
                //rout_path.close();

                route = stmt1.executeQuery(rout_string);
                route.next();
                    String id_route = route.getString(1);
                    String Number_route = route.getString(2);
                    String type_route = route.getString(3);
                    System.out.println(id_route + " " + Number_route + " " + type_route);

                    //String path_stop_string = "SELECT * FROM path_stop where path_id = '"+ pathid+"'";
                     String path_stop_string = "SELECT route_path_id, stop_id, weight from path_stop where route_path_id = '"+ pathid + "'";
                   // route.close();
                    //stmt.close();

                    path_stop = stmt2.executeQuery(path_stop_string);
                    while (path_stop.next()){
                        String id_path = path_stop.getString(1);
                        String stop_id = path_stop.getString(2);
                        String weight = path_stop.getString(3);
                        System.out.println(id_path + " " + stop_id + " " + weight);

                        String stop_string = "SELECT id, lat, lon, geopoint from stop where id = '"+ stop_id + "'";

                        stop = stmt3.executeQuery(stop_string);
                        stop.next();
                            String id_stop = stop.getString(1);
                            String lat = stop.getString(2);
                            String lon = stop.getString(3);
                            String geopoint = stop.getString(4);
                            System.out.println(id_stop + " " + lat + " " + lon + " " + geopoint);

                        }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt1.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt2.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt3.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rout_path.close(); } catch(SQLException se) { /*can't do anything */ }
            try { route.close(); } catch(SQLException se) { /*can't do anything */ }
            try { path_stop.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stop.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }

}
