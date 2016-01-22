package com.spider.service.impl;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.spider.service.Repositoryable;
/**
 * 实现具有优先级的队列
 */
public class QueueRepositoryableImpl implements Repositoryable{
	//队列
	Queue<String> lowQueue = new ConcurrentLinkedDeque<String>();
	Queue<String> highQueue = new ConcurrentLinkedDeque<String>();
	public String poll() {
		String url = highQueue.poll();
		if(url==null){
			url = lowQueue.poll();
		}
		return url;
	}

	public void addLow(String nextUrl) {
		this.lowQueue.add(nextUrl);
	}

	public void addHigh(String nextUrl) {
		this.highQueue.add(nextUrl);
	}

}
