package ma.easybank.DAO.Services;

import ma.easybank.DTO.Operation;
import ma.easybank.DTO.Savingsaccnt;
import ma.easybank.DTO.State;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class SavingsaccntService {

    private Connection connection;

    private static final String SAVE_SAVINGS_ACCOUNT = "with i as (insert into accounts (idclient, createdby) values (?,?) returning nbr) insert into savingsaccnt (accnbr,interestrate) select nbr,? from i returning accnbr";

    private static final String CHECK_ACCOUNT_WITHDRAWAL = "select count(*) from savingsaccnt \n" +
            "left join accounts \n" +
            "on accounts.nbr=savingsaccnt.accnbr \n" +
            "where accounts.nbr=? and accounts.state='Active' and accounts.balance-?>0";


    public SavingsaccntService(Connection connection){

        this.connection = connection;

    }

    //Savings Account Adding
    public Savingsaccnt saveSavingsaccnt(Savingsaccnt savingsaccnt) {


        try {

            PreparedStatement stmt = this.connection.prepareStatement(SAVE_SAVINGS_ACCOUNT);

            stmt.setInt(1, savingsaccnt.getClient().getCode());
            stmt.setInt(2, savingsaccnt.getCreatedBy().getMtrcltNbr());
            stmt.setFloat(3, savingsaccnt.getInterestrate());


            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){
                savingsaccnt.setBalance(0);
                savingsaccnt.setCrtnDate(LocalDate.now());
                savingsaccnt.setState(State.Active);
                savingsaccnt.setNbr(resultSet.getInt(1));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return savingsaccnt;

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
