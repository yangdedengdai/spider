package com.spider.service;

public interface Repositoryable {
	
	public String poll();
	public void addLow(String nextUrl);
	//高优先级队列添加
	public void addHigh(String nextUrl);

}
