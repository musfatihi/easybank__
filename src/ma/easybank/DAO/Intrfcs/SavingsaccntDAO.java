package ma.easybank.DAO.Intrfcs;

import ma.easybank.DTO.Client;
import ma.easybank.DTO.Savingsaccnt;

import java.util.List;

public interface SavingsaccntDAO {

    Savingsaccnt save(Savingsaccnt savingsaccnt);

    List<Savingsaccnt> findBy(Client client);

}
