package com.spider.run;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.commons.lang.StringUtils;

import com.spider.dao.Storeable;
import com.spider.model.Page;
import com.spider.service.Downloadable;
import com.spider.service.Processable;
import com.spider.util.SleepUtils;

public class Spider {
	private Downloadable downloadableImpl; 
	private Processable processableImpl;
	private Storeable storeableImpl;
	//队列
	Queue<String> queue = new ConcurrentLinkedDeque<String>();

	/**
	 * 开始
	 */
	public void start(){
		while(true){
		 //队列里面取数据
		 String url = queue.poll();
		 //如果是最后一条那就不执行，因为获取的是空
		 if(StringUtils.isNotBlank(url)){
	//		 String url = "http://item.jd.com/1861098.html";//京东手机单页
	//		 String url = "http://list.jd.com/list.html?cat=9987,653,655";//京东
			 Page page = this.download(url);//把代码转成字符串
			 this.process(page);//数据解析其实只需要网页字符串就行了
			 List<String> urlList = page.getUrlList();
			 for(String nextUrl : urlList){
				 queue.add(nextUrl);
			 }
			 if(!url.startsWith("http://list.jd.com/")){
				 System.out.println(page.getMap().get("title")+"============"+page.getMap().get("price"));
//				 spider.store(page);
			 }
			 //防止频繁访问IP被封
			 SleepUtils.sleep(5000);
		 }
	   }
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
		this.queue.add(url);
	}
}
