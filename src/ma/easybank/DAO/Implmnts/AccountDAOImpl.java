package ma.easybank.DAO.Implmnts;

import ma.easybank.DAO.Intrfcs.AccountDAO;
import ma.easybank.DAO.Services.AccountService;
import ma.easybank.DTO.Account;
import ma.easybank.DTO.Client;
import ma.easybank.DTO.State;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountDAOImpl implements AccountDAO {

    public static AccountService accountService;

    public AccountDAOImpl(AccountService accountService){

        AccountDAOImpl.accountService = accountService;

    }

    @Override
    public List<Account> findBy(Client client) {
        return accountService.findAccountsByClient(client);
    }

    @Override
    public Boolean delete(Account account) {
        return accountService.deleteAccount(account);
    }

    public static Map<String, List<Account>> findAll(){

        return accountService.findAllAccounts();

    }

    public static Boolean changeState(Account account, State state) {
        return accountService.changeState(account,state.toString());
    }

    public static Map<String, List<Account>> findAllByState(){

        Map<String, List<Account>> currentaccnt = accountService.findAllAccounts().get("current").stream()
                .collect(Collectors.groupingBy(
                        account -> {
                            if (account.getState().toString().equals("Active")) {
                                return "Active";
                            } else {
                                return "Blocked";
                            }
                        }
                ));


        Map<String, List<Account>> savingaccnt = accountService.findAllAccounts().get("savings").stream()
                .collect(Collectors.groupingBy(
                        account -> {
                            if (account.getState().toString().equals("Active")) {
                                return "Active";
                            } else {
                                return "Blocked";
                            }
                        }
                ));

        return combineMaps(currentaccnt,savingaccnt);
    }


    public static Map<String, List<Account>> combineMaps(Map<String, List<Account>> map1, Map<String, List<Account>> map2) {
        Map<String, List<Account>> combinedMap = new HashMap<>();

        // Add all entries from map1 to combinedMap
        combinedMap.putAll(map1);

        // Merge entries from map2 into combinedMap
        for (Map.Entry<String, List<Account>> entry : map2.entrySet()) {
            String key = entry.getKey();
            List<Account> values = entry.getValue();

            // If the key already exists in combinedMap, append the values
            if (combinedMap.containsKey(key)) {
                combinedMap.get(key).addAll(values);
            }
            // If the key is not in combinedMap, add it with the values from map2
            else {
                combinedMap.put(key, new ArrayList<>(values));
            }
        }

        return combinedMap;
    }

    public static Map<LocalDate, List<Account>> combineMapsDate(Map<LocalDate, List<Account>> map1, Map<LocalDate, List<Account>> map2) {
        Map<LocalDate, List<Account>> combinedMap = new HashMap<>();

        // Add all entries from map1 to combinedMap
        combinedMap.putAll(map1);

        // Merge entries from map2 into combinedMap
        for (Map.Entry<LocalDate, List<Account>> entry : map2.entrySet()) {
            LocalDate key = entry.getKey();
            List<Account> values = entry.getValue();

            // If the key already exists in combinedMap, append the values
            if (combinedMap.containsKey(key)) {
                combinedMap.get(key).addAll(values);
            }
            // If the key is not in combinedMap, add it with the values from map2
            else {
                combinedMap.put(key, new ArrayList<>(values));
            }
        }

        return combinedMap;
    }

    public static Map<LocalDate, List<Account>> findAllByDate() {

        Map<LocalDate, List<Account>> currentaccnt = accountService.findAllAccounts().get("current").stream()
                .collect(Collectors.groupingBy(Account::getCrtnDate));

        Map<LocalDate, List<Account>> savingsaccnt = accountService.findAllAccounts().get("savings").stream()
                .collect(Collectors.groupingBy(Account::getCrtnDate));

        return combineMapsDate(currentaccnt,savingsaccnt);
    }

}
