package ma.easybank.UTILS;

import ma.easybank.DAO.Implmnts.*;
import ma.easybank.DAO.Services.*;
import ma.easybank.DTO.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class App {

    public static String[] options = {
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
            "Affficher la liste des comptes par etat",
            "Afficher la liste des comptes par date",
            "Afficher l'historique affectations d'un employé"
    };

    public static Connection connection;


    public static EmployeeService employeeService;
    public static ClientService clientService;
    public static AccountService accountService;
    public static CurrentaccntService currentaccntService;
    public static SavingsaccntService savingsaccntService;
    public static OperationService operationService;
    public static MissionService missionService;
    //public static AssignmentService assignmentService;


    public static EmployeeDAOImpl employeeDAO;
    public static ClientDAOImpl clientDAO;
    public static CurrentaccntDAOImpl currentaccntDAO;
    public static SavingsaccntDAOImpl savingsaccntDAO;
    public static AccountDAOImpl accountDAO;
    public static OperationDAOImpl operationDAO;
    public static MissionDAOImpl missionDAO;
    public static AssignmentDAOImpl assignmentDAO;


    public static void start(){


        //-----------------Connection to DB-----------

        connection = DBConnection.makeConnection();

        //-----------------Services---------------------

        employeeService = new EmployeeService(connection);

        clientService = new ClientService(connection);

        accountService = new AccountService(connection);

        currentaccntService = new CurrentaccntService(connection);

        savingsaccntService = new SavingsaccntService(connection);

        operationService = new OperationService(connection);

        missionService = new MissionService(connection);

        //assignmentService = new AssignmentService(connection);




        //-------------------DAOs----------------------------

        employeeDAO = new EmployeeDAOImpl(employeeService);

        clientDAO = new ClientDAOImpl(clientService);

        accountDAO = new AccountDAOImpl(accountService);

        currentaccntDAO = new CurrentaccntDAOImpl(currentaccntService);

        savingsaccntDAO = new SavingsaccntDAOImpl(savingsaccntService);

        operationDAO = new OperationDAOImpl(operationService);

        missionDAO = new MissionDAOImpl(missionService);

        //assignmentDAO = new AssignmentDAOImpl(assignmentService);

    }

    public static void showOptions(String[] options){
        int i=1;
        for (String option : options) {
            System.out.println(i+" "+option);
            i++;
        }
    }

    // Asking for Input as Choice
    public static int takeInput(int min, int max) {
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

    public static void treatement(int option){

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
                //getAllEmployees();
                break;
            case 16:
                //findEmployees();
                break;
            case 17:
                //getAllClients();
                break;
            case 18:
                //findClients();
                break;
            case 19:
                getAllAccounts();
                break;
            case 20:
                changeAccountState();
                break;
            case 21:
                //getAllMissions();
                break;
            case 22:
                //assign();
                break;
            case 23:
                //deleteAssignment();
                break;
            case 24:
                //getAllAccountsByState();
                break;
            case 25:
                //getAllAccountsByDate();
                break;
            case 26:
                //getAsnmntsHistoryByEmpl();
                break;
            default:
                break;
        }

    }




    //--------------------------------------------- Employees------------------------------
    public static void addEmployee(){

        System.out.println("----------------------Ajout d'un Employe--------------------------");

        String[] fields = {"Prenom", "Nom", "Date de naissance", "Adresse", "N° Tel","Date de recrutement","Adresse Mail"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Prenom") || field.equals("Nom") || field.equals("Adresse") || field.equals("Adresse Mail")){
                attribut.setMandatory();
            }

            if(field.equals("Date de naissance") || field.equals("Date de recrutement")){
                attribut.setType("date");
                attribut.setMandatory();
            }

            if(field.equals("N° Tel")){
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Employee employee = new Employee(filledFields.get("Prenom"),filledFields.get("Nom"), Helpers.strToDate(filledFields.get("Date de naissance")),filledFields.get("Adresse"),
                filledFields.get("N° Tel"),Helpers.strToDate(filledFields.get("Date de recrutement")),filledFields.get("Adresse Mail"));

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
                attribut.setMandatory();
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Employee employee = new Employee(Integer.valueOf(filledFields.get("Matricule")));


        if(employeeDAO.delete(employee)){
            Helpers.displaySuccessMsg("Employe supprimé avec succès");
        }else{
            Helpers.displayErrorMsg("Aucun Employe avec ce matricule");
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
                attribut.setMandatory();
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Employee employee = new Employee(Integer.valueOf(filledFields.get("Matricule")));

        if(employeeDAO.findBy(employee).isPresent()){
            displayEmployee(employeeDAO.findBy(employee).get());
        }else{
            Helpers.displayErrorMsg("Aucun employé avec ce matricule!!");
        }

        System.out.println("--------------------------------------------------------------------");

    }


    //-------------------------------------------------Clients-------------------------------


    public static void addClient(){

        System.out.println("----------------------Ajout d'un Client--------------------------");

        String[] fields = {"Prenom", "Nom", "Date de naissance", "Adresse", "N° Tel"};

        List<Attribut> attributs = new ArrayList<>();


        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Prenom") || fields.equals("Nom") || fields.equals("Adresse")){
                attribut.setMandatory();
            }

            if(field.equals("N° Tel")){
                attribut.setMandatory();
                attribut.setType("number");
            }

            if(field.equals("Date de naissance")){
                attribut.setType("date");
                attribut.setMandatory();
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Client client = new Client(filledFields.get("Prenom"),filledFields.get("Nom"), Helpers.strToDate(filledFields.get("Date de naissance")),filledFields.get("Adresse"),
                filledFields.get("N° Tel"));

        displayClient(clientDAO.save(client));

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
                attribut.setMandatory();
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Client client = new Client(Integer.valueOf(filledFields.get("Code")));

        if(clientDAO.delete(client)){
            Helpers.displaySuccessMsg("Client supprimé avec succès");
        }else{
            Helpers.displayErrorMsg("Aucun client avec ce code!!");
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
                attribut.setMandatory();
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Client client = new Client(Integer.valueOf(filledFields.get("Code")));


        if(clientDAO.findBy(client).isPresent()){
            displayClient(clientDAO.findBy(client).get());
        }else{
            Helpers.displayErrorMsg("Aucun client avec ce code!!");
        }

        System.out.println("--------------------------------------------------------------------");

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

        String[] fields = {"Client", "Operateur"};

        List<Attribut> attributs = new ArrayList<>();


        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Client") || field.equals("Operateur")){
                attribut.setType("number");
                attribut.setMandatory();
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        if(option==1){

            List<Attribut> attribts = new ArrayList<>();

            String field = "Decouvert";

            Attribut attribt = new Attribut(field);

            attribts.add(attribt);

            HashMap<String,String> filedFields = takeInfos(attribts);

            Currentaccnt currentaccnt = new Currentaccnt(new Employee(Integer.valueOf(filledFields.get("Operateur"))),new Client(Integer.valueOf(filledFields.get("Client"))),Float.parseFloat(filedFields.get("Decouvert")));

            displayAccount(currentaccntDAO.save(currentaccnt));

        }else{

            List<Attribut> attribts = new ArrayList<>();

            String field = "Taux d'interet";

            Attribut attribt = new Attribut(field);

            attribts.add(attribt);

            HashMap<String,String> filedFields = takeInfos(attribts);

            Savingsaccnt savingsaccnt = new Savingsaccnt(new Employee(Integer.valueOf(filledFields.get("Operateur"))),new Client(Integer.valueOf(filledFields.get("Client"))),Float.parseFloat(filedFields.get("Taux d'interet")));

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
                attribut.setMandatory();
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Account account = new Account(Integer.valueOf(filledFields.get("Numero de compte")));

        if(accountDAO.delete(account)){
            Helpers.displaySuccessMsg("Compte supprimé avec succès");
        }else{
            Helpers.displayErrorMsg("Aucun compte trouvé!!");
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
                attribut.setMandatory();
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

        displayAccounts(AccountDAOImpl.findAll(accountService).get("current"));

        System.out.println("----------------------Les comptes d'epargne--------------------------");

        displayAccounts(AccountDAOImpl.findAll(accountService).get("savings"));

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
                attribut.setMandatory();
            }
            if(field.equals("Active or Blocked")){
                attribut.setType("state");
                attribut.setMandatory();
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Account account = new Account(Integer.valueOf(filledFields.get("Numero de compte")));

        if(AccountDAOImpl.changeState(account,State.valueOf(filledFields.get("Active or Blocked")),accountService))
        {
            Helpers.displaySuccessMsg("L'état de compte a été modifié avec succès");
        }else{
            Helpers.displayErrorMsg("Aucun compte avec ce numèro!!");
        }

        System.out.println("--------------------------------------------------------------------");


    }


    //------------------------------------------------Operations------------------------------

    public static void addOperation(){

        System.out.println("----------------------Faire une opération--------------------------");

        String[] fields = {"Operateur","Compte Bancaire", "Type d'operation","Montant"};

        List<Attribut> attributs = new ArrayList<>();


        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Operateur") || field.equals("Compte Bancaire")
                    || field.equals("Type d'operation")){
                attribut.setMandatory();
            }

            if(field.equals("Montant")){
                attribut.setMandatory();
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Operation operation = new Operation(Type.valueOf(filledFields.get("Type d'operation")),Double.parseDouble(filledFields.get("Montant")),new Account(Integer.valueOf(filledFields.get("Compte Bancaire"))),new Employee(Integer.valueOf(filledFields.get("Operateur"))));

        if(operation.getType()==Type.Withdrawal){

            if(OperationDAOImpl.isValid(operation,savingsaccntService,currentaccntService)){
                displayOperation(operationDAO.save(operation));
                Helpers.displaySuccessMsg("Opération réussie");
            }else{
                Helpers.displayErrorMsg("Opération est impossible!!");
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
                attribut.setMandatory();
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Operation operation = new Operation(Integer.valueOf(filledFields.get("Numéro d'opération")));

        if(operationDAO.delete(operation)){
            Helpers.displaySuccessMsg("Opération supprimé avec succès");
        }else{
            Helpers.displayErrorMsg("Aucune Opération trouvée!!");
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
                attribut.setMandatory();
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Operation operation = new Operation(Integer.valueOf(filledFields.get("Numero d'operation")));


        System.out.println("----------------------------------Opérations-----------------------------");


        if(operationDAO.findBy(operation).isPresent()){

            displayOperation(operationDAO.findBy(operation).get());

        }else{

            Helpers.displayErrorMsg("Rien à afficher");

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

            if(field.equals("Nom")){
                attribut.setMandatory();
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Mission mission = new Mission(filledFields.get("Nom"),filledFields.get("Description"));

        displayMission(missionDAO.save(mission));

        System.out.println("--------------------------------------------------------------------");


    }

    public static void deleteMission(){

        System.out.println("----------------------Supprimer une mission--------------------------");

        String[] fields = {"Code"};

        List<Attribut> attributs = new ArrayList<>();

        for (String field:fields) {

            Attribut attribut = new Attribut(field);

            if(field.equals("Code")){
                attribut.setMandatory();
                attribut.setType("number");
            }

            attributs.add(attribut);

        }

        HashMap<String,String> filledFields = takeInfos(attributs);

        Mission mission = new Mission(Integer.valueOf(filledFields.get("Code")));

        if(missionDAO.delete(mission))
        {
            Helpers.displaySuccessMsg("Mission supprimé avec succès");

        }else{

            Helpers.displayErrorMsg("Opération a echoué");

        }

        System.out.println("--------------------------------------------------------------------");
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
                            (attribut.isMandatory() && input.equals("")) ||
                            (attribut.getType().equals("state") && !Helpers.isValidState(input) )
            );


            filledFields.put(attribut.getName(),input);

        }

        return filledFields;

    }


    //---------------------------------------------------display--------------------------

    //---------------------------------------------Employee-------------------------------
    public static void displayEmployee(Employee employee){
        System.out.println("-------------------------------------------------------");
        System.out.println(employee);
        System.out.println("-------------------------------------------------------");
    }

    //---------------------------------------------Client--------------------------------
    public static void displayClient(Client client){
        System.out.println("-------------------------------------------------------");
        System.out.println(client);
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
        System.out.println(operation);
    }

    //----------------------------------------Mission--------------------------------------

    public static void displayMission(Mission mission){
        System.out.println("-------------------------------------------------------");
        System.out.println(mission);
        System.out.println("-------------------------------------------------------");
    }







}
