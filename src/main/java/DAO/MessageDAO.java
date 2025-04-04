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

    public Message getMessageById(int message_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM message WHERE message_id";
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
    
}
