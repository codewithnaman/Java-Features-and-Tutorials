package java9.feature8;

import java.util.Map;

public class MapExample {

    public static void main(String[] args) {
        //mapExample();
        mapDifferentTypeOfKeyAndValue();
    }

    private static void mapDifferentTypeOfKeyAndValue() {
        System.out.println(Map.of(1,"a","b",2));
        System.out.println(Map.<String,Integer>of("a",1,"b",2));
    }


    private static void mapExample() {
        Map<String,Integer> oneValueMap = Map.of("a",1);
        System.out.println(oneValueMap.getClass());
        System.out.println(oneValueMap);
        Map<String,Integer> map = Map.of("a",1,"b",2);
        System.out.println(map.getClass());
        System.out.println(map);
    }
}
