package akademiakodu.BMIcalculator.Model;

public enum Info {
    UNDERWEIGHT("You are in the underweigh rage"),
    HEALTHY("You are in the healthy weight rage"),
    OVERWEIGHT("You are in the overweight rage"),
    OBESE("You are in the obese rage");

    private final String Info;

    Info(String info){
        this.Info = info;
    }

    public String getInfo(){
        return Info;
    }
}
