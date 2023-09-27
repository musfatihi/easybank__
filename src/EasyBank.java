import ma.easybank.UTILS.App;

public class EasyBank {
    public static void main(String[] args) {

        App.start();

        while(true){

            App.showOptions(App.options);

            int option = App.takeInput(1, App.options.length);

            App.treatement(option);

        }

    }
}
