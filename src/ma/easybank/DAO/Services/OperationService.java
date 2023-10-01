package ma.easybank.DAO.Services;

import ma.easybank.DTO.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;

public class OperationService {

    private Connection connection;

    private static final String SAVE_OPERATION = "insert into operations (type,amount,accnbr,empmtrcl) values (?,?,?,?) returning nbr";

    private static final String DELETE_OPERATION = "update operations set deleted=true where nbr=?";

    private static final String FIND_OPERATION_NBR = "select * from operations where nbr=?";

    private static final String FIND_ACCOUNT_OPR_NBR = "select accounts.* from operations inner join accounts on operations.accnbr=accounts.nbr where operations.nbr=? ";





    //Constructor
    public OperationService(Connection connection) {

        this.connection = connection;

    }


    //Operation Saving
    public Operation saveOperation(Operation operation) {

        try {

            PreparedStatement stmt = this.connection.prepareStatement(SAVE_OPERATION);

            stmt.setString(1, operation.getType().toString());
            stmt.setDouble(2, operation.getAmount());
            stmt.setInt(3, operation.getAccount().getNbr());
            stmt.setInt(4,operation.getEmployee().getMtrcltNbr());


            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){

                operation.setNbr(resultSet.getInt(1));
                operation.setCrtnDate(LocalDate.now());

            }


        } catch (Exception e) {

            System.out.println(e);

        }

        return operation;

    }

    //Operation Deletion
    public Boolean deleteOperation(Operation operation){

        int rowsUpdated = 0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(DELETE_OPERATION);

            stmt.setInt(1,operation.getNbr());

            rowsUpdated = stmt.executeUpdate();

        }
        catch (Exception e) {

            System.out.println(e);

        }

        return rowsUpdated>0;

    }

    //Find Operation By nbr
    public Optional<Operation> findOperationByNbr(int nbr){

        try {

            PreparedStatement stmt = this.connection.prepareStatement(FIND_OPERATION_NBR, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, nbr);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                Operation operation = new Operation(resultSet.getInt(1), resultSet.getDate(2).toLocalDate(), Type.valueOf(resultSet.getString(3)), resultSet.getDouble(4), new Account(resultSet.getInt(5)), new Employee(resultSet.getInt(6)));

                return Optional.of(operation);

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return Optional.empty();

    }

    //Find Account By Operation nbr
    public Optional<Account> findAccountByOprNbr(Operation operation){

        try {

            PreparedStatement stmt = this.connection.prepareStatement(FIND_ACCOUNT_OPR_NBR, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, operation.getNbr());

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                Account account = new Account(resultSet.getInt(1),resultSet.getDouble(2),resultSet.getDate(3).toLocalDate(), State.valueOf(resultSet.getString(4)),new Client(resultSet.getInt(5)),new Employee(resultSet.getInt(6)));

                return Optional.of(account);

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return Optional.empty();

    }

}
