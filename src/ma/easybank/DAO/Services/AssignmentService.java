package ma.easybank.DAO.Services;

import ma.easybank.DTO.Assignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class AssignmentService {

    private Connection connection;

    private static final String SAVE_ASSIGNMENT = "insert into assignments(msncode,empmtrcl) values (?,?) returning id,startdate";

    private static final String DELETE_ASSIGNMENT = "update assignments set enddate=? where id=?";


    public AssignmentService(Connection connection){
        this.connection = connection;
    }


    public Assignment save(Assignment assignment) {

        try {

            PreparedStatement stmt = this.connection.prepareStatement(SAVE_ASSIGNMENT);

            stmt.setInt(1, assignment.getMission().getCode());
            stmt.setInt(2, assignment.getEmployee().getMtrcltNbr());

            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){
                assignment.setId(resultSet.getInt(1));
                assignment.setStartDate(resultSet.getDate(2).toLocalDate());
            }


        } catch (Exception e) {

            System.out.println(e);

        }

        return assignment;
    }

    public Boolean delete(Assignment assignment){

        int rowsUpdated = 0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(DELETE_ASSIGNMENT);

            stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setInt(2,assignment.getId());

            rowsUpdated = stmt.executeUpdate();

        }
        catch (Exception e) {

            System.out.println(e);

        }

        return rowsUpdated>0;

    }

}
