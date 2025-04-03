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




}
