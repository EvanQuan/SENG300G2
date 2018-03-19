package test;

import java.io.File;

public class MainTest {

	public static void main(String[] args) {
		String existsDir = _TestSuite.BASEDIR;
		if ((new File(existsDir).isDirectory())) {
			System.out.println("1 dir");
		}
		if ((new File(existsDir).exists())) {
			System.out.println("1 exists");
		}

		String notDir = "foo.txt";
		if ((new File(notDir).isDirectory())) {
			System.out.println("2 dir");
		}
		if ((new File(notDir).exists())) {
			System.out.println("2 exists");
		}

		String notExistsDir = existsDir.concat("/poophhh/");
		if ((new File(notExistsDir).isDirectory())) {
			System.out.println("3 dir");
		}
		if ((new File(notExistsDir).exists())) {
			System.out.println("3 dir");
		}
	}
}
