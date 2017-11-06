package com.test.study.java8.fn;

import java.util.function.Function;

import junit.framework.Assert;

/**
 * 
 * @author Haibo-W
 * @date 2016年9月22日 上午11:54:54
 */
public class FunctionTest {

	public static void main(String[] args) {
		/**
		 * Function 接口有一个参数并且返回一个结果，并附带了一些可以和其他函数组合的默认方法（compose, andThen）
		 */
		Function<Integer, String> f = new Function<Integer, String>() {

			@Override
			public String apply(Integer t) {
				return String.valueOf(t);
			}

		};
		String apply = f.apply(19);
		Assert.assertEquals(apply, "19");

		Function<Integer, String> f2 = (Integer t) -> String.valueOf(t);
		String apply2 = f2.apply(119);
		Assert.assertEquals(apply2, "119");

		Function<Integer, String> f3 = String::valueOf;
		String apply3 = f3.apply(120);
		Assert.assertEquals(apply3, "120");
	}

}
