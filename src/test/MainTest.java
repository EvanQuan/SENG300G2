package test;

public class MainTest {

	public class Inner {}
	public static void main(String[] args) {
	}
	
	public void method() {
		class Poop {
			
		}
		
		Poop p = new Poop();
	}
	
	Inner i = new Inner() { void anonMethod() {}};
}
