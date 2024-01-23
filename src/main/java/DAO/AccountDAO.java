package DAO;
import Model.*;
import java.sql.*;

public class AccountDAO{

    public static Account login(Account a, Connection c) throws Exception{
        try{
            PreparedStatement ps = c.prepareStatement("SELECT * FROM account WHERE username = ? AND password = ?");
            ps.setString(1, a.username);
            ps.setString(2, a.password);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return new Account(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3));
            }
            return null;
        }
        catch(SQLException s){
            throw new Exception();
        }
    }

    public static Account register(Account a, Connection c) throws Exception{
        if (a.password.length() < 4 || a.username.isBlank()){
            throw new Exception();
        }
        try{
            PreparedStatement ps = c.prepareStatement("INSERT INTO account (username, password) VALUES (?,?);");
            ps.setString(1, a.username);
            ps.setString(2, a.password);
            ps.execute();
        }
        catch (SQLException e){
            throw new Exception();
        }
        return login(a,c);
    }

    public static boolean postMessage(Message m, Connection c){
        return true;
    }

    public static boolean getMessage(int id, Connection c){
        return true;
    }

    public static boolean getMessages(int id, Connection c){
        return true;
    }

    public static boolean getAllMessages(){
        return true;
    }
}