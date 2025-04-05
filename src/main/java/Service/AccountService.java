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
            throw new IllegalArgumentException("");
        }
        String password = account.getPassword();
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("");
        }
        if (accountExists(username)){
            throw new IllegalArgumentException("");
        }
        return accountDAO.insertAccount(account);
    }

    //Handle login

    public Account login(String username, String password) throws IllegalArgumentException {
        Account account = getAccountByUsername(username);

        if (account == null) {
            throw new IllegalArgumentException("");
        }

        if (!account.getPassword().equals(password)){
            throw new IllegalArgumentException("");
        }

        return account;
    }

    
}
