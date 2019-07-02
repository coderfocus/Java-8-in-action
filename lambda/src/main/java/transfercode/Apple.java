package transfercode;

public class Apple {

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private String color;

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    private Integer weight;

    public Apple(String color,int weight){
        this.color = color;
        this.weight =weight;
    }



    //after Java 8
    public boolean isGreenApple(){
        return "green".equals(this.color);
    }

    public boolean isHeavyApple(){
        return this.weight > 150;
    }
}
