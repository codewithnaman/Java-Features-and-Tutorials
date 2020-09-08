package java9.feature8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListExample {

    public static void main(String[] args) {
        //java8ListCreation();
        //java8ListFromArray();
        //java9ListCreationWithMoreThan5Values();
        //java9ListWithLessThan4Values();
        java9ListWithNullValue();
    }

    private static void java9ListWithNullValue() {
        List<String> nList = List.of("Test","Test2",null,"Test3");
        System.out.println(nList);
    }

    private static void java9ListWithLessThan4Values() {
        List<String> zeroList = List.of();
        System.out.println(zeroList.getClass());
        List<String> oneList = List.of("Test");
        System.out.println(oneList.getClass());
        List<String> twoList = List.of("Test","Test2");
        System.out.println(twoList.getClass());
        List<String> nList = List.of("Test","Test2","Test3");
        System.out.println(nList.getClass());
    }

    private static void java9ListCreationWithMoreThan5Values() {
        List<String> list = List.of("Test","Test1","Test2","Test3","Test4","Test5");
        System.out.println(list.getClass());
        System.out.println(list);
        //list.set(0,"Changed");  // This operation is not supported got UnsupportedOperationException
        //System.out.println(list);
        //list.add("Test3");      // This operation is not supported got UnsupportedOperationException
        //System.out.println(list);
        //list.remove(0);        // This operation is not supported got UnsupportedOperationException
        //System.out.println(list);
    }

    private static void java8ListFromArray() {
        List<String> list = Arrays.asList("Test","Test2");
        System.out.println(list.getClass());
        System.out.println(list);
        list.set(0,"Changed");
        System.out.println(list);
        //list.add("Test3");   // This operation is not supported got UnsupportedOperationException
        //System.out.println(list);
        //list.remove(0);  // This operation is not supported got UnsupportedOperationException
        //System.out.println(list);
    }

    private static void java8ListCreation() {
        List<String> list = new ArrayList<>();
        list.add("Test");
        list.add("Test2");
        System.out.println(list.getClass());
        System.out.println(list);
        list.set(0,"Changed");
        System.out.println(list);
        list.add("Test3");
        System.out.println(list);
        list.remove(0);
        System.out.println(list);
    }
}
