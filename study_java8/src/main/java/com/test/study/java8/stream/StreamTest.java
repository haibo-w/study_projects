package com.test.study.java8.stream;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;


public class StreamTest {

	enum Status {
		OPEN, CLOSED
	};

	static class Beauty {
		private Status status = Status.CLOSED;

		private String name;

		private Integer size;

		private String nation;
		
		public Beauty() {
			
		}

		public Beauty(Status status, String name, Integer size, String nation) {
			super();
			this.status = status;
			this.name = name;
			this.size = size;
			this.nation = nation;
		}

		public Status getStatus() {
			return status;
		}

		public String getName() {
			return name;
		}

		public Integer getSize() {
			return size;
		}

		public String getNation() {
			return nation;
		}

		@Override
		public String toString() {
			return String.format("[%s, %d]", status, size);
		}
	}
	
	List<Beauty> beauties;
	@Before
	public void init(){
		
		beauties = Arrays.asList(
				new Beauty(Status.OPEN, "BDYJY", 35, "JAPAN"),
				new Beauty(Status.CLOSED, "CLS", 37, "JAPAN"),
				new Beauty(Status.OPEN, "JINGTIAN", 32, "CHINA"),
				new Beauty(Status.OPEN, "XZMLY", 34, "JAPAN"),
				new Beauty(Status.OPEN, "JACK", 34, "JAPAN"),
				new Beauty(Status.OPEN, "BINGBING", 33, "CHINA"),
				new Beauty(Status.OPEN, "XIXI", 36, "CHINA")
		);
		
//		List<Integer> collect = new Random().ints(30, 37).limit(500000).boxed().collect(Collectors.toList());
//		List<Integer> collect = new Random().ints(30, 37).limit(500000).boxed().
//		new Random().ints(30, 37).limit(500000).forEach(size -> {
//			new Beauty(Status.OPEN, "BDYJY", size, "JAPAN");
//		});
		//Stream.generate();
			
		//System.out.println(collect.size());
	}
	
	@Test
	public void testStram(){
		int asInt = beauties.stream().
				filter(b -> b.getStatus() == Status.OPEN).
				mapToInt(Beauty::getSize).
				max().
				getAsInt();
		System.out.println(" open max size:"+asInt);
	}
	
	@Test
	public void testCount() {
		System.out.println("stream count:"+beauties.stream().count());
		
		beauties.stream().sorted((s1,s2)->{
			if(s1.size > s2.size) {
				return 1;
			}else if(s1.size < s2.size) {
				return -1;
			}else {
				return 0;
			}
		}).forEach(s -> System.out.println(s.name + " " +s.size));
		
		
		// map 使用
		System.out.print("\nMap distince collect collectors ");
		beauties.stream().map(s -> s.size).distinct().collect(Collectors.toList()).forEach( s -> System.out.print(s+"\t"));
		
	}
	
	@Test
	public void testReduce() {
		List<Integer> ints = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		System.out.println("ints sum is:" + ints.stream().reduce(0, (sum, item) -> sum + item));
		
		System.out.println("ints sum is:" + ints.stream().reduce((sum, item) -> sum + item).get());
		
		beauties.stream().collect(Collectors.groupingBy(Beauty::getNation,Collectors.toList())).forEach((e , gplist) ->{
			double d = gplist.stream().mapToInt(Beauty::getSize).average().getAsDouble();
			System.out.println(e + " count:" + gplist.size() + " avg:" + d);
		});
		
	}

	@Test
	public void testIntStream() {
		int[] intArr = new int[] { 5, 10, 15, 20, 26, 35, 14, 28, 55, 44, 42, 31, 13, 69, 56 };

		OptionalInt max = IntStream.of(intArr).max();
		max.ifPresent(v -> System.out.println(v));

		// List<int[]> asList = Arrays.asList(intArr); 
		Integer[] intArr2 = new Integer[] { 5, 10, 15, 20, 26, 35, 14, 28, 55, 44, 42, 31, 13, 69, 56 };
		List<Integer> asList = Arrays.asList(intArr2);
		asList.stream().sorted().forEach(v -> {
			System.out.print(v + "\t");
		});

	}

}
