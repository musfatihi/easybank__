package ma.easybank.DAO.Intrfcs;

import ma.easybank.DTO.Mission;


public interface MissionDAO {

    Mission save(Mission mission);

    Boolean delete(Mission mission);

}
