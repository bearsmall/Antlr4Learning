public class Demo1{

	public static void test1(){
		int a = 1;//未使用的局部变量
		int b = 1;
		System.out.println(b);
	}

	/**
	 * 未使用的方法
	 */
	private void test3(){
		return;
	}

	public static void main(String[] args){
		test1();
	}

	public static void show(){

	}
}