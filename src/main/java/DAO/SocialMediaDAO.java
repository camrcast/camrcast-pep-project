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

    public static Message deleteMessage(int id, Connection c){
        try{
            Message m = getMessage(id, c);
            PreparedStatement ps = c.prepareStatement("DELETE FROM message WHERE message_id = ?");
            ps.setInt(1, id);
            ps.execute();
            return m;
        }
        catch(SQLException e){
            return new Message(-1, "", 0);
        }
    }

    public static Message postMessage(Message m, Connection c){
        if (m.getMessage_text().length() > 255 || m.getMessage_text().isBlank()){
            return new Message(-1, -1, "", 0);
        }
        try{
            PreparedStatement ps = c.prepareStatement("SELECT * FROM account WHERE account_id = ?;");
            ps.setInt(1, m.getPosted_by());
            ps.executeQuery();
            ps = c.prepareStatement("INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?);");
            ps.setInt(1, m.getPosted_by());
            ps.setString(2, m.getMessage_text());
            ps.setLong(3, m.getTime_posted_epoch());
            ps.execute();
            ps = c.prepareStatement("SELECT message_id FROM message WHERE posted_by = ? AND message_text = ? AND time_posted_epoch = ?;");
            ps.setInt(1, m.getPosted_by());
            ps.setString(2, m.getMessage_text());
            ps.setLong(3, m.getTime_posted_epoch());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                m.setMessage_id(rs.getInt(1));
                return m;
            }
        }
        catch(SQLException e){
            return new Message(-1,-1,"",0);
        }
        return new Message(-1,-1,"",0);
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

    public static List<Message> getMessagesByUser(int id, Connection c){
        List<Message> mess = new ArrayList<Message>();
        try{
            PreparedStatement ps = c.prepareStatement("SELECT * FROM message WHERE posted_by = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                mess.add(new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4)));
            }
        }
        catch(SQLException e){
            return mess;
        }
        return mess;
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

    public static Message updateMessage(String text, int id, Connection c){
        if (text.length() > 255 || text.isBlank()){
            return new Message(-1, -1, "", 0);
        }
        try{
            PreparedStatement ps = c.prepareStatement("UPDATE message SET message_text = ? WHERE message_id = ?");
            ps.setString(1, text);
            ps.setInt(2, id);
            ps.executeUpdate();
            return getMessage(id, c);
        }
        catch(SQLException e){
            return new Message(-1, -1, "", 0);
        }
    }
}