package ma.easybank.DAO.Services;

import ma.easybank.DTO.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountService {
    private Connection connection;


    private static final String DELETE_ACCOUNT = "update accounts set deleted=true where nbr=?";

    private static final String FIND_CURRENT_ACCOUNTS_CLIENT = "SELECT * FROM (SELECT accounts.nbr,accounts.balance,accounts.crtndate,accounts.state,accounts.idclient,accounts.createdby,currentaccnt.overdraft FROM accounts INNER JOIN currentaccnt ON accounts.nbr = currentaccnt.accnbr) AS SR WHERE SR.idclient=?";

    private static final String FIND_SAVINGS_ACCOUNTS_CLIENT = "SELECT * FROM (SELECT accounts.nbr,accounts.balance,accounts.crtndate,accounts.state,accounts.idclient,accounts.createdby,savingsaccnt.interestrate FROM accounts INNER JOIN savingsaccnt ON accounts.nbr = savingsaccnt.accnbr) AS SR WHERE SR.idclient=?";

    private static final String FIND_SAVINGS_ACCOUNTS = "SELECT accounts.nbr,accounts.balance,accounts.crtndate,accounts.state,accounts.idclient,accounts.createdby,savingsaccnt.interestrate FROM accounts INNER JOIN savingsaccnt ON accounts.nbr = savingsaccnt.accnbr where accounts.deleted!=true";

    private static final String FIND_CURRENT_ACCOUNTS = "SELECT accounts.nbr,accounts.balance,accounts.crtndate,accounts.state,accounts.idclient,accounts.createdby,currentaccnt.overdraft FROM accounts INNER JOIN currentaccnt ON accounts.nbr = currentaccnt.accnbr where accounts.deleted!=true";

    private static final String CHANGE_STATE = "update accounts set state=? where nbr=?";

    private static final String CHECK_ACCOUNT = "select count(*) from accounts where nbr=? and deleted=false";


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

    //Find All Accounts
    public Map<String, List<Account>> findAllAccounts() {


        Map<String, List<Account>> accounts = new HashMap<>();


        try {

            PreparedStatement stmt1 = this.connection.prepareStatement(FIND_CURRENT_ACCOUNTS, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet1 = stmt1.executeQuery();

            List<Account> currentAccounts = new ArrayList<>();

            while (resultSet1.next()) {

                Account account = new Currentaccnt(resultSet1.getInt(1),resultSet1.getFloat(2),resultSet1.getDate(3).toLocalDate(), State.valueOf(resultSet1.getString(4)),new Client(resultSet1.getInt(5)),new Employee(resultSet1.getInt(6)),resultSet1.getFloat(7));

                currentAccounts.add(account);

            }

            PreparedStatement stmt2 = this.connection.prepareStatement(FIND_SAVINGS_ACCOUNTS, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet2 = stmt2.executeQuery();

            List<Account> savingsAccounts = new ArrayList<>();

            while (resultSet2.next()) {

                Account account = new Savingsaccnt(resultSet2.getInt(1),resultSet2.getFloat(2),resultSet2.getDate(3).toLocalDate(), State.valueOf(resultSet2.getString(4)),new Client(resultSet2.getInt(5)),new Employee(resultSet2.getInt(6)),resultSet2.getFloat(7));

                savingsAccounts.add(account);

            }

            accounts.put("current",currentAccounts);
            accounts.put("savings",savingsAccounts);

        }
        catch(Exception e){

            System.out.println(e);

        }

        return accounts;

    }

    public Boolean changeState(Account account,String state){

        int rowsUpdated = 0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(CHANGE_STATE);

            stmt.setString(1,state);
            stmt.setInt(2,account.getNbr());

            rowsUpdated = stmt.executeUpdate();

        }
        catch (Exception e) {

            System.out.println(e);

        }

        return rowsUpdated>0;

    }

    //Check account
    public Boolean exists(Account account){

        int count=0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(CHECK_ACCOUNT, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, account.getNbr());

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
