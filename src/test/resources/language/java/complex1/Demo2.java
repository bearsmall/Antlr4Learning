public class Demo1{

    public void test1(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        });
    }

    public void test2(int b, int c, int a){
        int d = a-b;
        Map map = new HashMap();
        map.put("name","xiongyu");
    }
}