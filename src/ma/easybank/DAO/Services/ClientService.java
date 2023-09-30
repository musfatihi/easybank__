package ma.easybank.DAO.Services;

import ma.easybank.DTO.Client;
import ma.easybank.DTO.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ClientService {

    private Connection connection;

    private static final String SAVE_CLIENT = "insert into clients (firstname,lastname, birthdate, address, phonenumber) values (?,?,?,?,?) returning code";

    private static final String UPDATE_CLIENT = "update clients set firstname=?,lastname=?,birthdate=?,address=?,phonenumber=? where code=?";

    private static final String DELETE_CLIENT = "update clients set deleted=true where code=?";

    private static final String FIND_CLIENT_CODE = "select * from clients where code=?";

    private static final String FIND_ALL_CLIENTS = "select * from clients where deleted=false";

    private static final String CHECK_CLIENT = "select count(*) from clients where code=? and deleted=false";




    //Constructor
    public ClientService(Connection connection) {
        this.connection = connection;
    }

    //Client Saving
    public Client saveClient(Client client) {

        try {

            PreparedStatement stmt = this.connection.prepareStatement(SAVE_CLIENT);

            stmt.setString(1, client.getFirstName());
            stmt.setString(2, client.getLastName());
            stmt.setDate(3, java.sql.Date.valueOf(client.getBirthDate()));
            stmt.setString(4, client.getAddress());
            stmt.setString(5, client.getPhoneNumber());

            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){

                client.setCode(resultSet.getInt(1));

            }

        } catch (Exception e) {

            System.out.println(e);

        }

        return client;

    }

    //Client Updating
    public Client updateClient(Client client) {

        int updatedRows=0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(UPDATE_CLIENT);

            stmt.setString(1, client.getFirstName());
            stmt.setString(2, client.getLastName());
            stmt.setDate(3, java.sql.Date.valueOf(client.getBirthDate()));
            stmt.setString(4, client.getAddress());
            stmt.setString(5, client.getPhoneNumber());

            stmt.setInt(6, client.getCode());

            updatedRows = stmt.executeUpdate();

            if(updatedRows>0){
                return client;
            }

        } catch (Exception e) {

            System.out.println(e);

        }

        return null;

    }

    //Client Deletion
    public Boolean deleteClient(Client client){

        int rowsUpdated = 0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(DELETE_CLIENT);

            stmt.setInt(1,client.getCode());

            rowsUpdated = stmt.executeUpdate();

        }
        catch (Exception e) {

            System.out.println(e);

        }

        return rowsUpdated>0;

    }

    //Find Employee
    public Optional<Client> findClientByCode(int code) {

        try {

            PreparedStatement stmt = this.connection.prepareStatement(FIND_CLIENT_CODE, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, code);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                Client client = new Client(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDate(4).toLocalDate(),resultSet.getString(5),resultSet.getString(6));

                return Optional.of(client);

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return Optional.empty();

    }

    //Find All Clients
    public List<Client> findAllClients() {

        List<Client> clients = new ArrayList<>();

        try {

            PreparedStatement stmt = this.connection.prepareStatement(FIND_ALL_CLIENTS, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                Client client = new Client(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDate(4).toLocalDate(),resultSet.getString(5),resultSet.getString(6));

                clients.add(client);

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return clients;

    }

    //Check client
    public Boolean exists(Client client){

        int count=0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(CHECK_CLIENT, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, client.getCode());

            ResultSet resultSet = stmt.executeQuery();

            resultSet.next();

            count = resultSet.getInt(1);

        }
        catch(Exception e){

            System.out.println(e);

        }

        return count>0;

    }


}
