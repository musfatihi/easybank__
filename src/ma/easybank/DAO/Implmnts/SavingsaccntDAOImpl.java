package ma.easybank.DAO.Implmnts;

import ma.easybank.DAO.Intrfcs.SavingsaccntDAO;
import ma.easybank.DAO.Services.CurrentaccntService;
import ma.easybank.DAO.Services.SavingsaccntService;
import ma.easybank.DTO.Client;
import ma.easybank.DTO.Savingsaccnt;

import java.util.List;

public class SavingsaccntDAOImpl implements SavingsaccntDAO {

    public static SavingsaccntService savingsaccntService;

    public SavingsaccntDAOImpl(SavingsaccntService savingsaccntService){
        SavingsaccntDAOImpl.savingsaccntService = savingsaccntService;
    }
    @Override
    public Savingsaccnt save(Savingsaccnt savingsaccnt) {
        return savingsaccntService.saveSavingsaccnt(savingsaccnt);
    }

    @Override
    public List<Savingsaccnt> findBy(Client client) {
        return null;
    }
}
