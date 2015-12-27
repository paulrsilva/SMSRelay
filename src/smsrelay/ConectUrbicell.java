/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smsrelay;

import java.sql.*;

/**
 *
 * @author paulinorochaesilva
 */
public class ConectUrbicell {
    public void Conectar() throws ClassNotFoundException{
    
        String connectionUrl="jdbc:sqlserver://br.urbicell.com:1433;"+"databaseName=SmsServer;user=sa;password=asfadas@2014";
   
        
        //Declarando Objetos JDBC
        
        Connection con=null;
        Statement stmt=null;
        ResultSet rs=null;
        
        try {
         // Establish the connection.
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         con = DriverManager.getConnection(connectionUrl);

         // Create and execute an SQL statement that returns some data.
         String SQL = "SELECT * from ResumoSMS";
         stmt = con.createStatement();
         rs = stmt.executeQuery(SQL);

         // Iterate through the data in the result set and display it.
         while (rs.next()) {
            System.out.println(rs.getString(4) + " " + rs.getString(6));
         }
      }

      // Handle any errors that may have occurred.
      catch (Exception e) {
         e.printStackTrace();
      }
      finally {
         if (rs != null) try { rs.close(); } catch(Exception e) {}
         if (stmt != null) try { stmt.close(); } catch(Exception e) {}
         if (con != null) try { con.close(); } catch(Exception e) {}
      }
   }
}