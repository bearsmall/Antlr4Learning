import java.util.HashMap;
import java.util.TreeMap;

public class Demo1{

    public void test1(){
        Thread thread = new Thread(
            ()-> System.out.println("hello");
        );
    }

    public void test2(int a, int b){
        int d = a-b;
        Map<String, String> map = new TreeMap<>();
            map.
                    put("name"    ,
                    "xiongyu") ;
    }
}