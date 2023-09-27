package ma.easybank.DAO.Services;

import ma.easybank.DTO.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private Connection connection;


    private static final String DELETE_ACCOUNT = "update accounts set deleted=true where nbr=?";

    private static final String FIND_CURRENT_ACCOUNTS_CLIENT = "SELECT * FROM (SELECT accounts.nbr,accounts.balance,accounts.crtndate,accounts.state,accounts.idclient,accounts.createdby,currentaccnt.overdraft FROM accounts INNER JOIN currentaccnt ON accounts.nbr = currentaccnt.accnbr) AS SR WHERE SR.idclient=?";

    private static final String FIND_SAVINGS_ACCOUNTS_CLIENT = "SELECT * FROM (SELECT accounts.nbr,accounts.balance,accounts.crtndate,accounts.state,accounts.idclient,accounts.createdby,savingsaccnt.interestrate FROM accounts INNER JOIN savingsaccnt ON accounts.nbr = savingsaccnt.accnbr) AS SR WHERE SR.idclient=?";


    //Constructor
    public AccountService(Connection connection) {

        this.connection = connection;

    }


    //Account Deletion
    public Boolean deleteAccount(Account account){

        int rowsUpdated = 0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(DELETE_ACCOUNT);

            stmt.setInt(1,account.getNbr());

            rowsUpdated = stmt.executeUpdate();

        }
        catch (Exception e) {

            System.out.println(e);

        }

        return rowsUpdated>0;

    }

    //Find Accounts By Client
    public List<Account> findAccountsByClient(Client client) {

        List<Account> accounts = new ArrayList<>();

        try {

            PreparedStatement stmt1 = this.connection.prepareStatement(FIND_CURRENT_ACCOUNTS_CLIENT, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt1.setInt(1, client.getCode());

            ResultSet resultSet = stmt1.executeQuery();


            while (resultSet.next()) {

                Account account = new Currentaccnt(resultSet.getInt(1),resultSet.getFloat(2),resultSet.getDate(3).toLocalDate(), State.valueOf(resultSet.getString(4)),new Client(resultSet.getInt(5)),new Employee(resultSet.getInt(6)),resultSet.getFloat(7));

                accounts.add(account);

            }


            PreparedStatement stmt2 = this.connection.prepareStatement(FIND_SAVINGS_ACCOUNTS_CLIENT, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt2.setInt(1, client.getCode());

            ResultSet resultSet2 = stmt2.executeQuery();

            while (resultSet2.next()) {

                Account account = new Savingsaccnt(resultSet2.getInt(1),resultSet2.getFloat(2),resultSet2.getDate(3).toLocalDate(), State.valueOf(resultSet2.getString(4)),new Client(resultSet2.getInt(5)),new Employee(resultSet2.getInt(6)),resultSet2.getFloat(7));

                accounts.add(account);

            }

        }
        catch(Exception e){


            System.out.println(e);

        }

        return  accounts;

    }

}
