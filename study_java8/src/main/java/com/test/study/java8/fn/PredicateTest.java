package com.test.study.java8.fn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Haibo-W
 * @date 2016年9月22日 上午11:55:29	
 */
public class PredicateTest {

	static class People{
		
		public People(int age) {
			super();
			this.age = age;
		}

		private int age;

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
		
	}
	
	public List<People> getYangList(Predicate<People> p,List<People> yanglist){
		List<People> list = new ArrayList<>();
		yanglist.forEach((People po)->{
			if(p.test(po)){
				list.add(po);
			}
		});
		return list;
	}
	
	public static void main(String[] args) {
		
		PredicateTest test = new PredicateTest();
		List<People> yanglist = new ArrayList<>();
		yanglist.add(new People(18));
		yanglist.add(new People(20));
		yanglist.add(new People(25));
		List<People> list = test.getYangList((p)->p.getAge()>30, yanglist);
		
		System.out.println(list.size());
	}
}

