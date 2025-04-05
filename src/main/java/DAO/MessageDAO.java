package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

        public List<Message> getAllMessages() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Message> messages = new ArrayList<>();
        try {
            connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            preparedStatement = connection.prepareStatement(sql);
           rs = preparedStatement.executeQuery();
           while (rs.next()){
            Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getInt("time_posted_epoch"));
            messages.add(message);
           }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) preparedStatement.close();
                
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return messages; 
    }

    public Message getMessageByMessageId(int message_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM message WHERE message_id;";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getInt("time_posted_epoch"));
                return message;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public List<Message> getAllMessagesByAccountID(int account_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Message> messages = new ArrayList<>();
        try {
            connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return messages; 
    }

    public Message insertMessage(Message message) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet pkeyResultSet = null; 

        try {
            connection = ConnectionUtil.getConnection();
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();

            pkeyResultSet = preparedStatement.getGeneratedKeys();

            if (pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getInt(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (pkeyResultSet != null) pkeyResultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null; 
    }

    public Message updatMessage(int message_id, Message message) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionUtil.getConnection();
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2,message_id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public Message deleteMessageById(int message_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Message message = getMessageByMessageId(message_id);
            if (message == null){
                return null;
            }

            connection = ConnectionUtil.getConnection();
            String sql = "DELETE FROM message WHERE message_id = ?;";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,message_id);
            preparedStatement.executeUpdate();

            return message;


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    
}
