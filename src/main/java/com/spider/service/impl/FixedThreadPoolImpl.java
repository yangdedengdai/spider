package com.spider.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.spider.service.ThreadPoolable;
import com.spider.util.Config;

public class FixedThreadPoolImpl implements ThreadPoolable{
	ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(Config.nThread);
	public void execute(Runnable runnable) {
		newFixedThreadPool.execute(runnable);		
	}
}
