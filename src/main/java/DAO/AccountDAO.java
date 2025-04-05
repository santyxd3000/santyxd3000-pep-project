package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    public Account insertAccount(Account account){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet pkeyResultSet = null;
        try {
            connection = ConnectionUtil.getConnection();
            String sql = "INSERT INTO account (username, password) VALUES (?,?);";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());

            preparedStatement.executeUpdate();

            pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getInt(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
            } catch (SQLException e) {

                System.out.println(e.getMessage());

            }
            finally {
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

        public Account getAccountByUserName(String username) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null; 
            try {
                connection = ConnectionUtil.getConnection();
    
               
                String sql = "SELECT account_id, password FROM account WHERE username = ?";
                preparedStatement = connection.prepareStatement(sql);
    
                
                preparedStatement.setString(1, username);
                
                
                resultSet = preparedStatement.executeQuery();
    
                
                if (resultSet.next()) {
                    int accountId = resultSet.getInt("account_id");
                    String password = resultSet.getString("password");
                    return new Account(accountId, username, password);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    if (resultSet != null) resultSet.close();
                    if (preparedStatement != null) preparedStatement.close();
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            return null;
        }

    public boolean accountExistsById (int account_id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionUtil.getConnection();

            String sql = "SELECT 1 FROM account WHERE account_id = ?;";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,account_id);

            rs = preparedStatement.executeQuery();

            return rs.next();

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


        return false;
    }

        



}
