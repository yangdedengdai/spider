package com.spider.run;

import com.spider.dao.Storeable;
import com.spider.model.Page;
import com.spider.service.Downloadable;
import com.spider.service.Processable;

public class Spider {
	private Downloadable downloadableImpl; 
	private Processable processableImpl;
	private Storeable storeableImpl;
	/**
	 * 开始
	 */
	public void start(){
		
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
	
}
