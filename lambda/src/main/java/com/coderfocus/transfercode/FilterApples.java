package com.coderfocus.transfercode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilterApples {
    //before Java 8
    public static List<Apple> filterGreenApples(List<Apple> apples){
        List<Apple> result = new ArrayList();
        for (Apple apple: apples) {
            if("green".equals(apple.getColor())){
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterHeavyApples(List<Apple> apples){
        List<Apple> result = new ArrayList();
        for (Apple apple: apples) {
            if(apple.getWeight() > 150){
                result.add(apple);
            }
        }
        return result;
    }

    //after Java 8
    public static List<Apple> filterApples(List<Apple> apples, Predicate<Apple> p){
        List<Apple> result = new ArrayList<>();
        for (Apple apple: apples) {
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }

}
