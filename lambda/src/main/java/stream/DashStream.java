package stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DashStream {
    private static List<Dish> menu(){
        List<Dish> menu = Arrays.asList(
                new Dish("pork",false,800, Dish.Type.MEAT),
                new Dish("beef",false,700, Dish.Type.MEAT),
                new Dish("chicken",false,400, Dish.Type.MEAT),
                new Dish("french fries",true,530, Dish.Type.OTHER),
                new Dish("rice",true,350, Dish.Type.OTHER),
                new Dish("season fruit",true,120, Dish.Type.OTHER),
                new Dish("pizza",true,550, Dish.Type.OTHER),
                new Dish("prawns",false,300, Dish.Type.FISH),
                new Dish("salmon",false,450, Dish.Type.FISH)
        );
        return menu;
    }

    public static void operate(){
        List<String> threeHighCaloricDishNames = menu().stream()
                .filter(m->m.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(threeHighCaloricDishNames);

//        menu().stream().forEach(System.out::println);

        //筛选 filter distinct limit skip
        //skip(n) 如果流中元素不足n个，则返回一个空流
        List<Dish> dishes = menu().stream()
                .filter(d->d.getCalories()>700)
                .skip(2)
                .collect(Collectors.toList());
        System.out.println(dishes);

        //映射
        //流的扁平化 flatMap方法让你把一个流中的每一个值都换成另一个流，然后把所有的流连接起来成为一个流
        String[] words = {"hello","world"};
        List<String> uniqueChars = Arrays.stream(words)
                .map(w->w.split(""))
                .flatMap(Arrays::stream)
                //Stream<String[]> => Stream<String>
                .distinct()
                .collect(Collectors.toList());
        System.out.println(uniqueChars);
    }

}
