package com.coderfocus.test;

import com.coderfocus.model.Car;
import com.coderfocus.model.House;
import com.coderfocus.model.Insurance;
import com.coderfocus.model.Person;

import java.util.Optional;

public class Test1 {
    public static void main(String[] args) {
        Insurance insurance = new Insurance();
        insurance.setName("coderfocus");
        Optional<Insurance> optInsurance = Optional.ofNullable(insurance);

        Car car = new Car();
        car.setInsurance(optInsurance);
        Optional<Car> optCar = Optional.ofNullable(car);

        House house = new House();
        house.setAddress("JiNan");

        Person person = new Person();
        person.setCar(optCar);
        person.setHouse(house);
        Optional<Person> optPerson = Optional.ofNullable(person);

        System.out.println(getCarInsuranceName(optPerson));


        optPerson.ifPresent(x-> System.out.println(x.getClass()));

        filterName(optInsurance);

        System.out.println(getHouseAddress(optPerson));
    }

    private static String getCarInsuranceName(Optional<Person> person){
        return person.flatMap(p -> p.getCar())
                .flatMap(c->c.getInsurance())
                .map(i->i.getName())
                .orElse("unkown");
    }

    private static String getHouseAddress(Optional<Person> person){
        return person.map(p -> p.getHouse())
                .flatMap(h->h.getAddress())
                .filter(a->"Rizhao".equals(a))
                .orElse("BeiJing");
    }

    private static void filterName(Optional<Insurance> optionalInsurance){
        optionalInsurance.filter(insurance -> "coderfocus".equals(insurance.getName()))
                .ifPresent(x-> System.out.println(x.getName()));
    }
}
