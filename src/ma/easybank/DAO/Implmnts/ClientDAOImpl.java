package ma.easybank.DAO.Implmnts;

import ma.easybank.DAO.Intrfcs.GenericInterface;
import ma.easybank.DAO.Services.ClientService;
import ma.easybank.DTO.Client;
import ma.easybank.UTILS.Helpers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientDAOImpl implements GenericInterface<Client> {

    public static ClientService clientService;

    public ClientDAOImpl(ClientService clientService){
        ClientDAOImpl.clientService = clientService;
    }

    @Override
    public Client save(Client client) {
        return clientService.saveClient(client);
    }

    @Override
    public Client update(Client client) {
        return clientService.updateClient(client);
    }

    @Override
    public Optional<Client> findBy(Client client) {
        return clientService.findClientByCode(client.getCode());
    }

    @Override
    public Boolean delete(Client client) {
        return clientService.deleteClient(client);
    }

    public static List<Client> findAll() {
        return clientService.findAllClients();
    }

    public static List<Client> findByFirstName(String firstName) {
        return clientService.findAllClients().stream()
                .filter(client -> client.getFirstName().equals(firstName))
                .collect(Collectors.toList());
    }

    public static List<Client> findByLastName(String lastName) {
        return clientService.findAllClients().stream()
                .filter(client -> client.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    public static List<Client> findByAddress(String address) {
        return clientService.findAllClients().stream()
                .filter(client -> client.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    public static List<Client> findByPhoneNbr(String phoneNbr) {
        return clientService.findAllClients().stream()
                .filter(client -> client.getPhoneNumber().equals(phoneNbr))
                .collect(Collectors.toList());
    }

    public static List<Client> findByBirthDate(String birthDate) {
        return clientService.findAllClients().stream()
                .filter(client -> client.getBirthDate().equals(Helpers.strToDate(birthDate)))
                .collect(Collectors.toList());
    }


}
