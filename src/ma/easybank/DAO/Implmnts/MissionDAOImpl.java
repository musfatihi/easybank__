package ma.easybank.DAO.Implmnts;

import ma.easybank.DAO.Intrfcs.MissionDAO;
import ma.easybank.DAO.Services.MissionService;
import ma.easybank.DTO.Mission;

import java.util.List;

public class MissionDAOImpl implements MissionDAO {

    public static MissionService missionService;

    public MissionDAOImpl(MissionService missionService){

        MissionDAOImpl.missionService = missionService;

    }

    @Override
    public Mission save(Mission mission) {
        return missionService.saveMission(mission);
    }

    @Override
    public Boolean delete(Mission mission) {
        return missionService.deleteMission(mission);
    }

    public static List<Mission> findAll(MissionService missionService){
        return missionService.findAllMissions();
    }

}
