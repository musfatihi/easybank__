package ma.easybank.DAO.Intrfcs;


import ma.easybank.DTO.Account;
import ma.easybank.DTO.Client;
import ma.easybank.DTO.State;

import java.util.List;

public interface AccountDAO {

     List<Account> findBy(Client client);

     Boolean delete(Account account);

}
