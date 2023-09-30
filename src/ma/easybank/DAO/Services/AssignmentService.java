package ma.easybank.DAO.Services;

import ma.easybank.DTO.Assignment;
import ma.easybank.DTO.Employee;
import ma.easybank.DTO.Mission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AssignmentService {

    private Connection connection;

    private static final String SAVE_ASSIGNMENT = "insert into assignments(msncode,empmtrcl) values (?,?) returning id,startdate";

    private static final String DELETE_ASSIGNMENT = "update assignments set enddate=? where id=?";

    private static final String FIND_ASSIGNMENTS_EMPLOYEE = "select missions.name,missions.description,assignments.startdate,assignments.enddate from assignments inner join missions on assignments.msncode=missions.code where empmtrcl=? order by startdate desc";

    private static final String GET_STATS = "select count(*) from assignments";




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

    public List<Assignment> findByEmployee(Employee employee) {

        List<Assignment> assignments = new ArrayList<>();

        try {

            PreparedStatement stmt = this.connection.prepareStatement(FIND_ASSIGNMENTS_EMPLOYEE, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1,employee.getMtrcltNbr());

            ResultSet resultSet = stmt.executeQuery();

            Assignment assignment;

            while (resultSet.next()) {

                if(resultSet.getDate(4)!=null){
                    assignment = new Assignment(new Mission(resultSet.getString(1),resultSet.getString(2)),employee,resultSet.getDate(3).toLocalDate(),resultSet.getDate(4).toLocalDate());
                }else{
                    assignment = new Assignment(new Mission(resultSet.getString(1),resultSet.getString(2)),employee,resultSet.getDate(3).toLocalDate());
                }

                assignments.add(assignment);
            }

        } catch(Exception e){

            System.out.println(e);

        }

        return  assignments;

    }

    public HashMap<String,String> getStats() {

        HashMap<String,String> stats = new HashMap<>();

        try {

            PreparedStatement stmt = this.connection.prepareStatement(GET_STATS);

            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){
                stats.put("Total",String.valueOf(resultSet.getInt(1)));
            }


        } catch (Exception e) {

            System.out.println(e);

        }

        return stats;
    }

}
