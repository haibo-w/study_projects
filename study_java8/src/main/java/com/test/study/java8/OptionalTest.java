package com.test.study.java8;

import java.util.Optional;

public class OptionalTest {

	public static void main(String[] args) {
		
		Optional<String> opt = Optional.ofNullable(null);
		
		System.out.println(opt.isPresent());
		System.out.println(opt.orElseGet(()->"[none]"));
		
	}
}
