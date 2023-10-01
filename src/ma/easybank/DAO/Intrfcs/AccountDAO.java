package ma.easybank.DAO.Intrfcs;


import ma.easybank.DTO.Account;
import ma.easybank.DTO.Client;

import java.util.List;

public interface AccountDAO {

     List<Account> findBy(Client client);

     Boolean delete(Account account);

}
