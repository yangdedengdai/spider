package com.spider.service.impl;

import java.util.HashMap;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.print.DocFlavor.STRING;

import com.spider.service.Repositoryable;
import com.spider.util.DomainUtils;
import com.spider.util.RedisUtils;



/**
 * 首先把多个电商网站的数据存储到一个map结构里面
 * map结构：<网站顶级域名，redis中list列表的key>
 * 这样每一个网站都有一个队列。
 * 那么我们只需要保证随机的从map中取一个key即可。
 * 
 * 如果随机取一个可以？
 * 通过map的keyset可以获取一个可以的set集合，还可以转化成一个数组
 * 在通过random中的nextint方法获取一个随机数，需要给nextint传一个参数，就是map中key的个数，
 * 	例如：key的个数为3，那么返回的随机数只能是0 、 1、 2
 * 这样通过前面转化的key的数组，就可以把生成的随机数当成数组的角标，获取一个key，这个key就是随机获取的key
 * 下面再根据这个key找到对应的queue队列，取一个url即可
 * 
 * 
 * @author Administrator
 *
 */
public class RandomRedisRepository implements Repositoryable {
	HashMap<String, String> hashMap = new HashMap<String, String>();
	Random random = new Random();
	RedisUtils redisUtils = new RedisUtils();
	
	public String poll() {
		String[] keysarray = hashMap.keySet().toArray(new String[0]);
		int nextInt = random.nextInt(keysarray.length);
		String redis_key = hashMap.get(keysarray[nextInt]);
		return redisUtils.poll(redis_key);
	}

	public void addLow(String nextUrl) {
		String topDomain = DomainUtils.getTopDomain(nextUrl);
		hashMap.put(topDomain, topDomain);
		redisUtils.add(topDomain, nextUrl);
	}

	public void addHigh(String nextUrl) {
		addLow(nextUrl);
	}

}
