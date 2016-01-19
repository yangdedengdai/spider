package com.spider.util;

public class SleepUtils {
	
	public static void sleep(long million){
		 try {
				Thread.currentThread();
				Thread.sleep(million);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
}
