package com.test.study.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FunctionInvokeTest {

	static class TestCalss{
		
		
		
		public TestCalss() {
			System.out.println("construct");
		}

		public static void show(TestCalss tc) {
			System.out.println("showStatic" + tc);
		}
		
		public void showDynamic() {
			System.out.println("showDynamic");
		}
	}
	
	public static void main(String[] args) {
		Supplier<FunctionInvokeTest.TestCalss> sp = FunctionInvokeTest.TestCalss::new;
		System.out.println(sp.get());
		List<FunctionInvokeTest.TestCalss> list = Arrays.asList(sp.get());
		
		list.forEach(TestCalss::showDynamic);
		
		list.forEach(TestCalss::show); // static method must has param 
		
		List<String> strList = Arrays.asList("aa","bb","cc");
		strList.forEach(System.out::print);
		
	}
	
}
