package com.coderfocus.stream;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

public class DishStream {
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
    public enum CaloricLevel{DIEF,NORMAL,FAT}

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

        //匹配 anyMatch allMatch noneMatch
        //至少匹配一个元素
        boolean isVegetarian =  menu().stream().anyMatch(Dish::isVegetarian);
        //匹配所有元素
        boolean isHealthy =  menu().stream().allMatch(d->d.getCalories() < 1000);
        //没有任何匹配元素
        isHealthy = menu().stream().noneMatch(d->d.getCalories() >= 1000);

        //查找 findAny findFirst
        menu().stream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(d -> System.out.println(d.getName()));

        //归约
        //求和
        //有默认值
        List<Integer> nums = Arrays.asList(1,2,3,4,5);
        int sum = nums.stream().reduce(0,(a,b)->a+b);//15
        //无默认值
        List<Integer> noneNums = Arrays.asList();
        Optional<Integer> noneDefaultSum = noneNums.stream().reduce((a,b)->a+b);
        boolean isPresent = noneDefaultSum.isPresent();//false

        //最大值 最小值
        int max = nums.stream().reduce(0,Integer::max);
        Optional<Integer> min = nums.stream().reduce(Integer::min);
        max = nums.stream().reduce(0,(x,y)->x>y?x:y);
        min = nums.stream().reduce((x,y)->x<y?x:y);

        Optional<Dish> maxDish = menu().stream().max(Comparator.comparing(Dish::getCalories));
        Optional<Dish> minDish = menu().stream().min(Comparator.comparing(Dish::getCalories));


        //使用collect(Collectors.toSet())替代distinct()去除重复值
        List<String> names = Arrays.asList("song","wen","jie","song","wen","jie");
        Set<String> noneRepeatNames = names.stream()
                .collect(Collectors.toSet());
        System.out.println(noneRepeatNames);

        //使用collect(Collectors.joining())替代reduce("",(n1,n2)->n1+n2)拼接字符串
        String nameStr = names.stream()
                .collect(Collectors.joining());
        System.out.println(nameStr);

        //数值流
        sum = menu().stream()
                .mapToInt(Dish::getCalories)
                .sum();
        //装换为对象流
        Stream<Integer> stream = menu().stream()
                .mapToInt(Dish::getCalories)
                .boxed();
        //最大值默认值 区分最大值是0的流还是没有元素的流
        OptionalInt maxCalories = menu().stream()
                .mapToInt(Dish::getCalories)
                .max();
        max = maxCalories.orElse(1);

        //最小值默认值 区分最小值是0的流还是没有元素的流
        OptionalInt minCalories = menu().stream()
                .mapToInt(Dish::getCalories)
                .min();
        int min1 = minCalories.orElse(1);

        //数值范围
        long count = IntStream.range(1,100)
                .filter(n -> n % 2 == 0)
                .count();

        count = IntStream.rangeClosed(1,100)
                .filter(n -> n % 2 == 0)
                .count();

        //函数生成流：创建无限流
        //1.迭代
        Stream.iterate(0,n->n+2)
                .limit(10)
                .forEach(System.out::println);
        //生成斐波那契数
        Stream.iterate(new int[]{0,1},t->new int[]{t[1],t[0]+t[1]})
                .limit(10)
                .map(t->t[0])
                .forEach(System.out::println);
        //2.生成
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
    }

    public static void collect(){
        //计数
        long howManyDishes = menu().stream().count();
        howManyDishes = menu().stream().collect(counting());

        //最大值
        Optional<Dish> maxCaloriesDish = menu().stream()
                .collect(maxBy(Comparator.comparing(Dish::getCalories)));
        //最小值
        Optional<Dish> minCaloriesDish = menu().stream()
                .collect(minBy(Comparator.comparing(Dish::getCalories)));

        //汇总求和
        int totalCalories = menu().stream()
                .collect(summingInt(Dish::getCalories));

        totalCalories = menu().stream()
                .map(d -> d.getCalories())
                .reduce(0,(a,b)->a+b);
        //平均数
        double avgCalories = menu().stream()
                .collect(averagingInt(Dish::getCalories));

        //一次操作
        IntSummaryStatistics calories = menu().stream()
                .collect(summarizingInt(Dish::getCalories));
        System.out.println(calories);

        //连接字符串
        String menuName = menu().stream()
                .map(Dish::getName)
                .collect(joining());
        //连接字符串（可读性更好）
        menuName = menu().stream()
                .map(Dish::getName)
                .collect(joining(","));

        //分组
        Map<Dish.Type,List<Dish>> dishesByType = menu().stream()
                .collect(groupingBy(Dish::getType));
        System.out.println(dishesByType);

        Map<CaloricLevel,List<Dish>> dishesByLevel = menu().stream()
                .collect(groupingBy(
                        dish ->{
                            if(dish.getCalories() <= 400){
                                return CaloricLevel.DIEF;
                            }
                            if(dish.getCalories() <= 700){
                                return CaloricLevel.NORMAL;
                            }
                            return CaloricLevel.FAT;
                        }
                ));
        System.out.println(dishesByLevel);

        Map<Dish.Type,Map<CaloricLevel,List<Dish>>> dishesByTypeAndLevel = menu().stream()
                .collect(groupingBy(Dish::getType,groupingBy(dish ->{
                    if(dish.getCalories() <= 400){
                        return CaloricLevel.DIEF;
                    }
                    if(dish.getCalories() <= 700){
                        return CaloricLevel.NORMAL;
                    }
                    return CaloricLevel.FAT;
                })));
        System.out.println(dishesByTypeAndLevel);

        //分组计数
        Map<Dish.Type,Long> dishesByTypeCount = menu().stream()
                .collect(groupingBy(Dish::getType,counting()));
        System.out.println(dishesByTypeCount);

        Map<Dish.Type,Map<CaloricLevel,Long>> dishesByTypeAndLevelCount = menu().stream()
                .collect(groupingBy(Dish::getType,groupingBy(dish ->{
                    if(dish.getCalories() <= 400){
                        return CaloricLevel.DIEF;
                    }
                    if(dish.getCalories() <= 700){
                        return CaloricLevel.NORMAL;
                    }
                    return CaloricLevel.FAT;
                },counting())));
        System.out.println(dishesByTypeAndLevelCount);

        //分组最大值
        Map<Dish.Type,Optional<Dish>> mostDishesByType = menu().stream()
                .collect(groupingBy(Dish::getType,maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println(mostDishesByType);

        Map<Dish.Type,Dish> dishNamesByType = menu().stream()
                .collect(groupingBy(Dish::getType,collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)),Optional::get)));
        System.out.println(dishNamesByType);

        //分组类型装换
        Map<Dish.Type,List<String>> dishNamesByType1 = menu().stream()
                .collect(groupingBy(Dish::getType,mapping(Dish::getName,toList())));
        System.out.println(dishNamesByType1);
        //分区
        Map<Boolean,List<String>> vegetarianDishes = menu().stream()
                .collect(partitioningBy(Dish::isVegetarian,mapping(Dish::getName,toList())));
        System.out.println(vegetarianDishes);

    }

    public static void parallel(){
        //并行流默认线程数量，默认为处理器数量
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println(processors);//8

    }

    int a = 2;
    public void refactor(){
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                int a = 10;
                System.out.println(a);//10
            }
        };
        r1.run();
        //lambda不会生成新的作用域
        //lambda不能屏蔽包含类的变量
//        Runnable r2 = () ->{
//            int a = 10;
//            System.out.println(a);
//        };
        //lambda this代表包含类
        Runnable r3 = () ->{
            System.out.println(this.a);//2
        };
        r3.run();
        //匿名类 this类本身
        Runnable r4 = new Runnable() {
            int a = 10;
            @Override
            public void run() {
                System.out.println(a);//10
            }
        };
        r4.run();
    }

}
