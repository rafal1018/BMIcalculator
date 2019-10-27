package akademiakodu.BMIcalculator.Model;

public enum Info {
    UNDERWEIGHT("You are in the underweight rage"),
    HEALTHY("You are in the healthy weight rage"),
    OVERWEIGHT("You are in the overweight rage"),
    OBESE("You are in the obese rage");

    private final String name;

    Info(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
