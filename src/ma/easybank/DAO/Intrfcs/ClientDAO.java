package ma.easybank.DAO.Intrfcs;


import ma.easybank.DTO.Client;

import java.util.Optional;

public interface ClientDAO {

    Client save(Client client);

    Optional<Client> findBy(Client client);

    Boolean delete(Client client);

}
