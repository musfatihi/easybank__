package ma.easybank.DAO.Implmnts;

import ma.easybank.DAO.Intrfcs.CurrentaccntDAO;
import ma.easybank.DAO.Services.CurrentaccntService;
import ma.easybank.DTO.Client;
import ma.easybank.DTO.Currentaccnt;

import java.util.List;

public class CurrentaccntDAOImpl implements CurrentaccntDAO {

    public static CurrentaccntService currentaccntService;

    public CurrentaccntDAOImpl(CurrentaccntService currentaccntService){
        CurrentaccntDAOImpl.currentaccntService = currentaccntService;
    }

    @Override
    public Currentaccnt save(Currentaccnt currentaccnt) {
        return currentaccntService.saveCurrentaccnt(currentaccnt);
    }

    @Override
    public List<Currentaccnt> findBy(Client client) {
        return null;
    }
}
