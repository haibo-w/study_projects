package com.test.study.java8.fn;

public class Animal {
	
	private String name;

	public Animal() {
		super();
		name = " default name";
	}

	public Animal(String name) {
		super();
		this.name = name;
	}



	public static void run(Animal a) {
		System.out.println(a.name + " is run!");
	}
	
	public void showName() {
		System.out.println("my name is " + name);
	}
	
	public void say(String str) {
		System.out.println(" say :" + str);
	}
}
