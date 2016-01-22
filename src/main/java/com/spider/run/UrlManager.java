package com.spider.run;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.spider.job.UrlJob;

/**
 * 实现url调度
 * 需要定时向repository仓库插入入口url，每天插入一次，保证数据每天都能抓取一次
 * 这样的话就需要实现一个定时任务，每天凌晨执行一次
 * 执行的时候从一个存储入口url的列表中取出所有入口url，再把这些url插入到repository仓库中即可
 */
public class UrlManager {
	private final static String JOB_GROUP_NAME = "QUARTZ_JOBGROUP_NAME";//任务组
	private final static String TRIGGER_GROUP_NAME = "QUARTZ_TRIGGERGROUP_NAME";//触发器组
	public static void main(String[] args) {
		//创建一个SchedulerFactory工厂实例
		SchedulerFactory sf = new StdSchedulerFactory();
		
		//通过SchedulerFactory构建Scheduler对象
		try {
			Scheduler sche = sf.getScheduler();
			//用于描叙Job实现类及其他的一些静态信息，构建一个作业实例
			JobDetail jobDetail = JobBuilder.newJob(UrlJob.class)
			.withIdentity(UrlJob.class.getSimpleName(), JOB_GROUP_NAME)
			.build();
			 
			//构建一个触发器，规定触发的规则
			Trigger trigger = TriggerBuilder.newTrigger()//创建一个新的TriggerBuilder来规范一个触发器
			.withIdentity(UrlJob.class.getSimpleName(), TRIGGER_GROUP_NAME)//给触发器起一个名字和组名
			.startNow()//立即执行
			.withSchedule(
			CronScheduleBuilder.cronSchedule( "0 0 1 * * ?")
//			SimpleScheduleBuilder.simpleSchedule()
//			.withIntervalInSeconds(seconds)//时间间隔  单位：秒
//			.repeatForever()//一直执行
			)
			.build();//产生触发器
			
			//向Scheduler中添加job任务和trigger触发器
			sche.scheduleJob(jobDetail, trigger);
			 
			//启动
			sche.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
