/*
 * Copyright (C) 2013 Shanghai Paibo soft Co., Ltd
 *
 * All copyrights reserved by Shanghai Paibo.
 * Any copying, transferring or any other usage is prohibited.
 * Or else, Shanghai Paibo possesses the right to require legal 
 * responsibilities from the violator.
 * All third-party contributions are distributed under license by
 * Shanghai Paibo soft Co., Ltd.
 */
package com.test.study.utils.url2pic;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Haibo-W  Jan 27, 2015 4:19:37 PM
 * @since JDK 1.7	
 */
public class URL2Pic {

	/**
	 * @param args
	 * @author Haibo-W Jan 27, 2015 4:19:37 PM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		//System.out.println(new String(Base64.decode("aHR0cDovL3NlYXJjaC5qZC5jb20vU2VhcmNoP2tleXdvcmQ9JUU1JThGJTk4JUU1JUJEJUEyJUU5JTg3JTkxJUU1JTg4JTlBJmVuYz11dGYtOCZzdWdnZXN0PTA=")));
		url2pic(args[0]);
	}
	
	
	public static String url2pic(String url){
		try {
			URL u = new URL(url);
		} catch (MalformedURLException e) {
			System.out.println("非法URL");
			e.printStackTrace();
		}
		String file = System.currentTimeMillis()+".jpg";
		System.out.println(" 开始为["+url+"]截图");
//		byte[] encode = Base64.encode("http://search.jd.com/Search?keyword=%E5%8F%98%E5%BD%A2%E9%87%91%E5%88%9A&enc=utf-8&suggest=0".getBytes());
//		byte[] encode = Base64.encode("http://search.jd.com/Search?keyword=%E5%8F%98%E5%BD%A2%E9%87%91%E5%88%9A&enc=utf-8&suggest=0".getBytes());
//		url = new String(encode);
		try {
			String cmd = "/opt/CutyCapt/capt.sh \'"+url + "\' " + file;
			System.out.println("cmd:" + cmd);
			Process process = Runtime.getRuntime().exec(cmd);
			process.waitFor();
			process.exitValue();
		
			System.out.println(" ["+url+"]截图 完毕 " +file);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" ["+url+"]截图 失败 ");
		}
		
		return file;
	}

}
