package ma.easybank.UTILS;

import ma.easybank.DAO.Implmnts.*;
import ma.easybank.DAO.Services.*;
import ma.easybank.DTO.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.*;

public class App {

    private static String[] options = {
            "Ajouter un employé",
            "Supprimer un employé",
            "Chercher un employé",
            "Ajouter un client",
            "Supprimer un client",
            "Chercher un client",
            "Créer un compte",
            "Supprimer un compte",
            "Chercher les comptes par client",
            "Lancer une opération",
            "Supprimer une opération",
            "Chercher une opération",
            "Ajouter une mission",
            "Supprimer une mission",
            "Afficher la liste des employés",
            "Chercher un employé",
            "Afficher la liste des clients",
            "Chercher un client",
            "Afficher les comptes",
            "Changer le status d'un compte",
            "Afficher toutes les missions",
            "Créer une nouvelle affectation",
            "Supprimer une affectation",
            "Affficher la liste des comptes par état",
            "Afficher la liste des comptes par date",
            "Mettre à jour un employé",
            "Mettre à jour un client",
            "Modifier un compte",
            "Chercher un compte par numèro d'opération",
            "Afficher l'historique des affectations d'un employé",
            "Afficher les statistiques"
    };



    //------------------------------Services-------------------------

    private static EmployeeService employeeService;
    private static ClientService clientService;
    private static AccountService accountService;
    private static CurrentaccntService currentaccntService;
    private static SavingsaccntService savingsaccntService;
    private static OperationService operationService;
    private static MissionService missionService;
    private static AssignmentService assignmentService;


    //----------------------------------DAOs---------------------------------------
    private static EmployeeDAOImpl employeeDAO;
    private static ClientDAOImpl clientDAO;
    private static CurrentaccntDAOImpl currentaccntDAO;
    private static SavingsaccntDAOImpl savingsaccntDAO;
    private static AccountDAOImpl accountDAO;
    private static OperationDAOImpl operationDAO;
    private static MissionDAOImpl missionDAO;
    private static AssignmentDAOImpl assignmentDAO;


    public static void start(){


        //-----------------Connection to DB-----------

        Connection connection = DBConnection.makeConnection();

        //-----------------Services---------------------

        employeeService = new EmployeeService(connection);

        clientService = new ClientService(connection);

        accountService = new AccountService(connection);

        currentaccntService = new CurrentaccntService(connection);

        savingsaccntService = new SavingsaccntService(connection);

        operationService = new OperationService(connection);

        missionService = new MissionService(connection);

        assignmentService = new AssignmentService(connection);

        //-------------------DAOs----------------------------

        employeeDAO = new EmployeeDAOImpl(employeeService);

        clientDAO = new ClientDAOImpl(clientService);

        accountDAO = new AccountDAOImpl(accountService);

        currentaccntDAO = new CurrentaccntDAOImpl(currentaccntService);

        savingsaccntDAO = new SavingsaccntDAOImpl(savingsaccntService);

        operationDAO = new OperationDAOImpl(operationService);

        missionDAO = new MissionDAOImpl(missionService);

        assignmentDAO = new AssignmentDAOImpl(assignmentService);

        while(true){

            showOptions(options);

            int option = takeInput(1, options.length);

            treatement(option);

        }

    }

    private static void showOptions(String[] options){
        int i=1;
        for (String option : options) {
            System.out.println(i+" "+option);
            i++;
        }
    }

    // Asking for Input as Choice
    private static int takeInput(int min, int max) {
        String choice;
        Scanner input = new Scanner(System.in);

        while(true)
        {
            System.out.println("Faites un choix : ");

            choice = input.next();

            if(Helpers.isNumber(choice) && Integer.parseInt(choice) >= min && Integer.parseInt(choice) <= max)
            {
                return Integer.parseInt(choice);
            }
            else
            {
                Helpers.displayErrorMsg("Entrée non valide.");

            }

        }

    }

    private static void treatement(int option){

        switch(option) {
            case 1:
                addEmployee();
                break;
            case 2:
                deleteEmployee();
                break;
            case 3:
                findEmployeeByMtrcl();
                break;
            case 4:
                addClient();
                break;
            case 5:
                deleteClient();
                break;
            case 6:
                findClientByCode();
                break;
            case 7:
                addAccount();
                break;
            case 8:
                deleteAccount();
                break;
            case 9:
                findAccountsByClient();
                break;
            case 10:
                addOperation();
                break;
            case 11:
                deleteOperation();
                break;
            case 12:
                findOperationByNbr();
                break;
            case 13:
                addMission();
                break;
            case 14:
                deleteMission();
                break;
            case 15:
                getAllEmployees();
                break;
            case 16:
                findEmployees();
                break;
            case 17:
                getAllClients();
                break;
            case 18:
                findClients();
                break;
            case 19:
                getAllAccounts();
                break;
            case 20:
                changeAccountState();
                break;
            case 21:
                getAllMissions();
                break;
            case 22:
                assign();
                break;
            case 23:
                deleteAssignment();
                break;
            case 24:
                getAllAccountsByState();
                break;
            case 25:
                getAllAccountsByDate();
                break;
            case 26:
                updateEmployee();
                break;
            case 27:
                modifyClient();
                break;
            case 28:
                modifyAccount();
                break;
            case 29:
                findAccountByOprNbr();
                break;
            case 30:
                getAsnmntsHistoryByEmpl();
                break;
            case 31:
                getStats();
                break;
            default:
                break;
        }

    }




    //--------------------------------------------- Employees------------------------------
    public static void addEmployee(){

        System.out.println("----------------------Ajout d'un Employe--------------------------");

        String[] fields = {"Prenom", "Nom", "Date de naissance", "Adresse", "N Tel","Date de recrutement","Adresse Mail"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);


            if(field.equals("Date de naissance") || field.equals("Date de recrutement")){
                attribut.setType("date");
            }

            if(field.equals("N tel")){
                attribut.setType("tel");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Employee employee = new Employee(filledFields.get("Prenom"),filledFields.get("Nom"), Helpers.strToDate(filledFields.get("Date de naissance")),filledFields.get("Adresse"),
                filledFields.get("N Tel"),Helpers.strToDate(filledFields.get("Date de recrutement")),filledFields.get("Adresse Mail"));

        Helpers.displaySuccessMsg("Employé ajouté avec succès");

        displayEmployee(employeeDAO.save(employee));

        System.out.println("--------------------------------------------------------------------");

    }

    public static void deleteEmployee(){

        System.out.println("----------------------Suppression d'un Employe--------------------------");

        String[] fields = {"Matricule"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Matricule")){
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Employee employee = new Employee(Integer.valueOf(filledFields.get("Matricule")));


        if(employeeDAO.delete(employee)){
            Helpers.displaySuccessMsg("Employé supprimé avec succès");
        }else{
            Helpers.displayErrorMsg("Error!!");
        }

        System.out.println("----------------------------------------------------------------------------");

    }

    public static void findEmployeeByMtrcl(){

        System.out.println("----------------------Chercher un Employe--------------------------");

        String[] fields = {"Matricule"};

        List<Attribut> attributs = new ArrayList<>();


        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Matricule")){
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Employee employee = new Employee(Integer.valueOf(filledFields.get("Matricule")));

        if(employeeDAO.findBy(employee).isPresent()){
            displayEmployee(employeeDAO.findBy(employee).get());
        }else{
            Helpers.displayErrorMsg("Error!!");
        }

        System.out.println("--------------------------------------------------------------------");

    }

    public static void getAllEmployees(){

        System.out.println("----------------------Tous les employés--------------------------");

        displayEmployees(EmployeeDAOImpl.findAll());

        System.out.println("-----------------------------------------------------------------");

    }

    public static void findEmployees() {

        System.out.println("----------------------Chercher un Employé--------------------------");

        String[] fields = {"Prenom", "Nom", "Date de naissance", "Adresse", "Numero de Tel", "Date de recrutement", "Adresse mail"};

        showOptions(fields);

        int choice = takeInput(1, fields.length);

        switch (choice) {

            case 1:
                findEmployeesByFirstName();
                break;
            case 2:
                findEmployeesByLastName();
                break;
            case 3:
                findEmployeesByBirthDate();
                break;
            case 4:
                findEmployeesByAddress();
                break;
            case 5:
                findEmployeesByPhoneNbr();
                break;
            case 6:
                findEmployeesByRcrtmntDate();
                break;
            case 7:
                findEmployeesByMailAddress();
                break;
            default:
                break;
        }

        System.out.println("-----------------------------------------------------------------------------");

    }

    public static void findEmployeesByFirstName(){

        System.out.println("----------------------Chercher un Employé par prénom--------------------------");

        String[] fields = {"Prenom"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);


        displayEmployees(EmployeeDAOImpl.findByFirstName(filledFields.get("Prenom")));

        System.out.println("-----------------------------------------------------------------------------");


    }

    public static void findEmployeesByLastName(){

        System.out.println("----------------------Chercher un Employé par nom--------------------------");

        String[] fields = {"Nom"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);


        displayEmployees(EmployeeDAOImpl.findByLastName(filledFields.get("Nom")));

        System.out.println("-----------------------------------------------------------------------------");


    }

    public static void findEmployeesByAddress(){

        System.out.println("----------------------Chercher un Employé par Adresse--------------------------");

        String[] fields = {"Adresse"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);


        displayEmployees(EmployeeDAOImpl.findByAddress(filledFields.get("Adresse")));

        System.out.println("-----------------------------------------------------------------------------");

    }

    public static void findEmployeesByPhoneNbr(){

        System.out.println("----------------------Chercher un Employé par Numéro de Tél--------------------------");

        String[] fields = {"N TEL"};

        List<Attribut> attributs = new ArrayList<>();


        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);


        displayEmployees(EmployeeDAOImpl.findByPhoneNbr(filledFields.get("N TEL")));

        System.out.println("-----------------------------------------------------------------------------");
    }

    public static void findEmployeesByMailAddress(){

        System.out.println("----------------------Chercher un Employé par Adresse Mail--------------------------");

        String[] fields = {"E-MAIL"};

        List<Attribut> attributs = new ArrayList<>();

        //Objects Creation

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("E-MAIL")){
                attribut.setType("mail");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);


        displayEmployees(EmployeeDAOImpl.findByMailAdrs(filledFields.get("E-MAIL")));

        System.out.println("-----------------------------------------------------------------------------");

    }

    public static void findEmployeesByBirthDate(){

        System.out.println("----------------------Chercher un Employé par date de naissance--------------------------");

        String[] fields = {"Date de naissance"};

        List<Attribut> attributs = new ArrayList<>();

        //Objects Creation

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Date de naissance")){
                attribut.setType("date");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        displayEmployees(EmployeeDAOImpl.findByBirthDate(filledFields.get("Date de naissance")));

        System.out.println("-----------------------------------------------------------------------------");

    }

    public static void findEmployeesByRcrtmntDate(){

        System.out.println("----------------------Chercher un Employé par date de recrutement--------------------------");

        String[] fields = {"Date de recrutement"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Date de recrutement")){
                attribut.setType("date");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        displayEmployees(EmployeeDAOImpl.findByRcrtmntDate(filledFields.get("Date de recrutement")));

        System.out.println("-----------------------------------------------------------------------------");

    }

    public static void updateEmployee(){

        System.out.println("----------------------Modification d'un Employe--------------------------");

        String[] fields = {"Matricule","Prenom", "Nom", "Date de naissance", "Adresse", "N Tel","Date de recrutement","Adresse Mail"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Matricule")){
                attribut.setType("number");
            }

            if(field.equals("N Tel")){
                attribut.setType("tel");
            }

            if(field.equals("Adresse Mail")){
                attribut.setType("mail");
            }

            if(field.equals("Date de naissance") || field.equals("Date de recrutement")){
                attribut.setType("date");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Employee employee = new Employee(Integer.valueOf(filledFields.get("Matricule")),filledFields.get("Prenom"),filledFields.get("Nom"), Helpers.strToDate(filledFields.get("Date de naissance")),filledFields.get("Adresse"),
                filledFields.get("N Tel"),Helpers.strToDate(filledFields.get("Date de recrutement")),filledFields.get("Adresse Mail"));

        displayEmployee(employeeDAO.update(employee));

        Helpers.displaySuccessMsg("Employé modifié avec succès");

        System.out.println("--------------------------------------------------------------------");

    }





    //-------------------------------------------------Clients-------------------------------


    public static void addClient(){

        System.out.println("----------------------Ajout d'un Client--------------------------");

        String[] fields = {"Prenom", "Nom", "Date de naissance", "Adresse", "N Tel"};

        List<Attribut> attributs = new ArrayList<>();


        for (String field:fields) {

            Attribut attribut = new Attribut(field);


            if(field.equals("N Tel")){
                attribut.setType("tel");
            }

            if(field.equals("Date de naissance")){
                attribut.setType("date");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Client client = new Client(filledFields.get("Prenom"),filledFields.get("Nom"), Helpers.strToDate(filledFields.get("Date de naissance")),filledFields.get("Adresse"),
                filledFields.get("N Tel"));

        Helpers.displaySuccessMsg("Client ajouté avec succès");
        displayClient(clientDAO.save(client));

        System.out.println("--------------------------------------------------------------------");

    }

    public static void modifyClient(){

        System.out.println("----------------------Modification d'un Client--------------------------");

        String[] fields = {"Code","Prenom", "Nom", "Date de naissance", "Adresse", "N Tel"};

        List<Attribut> attributs = new ArrayList<>();


        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Code")){
                attribut.setType("number");
            }

            if(field.equals("N Tel")){
                attribut.setType("tel");
            }

            if(field.equals("Date de naissance")){
                attribut.setType("date");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Client client = new Client(Integer.valueOf(filledFields.get("Code")),filledFields.get("Prenom"),filledFields.get("Nom"), Helpers.strToDate(filledFields.get("Date de naissance")),filledFields.get("Adresse"),
                filledFields.get("N Tel"));

        displayClient(clientDAO.update(client));
        Helpers.displaySuccessMsg("Client modifié avec succès");

        System.out.println("--------------------------------------------------------------------");

    }

    public static void deleteClient(){

        System.out.println("----------------------Suppression d'un Client--------------------------");

        String[] fields = {"Code"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Code")){
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Client client = new Client(Integer.valueOf(filledFields.get("Code")));

        if(clientDAO.delete(client)){
            Helpers.displaySuccessMsg("Client supprimé avec succès");
        }else{
            Helpers.displayErrorMsg("Error!!");
        }

        System.out.println("--------------------------------------------------------------------");

    }

    public static void findClientByCode(){

        System.out.println("----------------------Chercher un Client--------------------------");

        String[] fields = {"Code"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Code")){
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Client client = new Client(Integer.valueOf(filledFields.get("Code")));


        if(clientDAO.findBy(client).isPresent()){
            displayClient(clientDAO.findBy(client).get());
        }else{
            Helpers.displayErrorMsg("Error!!");
        }

        System.out.println("--------------------------------------------------------------------");

    }

    public static void getAllClients(){

        System.out.println("----------------------Tous les clients--------------------------");

        displayClients(ClientDAOImpl.findAll());

        System.out.println("-----------------------------------------------------------------");

    }

    public static void findClients(){

        System.out.println("----------------------Chercher un Client--------------------------");

        String[] fields = {"Prenom", "Nom", "Date de naissance", "Adresse", "Numero de Tel"};

        showOptions(fields);

        int choice = takeInput(1,fields.length);

        switch (choice){

            case 1:
                findClientsByFirstName();
                break;
            case 2:
                findClientsByLastName();
                break;
            case 3:
                findClientsByBirthDate();
                break;
            case 4:
                findClientsByAddress();
                break;
            case 5:
                findClientsByPhoneNbr();
                break;
            default:
                break;
        }

        System.out.println("--------------------------------------------------------------------");


    }

    public static void findClientsByFirstName(){

        System.out.println("----------------------Chercher un Employé par prénom--------------------------");

        String[] fields = {"Prenom"};

        List<Attribut> attributs = new ArrayList<>();


        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        displayClients(ClientDAOImpl.findByFirstName(filledFields.get("Prenom")));

    }

    public static void findClientsByLastName(){

        System.out.println("----------------------Chercher un Employé par nom--------------------------");

        String[] fields = {"Nom"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);


        displayClients(ClientDAOImpl.findByLastName(filledFields.get("Nom")));

    }

    public static void findClientsByAddress(){

        System.out.println("----------------------Chercher un Employé par Adresse--------------------------");

        String[] fields = {"Adresse"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);


        displayClients(ClientDAOImpl.findByAddress(filledFields.get("Adresse")));

    }

    public static void findClientsByPhoneNbr(){

        System.out.println("----------------------Chercher un Employé par Numéro de Tél--------------------------");

        String[] fields = {"N Tel"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("N Tel")){
                attribut.setType("tel");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);


        displayClients(ClientDAOImpl.findByPhoneNbr(filledFields.get("N Tel")));


    }

    public static void findClientsByBirthDate(){

        System.out.println("----------------------Chercher un Employé par date de naissance--------------------------");

        String[] fields = {"Date de naissance"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Date de naissance")){
                attribut.setType("date");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        displayClients(ClientDAOImpl.findByBirthDate(filledFields.get("Date de naissance")));

    }




    //------------------------------------------------Comptes----------------------------


    public static void addAccount(){

        System.out.println("----------------------Creation d'un Compte--------------------------");

        System.out.println("--> 1 Compte courant");
        System.out.println("--> 2 Compte d'epargne");


        int option = takeInput(1,2);

        if(option==1){
            System.out.println("----------------------Creation d'un Compte courant--------------------------");
        }else{
            System.out.println("----------------------Creation d'un Compte d'epargne--------------------------");
        }

        String[] fields = {"Code", "Matricule"};

        List<Attribut> attributs = new ArrayList<>();


        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Code") || field.equals("Matricule")){
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        if(option==1){

            List<Attribut> attribts = new ArrayList<>();

            String field = "Decouvert";

            Attribut attribt = new Attribut(field);

            attribt.setType("amount");

            attribts.add(attribt);

            HashMap<String,String> filedFields = takeInfos(attribts);

            Currentaccnt currentaccnt = new Currentaccnt(new Employee(Integer.valueOf(filledFields.get("Matricule"))),new Client(Integer.valueOf(filledFields.get("Code"))),Double.parseDouble(filedFields.get("Decouvert")));

            Helpers.displaySuccessMsg("Compte courant créé avec succès");
            displayAccount(currentaccntDAO.save(currentaccnt));

        }else{

            List<Attribut> attribts = new ArrayList<>();

            String field = "Taux d'interet";

            Attribut attribt = new Attribut(field);

            attribt.setType("percentage");

            attribts.add(attribt);

            HashMap<String,String> filedFields = takeInfos(attribts);

            Savingsaccnt savingsaccnt = new Savingsaccnt(new Employee(Integer.valueOf(filledFields.get("Matricule"))),new Client(Integer.valueOf(filledFields.get("Code"))),Double.parseDouble(filedFields.get("Taux d'interet")));

            Helpers.displaySuccessMsg("Compte d'epargne créé avec succès");
            displayAccount(savingsaccntDAO.save(savingsaccnt));

        }

        System.out.println("--------------------------------------------------------------------");

    }

    public static void deleteAccount(){

        System.out.println("----------------------Suppression d'un Compte--------------------------");

        String[] fields = {"Numero de compte"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Numero de compte")){
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Account account = new Account(Integer.valueOf(filledFields.get("Numero de compte")));

        if(accountDAO.delete(account)){
            Helpers.displaySuccessMsg("Compte supprimé avec succès");
        }else{
            Helpers.displayErrorMsg("Error!!");
        }

        System.out.println("--------------------------------------------------------------------");

    }

    public static void findAccountsByClient(){

        System.out.println("----------------------Chercher les comptes par client--------------------------");

        String[] fields = {"Code"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Code")){
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Client client = new Client(Integer.valueOf(filledFields.get("Code")));


        System.out.println("----------------------------------Comptes-----------------------------");


        displayAccounts(accountDAO.findBy(client));


        System.out.println("--------------------------------------------------------------------");

    }

    public static void getAllAccounts(){
        System.out.println("----------------------Tous les comptes--------------------------");

        System.out.println("----------------------Les comptes courants--------------------------");

        displayAccounts(AccountDAOImpl.findAll().get("current"));

        System.out.println("----------------------Les comptes d'epargne--------------------------");

        displayAccounts(AccountDAOImpl.findAll().get("savings"));

        System.out.println("-----------------------------------------------------------------");
    }

    public static void changeAccountState(){

        System.out.println("----------------------Changement de status d'un Compte--------------------------");

        String[] fields = {"Numero de compte","Active or Blocked"};

        List<Attribut> attributs = new ArrayList<>();


        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Numero de compte")){
                attribut.setType("number");
            }
            if(field.equals("Active or Blocked")){
                attribut.setType("state");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Account account = new Account(Integer.valueOf(filledFields.get("Numero de compte")));

        if(AccountDAOImpl.changeState(account,State.valueOf(filledFields.get("Active or Blocked"))))
        {
            Helpers.displaySuccessMsg("L'état de compte a été modifié avec succès");
        }else{
            Helpers.displayErrorMsg("Error!!");
        }

        System.out.println("--------------------------------------------------------------------");


    }

    public static void getAllAccountsByState(){

        System.out.println("---------------------------------Tous les comptes------------------------------");

        Map<String,List<Account>> accounts = AccountDAOImpl.findAllByState();

        System.out.println("----------------------------------Comptes Actifs-------------------------------");

        displayAccounts(accounts.get("Active"));

        System.out.println("----------------------------------Comptes Bloqués------------------------------");

        displayAccounts(accounts.get("Blocked"));

        System.out.println("---------------------------------------------------------------------------------");

    }

    public static void getAllAccountsByDate(){

        System.out.println("---------------------------------Tous les comptes par date------------------------------");

        AccountDAOImpl.findAllByDate().forEach((crnDate, accounts) -> {
            System.out.println("Date: " + Helpers.localDateToStr(crnDate)+"\n");
            accounts.forEach(account -> System.out.println(account));
        });

        System.out.println("---------------------------------------------------------------------------------");

    }

    public static void modifyAccount() {

        System.out.println("----------------------Modification d'un Compte--------------------------");

        changeAccountState();

        System.out.println("------------------------------------------------------------------------");

    }

    public static void findAccountByOprNbr(){

        System.out.println("----------------------Chercher le compte par numéro d'opération--------------------------");

        String[] fields = {"Numero d'operation"};

        List<Attribut> attributs = new ArrayList<>();


        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Numero d'operation")){
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Operation operation = new Operation(Integer.valueOf(filledFields.get("Numero d'operation")));


        System.out.println("----------------------------------Compte-----------------------------");

       if(OperationDAOImpl.findAccountByOprNbr(operation).isPresent()){
           displayAccount(OperationDAOImpl.findAccountByOprNbr(operation).get());
       }else{
           Helpers.displayErrorMsg("Aucune opération trouvée!!");
       }

        System.out.println("--------------------------------------------------------------------");

    }


    //------------------------------------------------Operations------------------------------

    public static void addOperation(){

        System.out.println("----------------------Faire une opération--------------------------");

        String[] fields = {"Matricule","Numero de compte", "Type d'operation","Montant"};

        List<Attribut> attributs = new ArrayList<>();


        for (String field:fields) {

            Attribut attribut = new Attribut(field);


            if(field.equals("Matricule") || field.equals("Numero de compte")){
                attribut.setType("number");
            }

            if(field.equals("Montant")){
                attribut.setType("amount");
            }

            if(field.equals("Type d'operation")){
                attribut.setType("type");
            }


            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Operation operation = new Operation(Type.valueOf(filledFields.get("Type d'operation")),Double.parseDouble(filledFields.get("Montant")),new Account(Integer.valueOf(filledFields.get("Numero de compte"))),new Employee(Integer.valueOf(filledFields.get("Matricule"))));

        if(operation.getType()==Type.Withdrawal){

            if(OperationDAOImpl.isValid(operation,savingsaccntService,currentaccntService)){
                displayOperation(operationDAO.save(operation));
                Helpers.displaySuccessMsg("Opération réussie");
            }else{
                Helpers.displayErrorMsg("Opération non permise!!");
            }

        }else{

            displayOperation(operationDAO.save(operation));
            Helpers.displaySuccessMsg("Opération réussie");

        }

        System.out.println("--------------------------------------------------------------------");

    }

    public static void deleteOperation(){

        System.out.println("----------------------Suppression d'une opération--------------------------");

        String[] fields = {"Numéro d'opération"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Numéro d'opération")){
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Operation operation = new Operation(Integer.valueOf(filledFields.get("Numéro d'opération")));

        if(operationDAO.delete(operation)){
            Helpers.displaySuccessMsg("Opération supprimé avec succès");
        }else{
            Helpers.displayErrorMsg("Error!!");
        }

        System.out.println("--------------------------------------------------------------------");


    }


    public static void findOperationByNbr(){

        System.out.println("----------------------Chercher les opérations par numéro--------------------------");

        String[] fields = {"Numero d'operation"};

        List<Attribut> attributs = new ArrayList<>();


        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Numero d'operation")){
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Operation operation = new Operation(Integer.valueOf(filledFields.get("Numero d'operation")));


        System.out.println("----------------------------------Opérations-----------------------------");


        if(operationDAO.findBy(operation).isPresent()){

            displayOperation(operationDAO.findBy(operation).get());

        }else{

            Helpers.displayErrorMsg("Aucune operation trouvée!!");

        }

        System.out.println("--------------------------------------------------------------------");


    }

    //---------------------------------------------------------------------------------

    public static void addMission(){

        System.out.println("----------------------Ajouter une mission--------------------------");

        String[] fields = {"Nom","Description"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Mission mission = new Mission(filledFields.get("Nom"),filledFields.get("Description"));

        displayMission(missionDAO.save(mission));
        Helpers.displaySuccessMsg("Mission ajoutée avec succès");

        System.out.println("--------------------------------------------------------------------");


    }

    public static void deleteMission(){

        System.out.println("----------------------Supprimer une mission--------------------------");

        String[] fields = {"Code mission"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Code mission")){
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Mission mission = new Mission(Integer.valueOf(filledFields.get("Code mission")));

        if(missionDAO.delete(mission))
        {
            Helpers.displaySuccessMsg("Mission supprimée avec succès");
        }else{
            Helpers.displayErrorMsg("Error");
        }

        System.out.println("--------------------------------------------------------------------");
    }

    public static void getAllMissions(){

        System.out.println("----------------------Toutes les missions--------------------------");

        displayMissions(MissionDAOImpl.findAll());

        System.out.println("-----------------------------------------------------------------");

    }


    public static void assign(){

        System.out.println("----------------------Creation d'une affectation--------------------------");

        String[] fields = {"Code mission", "Matricule"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Code mission") || field.equals("Matricule")){
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Assignment assignment = new Assignment(new Mission(Integer.parseInt(filledFields.get("Code mission"))),new Employee(Integer.parseInt(filledFields.get("Matricule"))));

        displayAssignment(assignmentDAO.save(assignment));
        Helpers.displaySuccessMsg("Affectation ajoutée avec succès");

        System.out.println("--------------------------------------------------------------------");

    }

    public static void deleteAssignment(){

        System.out.println("----------------------Suppression d'une affectation--------------------------");

        String[] fields = {"Id Affectation"};

        List<Attribut> attributs = new ArrayList<>();


        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Id Affectation")){
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Assignment assignment = new Assignment(Integer.valueOf(filledFields.get("Id Affectation")));

        if(assignmentDAO.delete(assignment)){
            Helpers.displaySuccessMsg("Affectation supprimée avec succès");
        }else{
            Helpers.displayErrorMsg("L'Opération a echoué!!");
        }

        System.out.println("--------------------------------------------------------------------");

    }

    public static void getAsnmntsHistoryByEmpl(){

        System.out.println("----------------------Historique affectations Employé--------------------------");

        String[] fields = {"Matricule"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Matricule")){
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Employee employee = new Employee(Integer.valueOf(filledFields.get("Matricule")));

        displayAssignments(assignmentDAO.findBy(employee));

        System.out.println("--------------------------------------------------------------------");


    }

    public static void getStats(){

        System.out.println("----------------------Statistiques--------------------------");

        displayStats(AssignmentDAOImpl.getStats());

        System.out.println("-------------------------------------------------------------");

    }
















    public static HashMap<String,String> takeInfos(List<Attribut> attributs){

        HashMap<String,String> filledFields = new HashMap<String, String>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String input="";

        for (Attribut attribut:attributs) {



            do{
                System.out.println("Entrez "+attribut.getName());

                try {

                    input = reader.readLine();


                }catch (Exception e){

                }

            }while (
                    (attribut.getType().equals("date") && !Helpers.isValidDate(input)) ||
                            (attribut.getType().equals("number") && !Helpers.isNumber(input)) ||
                            (attribut.getType().equals("amount") && !Helpers.isValidAmount(input)) ||
                            (attribut.getType().equals("percentage") && !Helpers.isValidPercentage(input)) ||
                            (attribut.isMandatory() && input.equals("")) ||
                            (attribut.getType().equals("state") && !Helpers.isValidState(input) ) ||
                            (attribut.getType().equals("type") && !Helpers.isValidType(input) ) ||
                            (attribut.getName().equals("Matricule") && !attribut.employeeExists(new Employee(Integer.valueOf(input)))) ||
                            (attribut.getName().equals("Code") && !attribut.clientExists(new Client(Integer.valueOf(input)))) ||
                            (attribut.getName().equals("Numero de compte") && !attribut.accountExists(new Account(Integer.valueOf(input)))) ||
                            (attribut.getName().equals("Code mission") && !attribut.missionExists(new Mission(Integer.valueOf(input)))) ||
                            (attribut.getType().equals("mail") && !Helpers.isValidEmail(input))
            );


            filledFields.put(attribut.getName(),input);

        }

        return filledFields;

    }


    //---------------------------------------------------display--------------------------

    //---------------------------------------------Employee-------------------------------
    public static void displayEmployee(Employee employee){
        System.out.println("-------------------------------------------------------");
        if(employee!=null){
            System.out.println(employee);
        }
        System.out.println("-------------------------------------------------------");
    }

    public static void displayEmployees(List<Employee> employees){

        System.out.println("-------------------------------------------------------");

        if(employees==null || employees.isEmpty()){
            System.out.println("Rien à Afficher");
        }

        for (Employee employee : employees) {
            System.out.println(employee);
        }

        System.out.println("-------------------------------------------------------");

    }

    //---------------------------------------------Client--------------------------------
    public static void displayClient(Client client){
        System.out.println("-------------------------------------------------------");
        if(client!=null){
            System.out.println(client);
        }
        System.out.println("-------------------------------------------------------");
    }


    public static void displayClients(List<Client> clients){

        System.out.println("-------------------------------------------------------");

        if(clients==null || clients.isEmpty()){
            System.out.println("Rien à Afficher");
        }

        for (Client client : clients) {

            System.out.println(client);

        }

        System.out.println("-------------------------------------------------------");

    }

    //------------------------------------------Compte------------------------------------

    public static void displayAccount(Account account){
        System.out.println("-------------------------------------------------------");
        System.out.println(account);
        System.out.println("-------------------------------------------------------");
    }

    public static void displayAccounts(List<Account> accounts){

        if(accounts==null || accounts.isEmpty()){

            System.out.println("Rien à Afficher");

        }else{

            for (Account account : accounts) {
                System.out.println(account);
            }

        }

    }

    //--------------------------------------------Operation-------------------------------

    public static void displayOperation(Operation operation){
        System.out.println("-------------------------------------------------------");
        System.out.println(operation);
        System.out.println("-------------------------------------------------------");
    }

    //----------------------------------------Mission--------------------------------------

    public static void displayMission(Mission mission){
        System.out.println("-------------------------------------------------------");
        System.out.println(mission);
        System.out.println("-------------------------------------------------------");
    }

    public static void displayMissions(List<Mission> missions){
        if(missions==null || missions.isEmpty()){
            System.out.println("Rien à afficher");
        }else{
            for (Mission mission : missions) {

                System.out.println(mission);

            }
        }
    }

    //-----------------------------------------Assignments-------------------------------------

    public static void displayAssignment(Assignment assignment){
        System.out.println("-------------------------------------------------------");
        System.out.println(assignment);
        System.out.println("-------------------------------------------------------");
    }

    public static void displayAssignments(List<Assignment> assignments){

        if(assignments==null || assignments.isEmpty()){
            System.out.println("Rien à afficher");
        }else{
            for (Assignment assignment : assignments) {
                System.out.println(assignment);
            }
        }
    }

    public static void displayStats(HashMap<String,String> stats){
        for (String key : stats.keySet()) {
            String value = stats.get(key);
            System.out.println(key + " : " + value);
        }
    }

}
