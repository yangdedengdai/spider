package com.yang.spider;

import org.junit.Test;

import com.spider.dao.impl.HBaseStoreableImpl;
import com.spider.model.Page;
import com.spider.run.Spider;
import com.spider.service.impl.DownloadableImpl;
import com.spider.service.impl.JDProcessableImpl;

public class SpiderTest {
	@Test
	public void test(){
		 Spider spider = new Spider();
		 spider.setDownloadableImpl(new DownloadableImpl());
		 spider.setProcessableImpl(new JDProcessableImpl());
		 spider.setStoreableImpl(new HBaseStoreableImpl());
		 String url = "http://item.jd.com/1861098.html";
		 Page page = spider.download(url);//把代码转成字符串
//		 System.out.println("网页内容: "+page.getContent());
		 spider.process(page);//数据解析其实只需要网页字符串就行了
		 System.out.println(page.getMap().get("title"));
		 System.out.println(page.getMap().get("picUrl"));
		 System.out.println(page.getMap().get("price"));
		 System.out.println(page.getMap().get("params"));
		 spider.store(page);
	}
	
}
