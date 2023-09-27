package ma.easybank.UTILS;

public class Attribut {
    private String name;
    private Boolean isMandatory=false;
    private String type="string";


    public Attribut(String name){

        this.name = name;

    }

    public String getName() {
        return name;
    }

    public Boolean isMandatory() {
        return isMandatory;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMandatory() {
        this.isMandatory = true;
    }

    public void setType(String type) {
        this.type = type;
    }
}
