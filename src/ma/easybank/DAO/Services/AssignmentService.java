package ma.easybank.DAO.Services;

import ma.easybank.DTO.Assignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AssignmentService {

    private Connection connection;

    private static final String SAVE_ASSIGNMENT = "insert into assignments(msncode,empmtrcl) values (?,?) returning id,startdate";


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

}
