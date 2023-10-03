package ma.easybank.DAO.Services;

import ma.easybank.DTO.Account;
import ma.easybank.DTO.Mission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MissionService {

    private Connection connection;

    private static final String SAVE_MISSION = "insert into missions (name,description) values (?,?) returning code";

    private static final String DELETE_MISSION = "update missions set deleted=true where code=?";

    private static final String FIND_ALL_MISSIONS = "select * from missions where deleted=false";

    private static final String CHECK_MISSION = "select count(*) from missions where code=? and deleted=false";




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

            resultSet.next();

            mission.setCode(resultSet.getInt(1));

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

    //Find All Missions
    public List<Mission> findAllMissions() {

        List<Mission> missions = new ArrayList<>();

        try {

            PreparedStatement stmt = this.connection.prepareStatement(FIND_ALL_MISSIONS, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                Mission mission = new Mission(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3));

                missions.add(mission);

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return missions;

    }

    //Check mission
    public Boolean exists(Mission mission){

        int count=0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(CHECK_MISSION, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, mission.getCode());

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
