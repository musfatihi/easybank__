package ma.easybank.DAO.Intrfcs;


import ma.easybank.DTO.Client;
import ma.easybank.DTO.Currentaccnt;

import java.util.List;

public interface CurrentaccntDAO {

    Currentaccnt save(Currentaccnt currentaccnt);

    List<Currentaccnt> findBy(Client client);

}
