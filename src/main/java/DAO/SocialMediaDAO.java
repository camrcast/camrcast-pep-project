package DAO;
import Model.*;
import java.sql.*;
import java.util.*;

public class SocialMediaDAO{

    public static Account login(Account a, Connection c){
        try{
            PreparedStatement ps = c.prepareStatement("SELECT * FROM account WHERE username = ? AND password = ?;");
            ps.setString(1, a.username);
            ps.setString(2, a.password);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        }
        catch(SQLException s){
            return new Account(-1,"","");
        }
        return new Account(-1,"","");
    }

    public static Account register(Account a, Connection c){
        if (a.password.length() < 4 || a.username.isBlank()){
            return new Account(-1,"","");
        }
        try{
            PreparedStatement ps = c.prepareStatement("INSERT INTO account (username, password) VALUES (?,?);");
            ps.setString(1, a.username);
            ps.setString(2, a.password);
            ps.execute();
        }
        catch (SQLException e){
            return new Account(-1,"","");
        }
        return login(a,c);
    }

    public static boolean deleteMessage(Message m, Connection c){
        return true;
    }

    public static boolean postMessage(Message m, Connection c){
        return true;
    }

    public static Message getMessage(int id, Connection c){
        try{
            PreparedStatement ps = c.prepareStatement("SELECT * FROM message WHERE message_id = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4));
            }
        }
        catch(SQLException e){
            return new Message(-1,0,"",0);
        }
        return new Message(-1,0,"",0);
    }

    public static boolean getMessagesByUser(int id, Connection c){
        return true;
    }

    public static List<Message> getAllMessages(Connection c){
        List<Message> mess = new ArrayList<Message>();
        try{
            PreparedStatement ps = c.prepareStatement("SELECT * FROM message;");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                mess.add(new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4)));
            }
        }
        catch (SQLException e){
            return mess;
        }
        return mess;
    }
}