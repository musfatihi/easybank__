package ma.easybank.DAO.Implmnts;

import ma.easybank.DAO.Intrfcs.OperationDAO;
import ma.easybank.DAO.Services.CurrentaccntService;
import ma.easybank.DAO.Services.OperationService;
import ma.easybank.DAO.Services.SavingsaccntService;
import ma.easybank.DTO.Account;
import ma.easybank.DTO.Operation;

import java.util.Optional;

public class OperationDAOImpl implements OperationDAO {

    public static OperationService operationService;
    public static CurrentaccntService currentaccntService;
    public static SavingsaccntService savingsaccntService;

    public OperationDAOImpl(OperationService operationService){
        OperationDAOImpl.operationService = operationService;
    }

    @Override
    public Operation save(Operation operation) {
        return operationService.saveOperation(operation);
    }

    @Override
    public Optional<Operation> findBy(Operation operation) {
        return operationService.findOperationByNbr(operation.getNbr());
    }

    @Override
    public Boolean delete(Operation operation) {
        return operationService.deleteOperation(operation);
    }

    public static Boolean isValid(Operation operation,SavingsaccntService savingsaccntService,CurrentaccntService currentaccntService) {
        return currentaccntService.isWithdrawalValid(operation) || savingsaccntService.isWithdrawalValid(operation);
    }

    public static Optional<Account> findAccountByOprNbr(Operation operation){
        return operationService.findAccountByOprNbr(operation);
    }

}
