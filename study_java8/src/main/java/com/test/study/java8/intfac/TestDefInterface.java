package com.test.study.java8.intfac;

public class TestDefInterface {

	public static void main(String[] args) {
		System.out.println(new InterfaceDef() {}.notRequired());
		
		System.out.println(new InterfaceDefImpl().notRequired());
	}
}
