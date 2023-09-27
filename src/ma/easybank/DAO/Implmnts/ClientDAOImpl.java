package ma.easybank.DAO.Implmnts;

import ma.easybank.DAO.Intrfcs.ClientDAO;
import ma.easybank.DAO.Services.ClientService;
import ma.easybank.DAO.Services.EmployeeService;
import ma.easybank.DTO.Client;

import java.util.Optional;

public class ClientDAOImpl implements ClientDAO {

    public static ClientService clientService;

    public ClientDAOImpl(ClientService clientService){
        ClientDAOImpl.clientService = clientService;
    }

    @Override
    public Client save(Client client) {
        return clientService.saveClient(client);
    }

    @Override
    public Optional<Client> findBy(Client client) {
        return clientService.findClientByCode(client.getCode());
    }

    @Override
    public Boolean delete(Client client) {
        return clientService.deleteClient(client);
    }

}
