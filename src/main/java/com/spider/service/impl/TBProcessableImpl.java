package com.spider.service.impl;

import org.apache.commons.lang.StringUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.spider.model.Page;
import com.spider.service.Processable;
import com.spider.util.HtmlUtils;

public class TBProcessableImpl implements Processable{

	public void process(Page page) {
		String content = page.getContent();
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode rootNode = htmlCleaner.clean(content);
		try {
			String url = page.getUrl();
			if(url.startsWith("https://list.tmall.com/")){
				//解析下一页
				String nextUrl = HtmlUtils.getAttributeByName(rootNode,"//*[@id=\"J_Filter\"]/p/a" , "href");
				if(StringUtils.isNotBlank(nextUrl)){
					nextUrl = "https://list.tmall.com/search_product.htm"+nextUrl.replace("&amp;","&");
					page.addUrl(nextUrl);
				}
				//解析当前页
				Object[] itemList = rootNode.evaluateXPath( "//*[@id=\"J_ItemList\"]/div/div/div[1]/a");
				for(Object obj :itemList){
					TagNode itemNode = (TagNode) obj;
					String itemHrel = itemNode.getAttributeByName("href");
					if(itemHrel.startsWith("//detail.tmall.com/item.htm")){
						page.addUrl("https:"+itemHrel);
					}
				}
			}else{
				processProduct(page,rootNode);
			}
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 解析商品
	 */
	public void processProduct(Page page,TagNode rootNode){
		//解析标题，用XPATH
		String title = HtmlUtils.getText(rootNode, "//*[@id=\"J_DetailMeta\"]/div[1]/div[1]/div/div[1]/h1");
//		page.addField("title", title);

		//解析图片地址
		String picUrl = HtmlUtils.getAttributeByName(rootNode, "//*[@id=\"J_ImgBooth\"]", "src");
//		page.addField("picUrl","https:"+picUrl);
		
		//解析价格
		String price = HtmlUtils.getText(rootNode, "//*[@id=\"J_PromoPrice\"]/dd/div/span");
		System.out.println(price);
	}
}
