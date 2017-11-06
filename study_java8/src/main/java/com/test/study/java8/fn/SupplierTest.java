package com.test.study.java8.fn;

import java.util.function.Supplier;

public class SupplierTest {

	public static void main(String[] args) {
		//Supplier<T>  供应者Supplier 接口返回一个任意范型的值，和Function接口不同的是该接口没有任何参数
		
		
		Supplier<String> sp = String::new;
		System.out.println(sp.get());
		
		Supplier<Animal> sp2 = Animal::new;
		System.out.println();
		sp2.get().showName();
		
	}
}
