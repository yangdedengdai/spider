package com.spider.run;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spider.dao.Storeable;
import com.spider.dao.impl.HBaseStoreableImpl;
import com.spider.model.Page;
import com.spider.service.Downloadable;
import com.spider.service.Processable;
import com.spider.service.Repositoryable;
import com.spider.service.ThreadPoolable;
import com.spider.service.impl.DownloadableImpl;
import com.spider.service.impl.FixedThreadPoolImpl;
import com.spider.service.impl.JDProcessableImpl;
import com.spider.service.impl.RedisQueueRepositoryableImpl;
import com.spider.util.Config;
import com.spider.util.SleepUtils;

public class Spider {
	Logger logger = LoggerFactory.getLogger(Spider.class);
	private Downloadable downloadableImpl = new DownloadableImpl(); 
	private Processable processableImpl;
	private Storeable storeableImpl = new HBaseStoreableImpl();
	private Repositoryable repositoryableImpl =new RedisQueueRepositoryableImpl();;
	private ThreadPoolable threadPoolableImpl = new FixedThreadPoolImpl();
	
	/**
	 * 开始
	 */
	public void start(){
		check();
		logger.info("爬虫开始启动...");
		while(true){
		 //队列里面取数据
		 final String url = repositoryableImpl.poll();
		 System.out.println("抓取的url："+url);
		 //如果是最后一条那就不执行，因为获取的是空
		 if(StringUtils.isNotBlank(url)){
			 threadPoolableImpl.execute(
					 new Runnable() {
							public void run() {
						 	//			 String url = "http://item.jd.com/1861098.html";//京东手机单页
							//		 String url = "http://list.jd.com/list.html?cat=9987,653,655";//京东
									 Page page = Spider.this.download(url);//把代码转成字符串
									 Spider.this.process(page);//数据解析其实只需要网页字符串就行了
									 List<String> urlList = page.getUrlList();
									 for(String nextUrl : urlList){
										 if(nextUrl.startsWith("http://list.jd.com/")){
											 repositoryableImpl.addHigh(nextUrl);
										 }else{
											 repositoryableImpl.addLow(nextUrl);
										 }
									 }
									 if(!url.startsWith("http://list.jd.com/")){
										 System.out.println(page.getMap().get("title")+"============"+page.getMap().get("price"));
						//				 spider.store(page);
									 }
									 //防止频繁访问IP被封
									 SleepUtils.sleep(Config.millions_1);
							}
						});
		 }else{
			 logger.info("没有url了，休息一会！");
			 SleepUtils.sleep(Config.millions_5);
		 }
	   }
	}
	private void check(){
		if(processableImpl==null){
			String message = "缺少process解析配置";
			logger.error(message);
			throw new RuntimeException(message);
		}
		logger.info("=====================================================================");
		logger.info("downloadableImpl的默认配置为：{}",downloadableImpl.getClass().getName());
		logger.info("processableImpl的默认配置为：{}",processableImpl.getClass().getName());
		logger.info("processableImpl的默认配置为：{}",storeableImpl.getClass().getName());
		logger.info("repositoryableImpl的默认配置为：{}",repositoryableImpl.getClass().getName());
		logger.info("threadPoolableImpl的默认配置为：{}",threadPoolableImpl.getClass().getName());
		logger.info("=====================================================================");
	}
	/**
	 * 下载
	 */
	public Page download(String url){
		Page page = this.downloadableImpl.download(url);
		return page;
	}
	/**
	 *数据解析 
	 */
	public void process(Page page){
		this.processableImpl.process(page);
	}
	/**
	 * 存储 
	 */
	public void store(Page page){
		this.storeableImpl.store(page);
	}
	
	public Downloadable getDownloadableImpl() {
		return downloadableImpl;
	}
	public void setDownloadableImpl(Downloadable downloadableImpl) {
		this.downloadableImpl = downloadableImpl;
	}
	public Processable getProcessableImpl() {
		return processableImpl;
	}
	public void setProcessableImpl(Processable processableImpl) {
		this.processableImpl = processableImpl;
	}
	public Storeable getStoreableImpl() {
		return storeableImpl;
	}
	public void setStoreableImpl(Storeable storeableImpl) {
		this.storeableImpl = storeableImpl;
	}
	//设置种子
	public void setSeedUrl(String url){
		this.repositoryableImpl.addLow(url);
	}
	public Repositoryable getRepositoryableImpl() {
		return repositoryableImpl;
	}
	public void setRepositoryableImpl(Repositoryable repositoryableImpl) {
		this.repositoryableImpl = repositoryableImpl;
	}
	public static void main(String[] args) {
		 Spider spider = new Spider();
//		 spider.setDownloadableImpl(new DownloadableImpl());
		 spider.setProcessableImpl(new JDProcessableImpl());//京东商城解析
//		 spider.setProcessableImpl(new TBProcessableImpl());//淘宝数码解析
//		 spider.setRepositoryableImpl(new RedisQueueRepositoryableImpl());
//		 spider.setStoreableImpl(new HBaseStoreableImpl());
		 spider.setSeedUrl("http://list.jd.com/list.html?cat=9987,653,655");//京东商城
//		 spider.setSeedUrl("https://list.tmall.com/search_product.htm?spm=a220m.1000858.0.0.UXHOcj&cat=50024400&sort=s&style=g&search_condition=55&from=sn_1_rightnav&active=1&industryCatId=50024400&tmhkmain=0#J_crumbs");
		 spider.start();
	}
	
}
