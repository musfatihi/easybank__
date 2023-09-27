package ma.easybank.DAO.Implmnts;

import ma.easybank.DAO.Intrfcs.AccountDAO;
import ma.easybank.DAO.Services.AccountService;
import ma.easybank.DTO.Account;
import ma.easybank.DTO.Client;
import ma.easybank.DTO.State;

import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    public static AccountService accountService;

    public AccountDAOImpl(AccountService accountService){

        AccountDAOImpl.accountService = accountService;

    }

    @Override
    public List<Account> findBy(Client client) {
        return accountService.findAccountsByClient(client);
    }

    @Override
    public Boolean delete(Account account) {
        return accountService.deleteAccount(account);
    }

    @Override
    public Boolean changeState(Account account, State state) {
        return null;
    }
}
