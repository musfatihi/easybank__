package ma.easybank.DAO.Services;

import ma.easybank.DTO.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;


public class ClientService {

    private Connection connection;

    private static final String SAVE_CLIENT = "insert into clients (firstname,lastname, birthdate, address, phonenumber) values (?,?,?,?,?) returning code";

    private static final String DELETE_CLIENT = "update clients set deleted=true where code=?";

    private static final String FIND_CLIENT_CODE = "select * from clients where code=?";


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


}
