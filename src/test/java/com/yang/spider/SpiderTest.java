package com.yang.spider;

import org.junit.Test;

import com.spider.dao.impl.HBaseStoreableImpl;
import com.spider.model.Page;
import com.spider.run.Spider;
import com.spider.service.impl.DownloadableImpl;
import com.spider.service.impl.JDProcessableImpl;
import com.spider.service.impl.TBProcessableImpl;

public class SpiderTest {
	@Test
	public void test(){
		 Spider spider = new Spider();
		 spider.setDownloadableImpl(new DownloadableImpl());
		 spider.setProcessableImpl(new JDProcessableImpl());//京东商城解析
//		 spider.setProcessableImpl(new TBProcessableImpl());//淘宝数码解析
		 spider.setStoreableImpl(new HBaseStoreableImpl());
		 spider.setSeedUrl("http://list.jd.com/list.html?cat=9987,653,655");//京东商城
//		 spider.setSeedUrl("https://list.tmall.com/search_product.htm?spm=a220m.1000858.0.0.UXHOcj&cat=50024400&sort=s&style=g&search_condition=55&from=sn_1_rightnav&active=1&industryCatId=50024400&tmhkmain=0#J_crumbs");
		 spider.start();
	}
	
}
