package Service;
import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account getAccountByUsername(String username) {
        return accountDAO.getAccountByUserName(username);
    }
    public boolean accountExists(String username){
        return getAccountByUsername(username) != null;
    }

    public Account registerAccount (Account account) throws IllegalArgumentException{
        String username = account.getUsername();
        if (username == null || username.isBlank()){
            throw new IllegalArgumentException("User cannot be blank");
        }
        String password = account.getPassword();
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("String must be at least 4 characters");
        }
        if (accountExists(username)){
            throw new IllegalArgumentException("Account with the username already exists");
        }
        return accountDAO.insertAccount(account);
    }

    //Handle login

    public Account login (String username, String password) throws IllegalArgumentException {
        Account account = getAccountByUsername(username);
        
        if (account == null) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        if (!account.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid username or password");

        return account;

    }
    
}
