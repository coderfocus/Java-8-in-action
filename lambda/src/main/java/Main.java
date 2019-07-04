
import stream.DashStream;
import transfercode.Apple;
import transfercode.FilterApples;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args){
//        File[] files = new File(".").listFiles(new FileFilter() {
//            public boolean accept(File file) {
//                return file.isHidden();
//            }
//        });

        File[] files = new File(".").listFiles(File::isHidden);


        List<Apple> apples = new ArrayList();
        apples.add(new Apple("red",120));
        apples.add(new Apple("red",200));
        apples.add(new Apple("green",120));
        apples.add(new Apple("green",200));

        //匿名类（不用创建很多只用实例化一次的类）
        List<Apple> greenApples2 = FilterApples.filterApples(apples, new Predicate<Apple>() {
            @Override
            public boolean test(Apple apple) {
                return "green".equals(apple.getColor());
            }
        });

        //方法引用（相当于C#中将方法绑定到同类型委托）
        List<Apple> greenApples = FilterApples.filterApples(apples,Apple::isGreenApple);
        List<Apple> heavyApples = FilterApples.filterApples(apples,Apple::isHeavyApple);

        //lambda(匿名函数)
        List<Apple> greenApples1 = FilterApples.filterApples(apples,(Apple a) -> "green".equals(a.getColor()));
        List<Apple> heavyApples1 = FilterApples.filterApples(apples, a-> a.getWeight() >150);
        List<Apple> greenAndHeavyApples = FilterApples.filterApples(apples, a-> "green".equals(a.getColor()) && a.getWeight() >150);

        //stream 筛选数据
        List<Apple> streamApples = apples.stream()
                .filter(a->a.getWeight()>150)
                .collect(Collectors.toList());

        apples.sort((a,b)->a.getWeight().compareTo(b.getWeight()));
        //方法引用
        apples.sort(Comparator.comparing(Apple::getWeight));

        //比较器复合
        Comparator<Apple> comparator = (a,b)->a.getWeight().compareTo(b.getWeight());
        Comparator<Apple> reComparator = comparator.reversed();
        Comparator<Apple> thenComparator = reComparator.thenComparing(Apple::getColor);

        apples.sort(Comparator.comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor)) ;


        //谓词符合 and 和 or 从左到右确定优先级
        Predicate<Apple> redApple = apple -> "red".equals(apple.getColor());
        Predicate<Apple> notRedApple = redApple.negate();
        Predicate<Apple> redAndHeavyApple = redApple.and(apple -> apple.getWeight()>150);
        Predicate<Apple> redAndHeavyOrGreenApple = redApple.and(apple -> apple.getWeight()>150)
                .or(apple -> "green".equals(apple.getColor()));
        List<Apple> redAndHeavyOrGreenApples = FilterApples.filterApples(apples,redAndHeavyOrGreenApple);

        //函数复合
        Function<Integer,Integer> f = x -> x+1;
        Function<Integer,Integer> g = x -> x*2;

        Function<Integer,Integer> h = f.andThen(g);
        int result = h.apply(1); //4

        Function<Integer,Integer> h1 = f.compose(g);
        int result1 = h1.apply(1); //3


//        DashStream.operate();
        DashStream.collect();
    }
}
