package ma.easybank.UTILS;

import ma.easybank.DTO.State;
import ma.easybank.DTO.Type;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Helpers {

    //Check if Input is A Number
    public static Boolean isNumber(String str){

        try {

            for (int i = 0; i < str.length();i++) {

                Integer.parseInt(str.substring(i,i+1));

            }

        }
        catch(NumberFormatException e){

            displayErrorMsg("Not A Number!!");

            return false;

        }

        return true;

    }

    //Display Success Message
    public static void displaySuccessMsg(String msg){
        System.out.print("\u001B[32m");
        System.out.println(msg);
        System.out.print("\u001B[0m");
    }

    //Display Error Message
    public static void displayErrorMsg(String msg){
        System.out.print("\u001B[31m");
        System.out.println(msg);
        System.out.print("\u001B[0m");
    }

    public static Boolean isValidDate(String date){

        // Define a DateTimeFormatter for the specified format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {

            // Parse the string into a LocalDate object using the formatter
            LocalDate.parse(date, formatter);

        } catch (Exception e) {

            return false;
        }

        return true;

    }

    public static LocalDate strToDate(String date){


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


        return LocalDate.parse(date, formatter);


    }

    public static Boolean isValidState(String valueToCheck){

        for (State enumValue : State.values()) {

            if (enumValue.name().equals(valueToCheck)) {
                return true;

            }

        }

        return false;

    }

    public static Boolean isValidType(String valueToCheck){

        for (Type enumValue : Type.values()) {

            if (enumValue.name().equals(valueToCheck)) {
                return true;

            }

        }

        return false;

    }

    public static Boolean isValidAmount(String amount){

        double amnt;

        try {
            amnt = Double.parseDouble(amount);
        }
        catch (Exception e) {
            return false;
        }

        if(amnt<=0){
            return false;
        }

        return true;

    }

    public static Boolean isValidPercentage(String percentage){

        double percent;

        try {
            percent = Double.parseDouble(percentage);
        }
        catch (Exception e) {
            return false;
        }

        if(percent<0 || percent>100){
            return false;
        }

        return true;

    }


    public static String localDateToStr(LocalDate localDate){

        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
        return localDate.format(formatters);

    }


    public static Boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}

