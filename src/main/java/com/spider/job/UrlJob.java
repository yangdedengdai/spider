package com.spider.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.spider.util.RedisUtils;

public class UrlJob implements Job{
	RedisUtils redisUtils = new RedisUtils();
	public void execute(JobExecutionContext context) throws JobExecutionException {
		List<String> urlList = redisUtils.lrange(RedisUtils.start_url, 0, -1);
		//把入口url插入repository
		for (String url : urlList) {
			redisUtils.add(RedisUtils.heightkey, url);
		}
	}
}
