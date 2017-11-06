package com.test.study.java8.lambda;

import java.util.Arrays;

import org.junit.Test;

public class TestLambda {

	@Test
	public void each() {
		
		
		System.out.print("next Test:");
		Arrays.asList("a","b","c","d").forEach( (String e) -> System.out.print(e));
		System.out.println();
		
		
		System.out.print("next Test:");
		Arrays.asList("a","b","c","d").forEach(e -> System.out.print(e));
		System.out.println();
		
		System.out.print("next Test:");
		Arrays.asList("a","b","c","d").forEach(e -> {
			System.out.print(e);
		});
		System.out.println();
		
		System.out.print("next Test:");
		Arrays.asList("a","b","c","d").forEach(e -> {
			if(e.equals("a")) {
				return ;
			}
			System.out.print(e);
		});
		System.out.println();
		
		
	}
}
