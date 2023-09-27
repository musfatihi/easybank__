package ma.easybank.DAO.Services;

import ma.easybank.DTO.Mission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MissionService {

    private Connection connection;

    private static final String SAVE_MISSION = "insert into missions (name,description) values (?,?) returning code";

    private static final String DELETE_MISSION = "update missions set deleted=true where code=?";



    //Constructor
    public MissionService(Connection connection) {
        this.connection = connection;
    }

    //Mission Saving
    public Mission saveMission(Mission mission) {

        try {

            PreparedStatement stmt = this.connection.prepareStatement(SAVE_MISSION);

            stmt.setString(1, mission.getName());
            stmt.setString(2, mission.getDescription());

            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){
                mission.setCode(resultSet.getInt(1));
            }

        } catch (Exception e) {

            System.out.println(e);

        }

        return mission;

    }

    //Mission Deletion
    public Boolean deleteMission(Mission mission){

        int rowsUpdated = 0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(DELETE_MISSION);

            stmt.setInt(1,mission.getCode());

            rowsUpdated = stmt.executeUpdate();

        }
        catch (Exception e) {

            System.out.println(e);

        }

        return rowsUpdated>0;

    }

}
