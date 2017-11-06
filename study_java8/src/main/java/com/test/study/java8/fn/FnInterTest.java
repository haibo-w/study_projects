package com.test.study.java8.fn;

/**
 *	函数式接口 测试 
 * @author Haibo-W
 *
 */
public class FnInterTest {

	public static void main(String[] args) {
		
		String str = "abcd";
		
		//注册大写转换函数实现
		FnInter fnInter = (String e) -> {
			return e.toUpperCase();
		}; 
		String upper = fnInter.convert(str);
		System.out.println(upper);
		
		//注册小写转换实现
		fnInter = e -> {
			return e.toLowerCase();
		};
		
		System.out.println(fnInter.convert(upper));
	}
}
