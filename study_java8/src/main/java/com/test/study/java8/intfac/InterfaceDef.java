package com.test.study.java8.intfac;

/**
 * 默认方法测试
 * @author Haibo-W
 *
 */
interface InterfaceDef {

	default String notRequired() {
		return "default method notRequired";
	}
}

class InterfaceDefImpl implements InterfaceDef{

	@Override
	public String notRequired() {
		return "impl method invoke";
		//return InterfaceDef.super.notRequired();
	}
}



