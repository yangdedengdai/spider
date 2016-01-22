package com.spider.service.impl;

import com.spider.service.Repositoryable;
import com.spider.util.RedisUtils;

public class RedisQueueRepositoryableImpl implements Repositoryable{
	RedisUtils redisUtils = new RedisUtils();
	public String poll() {
		String url = redisUtils.poll(RedisUtils.heightkey);
		if(url==null){
			url = redisUtils.poll(RedisUtils.lowkey);
		}
		return url;
	}

	public void addLow(String nextUrl) {
		redisUtils.add(redisUtils.lowkey, nextUrl);
		
	}

	public void addHigh(String nextUrl) {
		redisUtils.add(redisUtils.heightkey, nextUrl);
	}

}
