package com.test.study.java8.fn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Haibo-W
 * @date 2016年9月22日 上午11:55:29	
 */
public class PredicateTest {

	public static void main(String[] args) {
		
		//Predicate  断言；断定 接口只有一个参数，返回boolean类型。该接口包含多种默认方法来将Predicate组合成其他复杂的逻辑（比如：与，或，非）：

		//用于做一些判断 
		Predicate<String> predicate = (s) -> s.length() > 0;    //长度大于0
		System.out.println(predicate.test("foo"));              // true  
		System.out.println(predicate.negate().test("foo"));     // false   
		
		Predicate<String> isEmpty = String::isEmpty;  
		Predicate<String> isNotEmpty = isEmpty.negate();   
		System.out.println(isEmpty.test("hello"));
		System.out.println(isNotEmpty.test("hello"));
		
		
//		PredicateTest test = new PredicateTest();
//		List<People> yanglist = new ArrayList<>();
//		yanglist.add(new People(18));
//		yanglist.add(new People(20));
//		yanglist.add(new People(25));
//		List<People> list = test.getYangList((p)->p.getAge()>30, yanglist);
//		System.out.println(list.size());
	}
	
	
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
}

