package com.test.study.java8.fn;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerTest {

	public static void main(String[] args) {
		//元素执行特定的操作直到所有的元素被处理或者抛出一个异常。
		Consumer<Animal> cn = (p) ->  p.showName();
		cn.accept(new Animal("dog"));
		
		List<Animal> list = Arrays.asList(new Animal("fish"));
		String str= "j";
		list.forEach(e -> e.say(str));
		list.forEach(Animal::run);
		list.forEach(Animal::showName);
		
	}
}
