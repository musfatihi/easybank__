package ma.easybank.DAO.Services;

import ma.easybank.DTO.Currentaccnt;
import ma.easybank.DTO.Operation;
import ma.easybank.DTO.State;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class CurrentaccntService {
    private Connection connection;

    private static final String SAVE_CURRENT_ACCOUNT = "with i as (insert into accounts (idclient, createdby) values (?,?) returning nbr) insert into currentaccnt (accnbr,overdraft) select nbr,? from i returning accnbr";

    private static final String CHECK_ACCOUNT_WITHDRAWAL = "select count(*) from currentaccnt \n" +
            "left join accounts \n" +
            "on accounts.nbr=currentaccnt.accnbr \n" +
            "where accounts.nbr=? and accounts.state='Active' and accounts.balance-?>-currentaccnt.overdraft";


    public CurrentaccntService(Connection connection){
        this.connection = connection;
    }


    //Current Account Adding
    public Currentaccnt saveCurrentaccnt(Currentaccnt currentaccnt) {

        try {

            PreparedStatement stmt = this.connection.prepareStatement(SAVE_CURRENT_ACCOUNT);

            stmt.setInt(1, currentaccnt.getClient().getCode());
            stmt.setInt(2, currentaccnt.getCreatedBy().getMtrcltNbr());
            stmt.setFloat(3, currentaccnt.getOverdraft());


            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){
                currentaccnt.setBalance(0);
                currentaccnt.setState(State.Active);
                currentaccnt.setNbr(resultSet.getInt(1));
                currentaccnt.setCrtnDate(LocalDate.now());
            }

        } catch (Exception e) {

            System.out.println(e);

        }

        return currentaccnt;

    }

    //Check Withdarawal
    public Boolean isWithdrawalValid(Operation operation){

        int found=0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(CHECK_ACCOUNT_WITHDRAWAL);

            stmt.setInt(1,operation.getAccount().getNbr());

            stmt.setDouble(2,operation.getAmount());

            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){
                found = resultSet.getInt(1);
            }

        }
        catch (Exception e) {

            System.out.println(e);

        }

        return found>0;

    }

}
