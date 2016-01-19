package com.spider.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.spider.model.Page;
import com.spider.service.Processable;
import com.spider.util.HtmlUtils;
import com.spider.util.PageUtils;

public class JDProcessableImpl implements Processable{

	public void process(Page page) {
		String  content = page.getContent();
		HtmlCleaner htmlClear = new HtmlCleaner();
		TagNode rootNode = htmlClear.clean(content);
		try {
		String url = page.getUrl();
			if(url.startsWith("http://list.jd.com/")){
				//解析下一页
				String nextUrl = HtmlUtils.getAttributeByName(rootNode, "//*[@id=\"J_topPage\"]/a[2]", "href");
				if(!nextUrl.equals("javascript:;")){//若不是最后一页才往下执行
					nextUrl = "http://list.jd.com"+nextUrl.replace("&amp;","&");
					page.addUrl(nextUrl);
				}
				//解析当前页
				Object[] evaluateXPath = rootNode.evaluateXPath("//*[@id=\"plist\"]/ul/li/div/div[1]/a");
				for(Object obj : evaluateXPath){
					TagNode tagNode = (TagNode) obj;
					page.addUrl(tagNode.getAttributeByName("href"));
				}
			}else{
				processProduct(page,rootNode);
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 解析商品
	 */
	public void processProduct(Page page,TagNode rootNode){
		try {
			//解析标题，用XPATH
			String title = HtmlUtils.getText(rootNode, "//*[@id=\"name\"]/h1");
			page.addField("title", title);
			
			//解析图片地址
			String picUrl = HtmlUtils.getAttributeByName(rootNode,"//*[@id=\"spec-n1\"]/img","src");
			page.addField("picUrl",picUrl);

			//解析价格
			String url = page.getUrl();
			//根据Id进行匹配
			Pattern compile = Pattern.compile("http://item.jd.com/([0-9]+).html");
			Matcher matcher = compile.matcher(url);
			String goodsId = "";
			if(matcher.find()){
				goodsId = matcher.group(1);
			}
			//设置商品ID也就是rowKey
			page.setGoodsId("jd_"+goodsId);
			
			String priceJsonResponse = PageUtils.getContent("http://p.3.cn/prices/get?skuid=J_"+goodsId);
			JSONArray objArray  = new JSONArray(priceJsonResponse);
			JSONObject obj = (JSONObject) objArray.get(0);
			page.addField("price", obj.getString("p").toString());
			//解析规格参数,解析所有的tr
			//用json数组存储
			JSONArray paramsJson = new JSONArray();
			Object[] evaliateXPath = rootNode.evaluateXPath("//*[@id=\"product-detail-2\"]/table/tbody/tr");
			for(Object trObject : evaliateXPath){
				TagNode trNode = (TagNode) trObject;
				if(StringUtils.isNotBlank(trNode.getText().toString())){
					JSONObject jsonObject = new JSONObject(); 
					//获取tr对象数组里面的th,默认获取第一个th,要是有th的获取th没有的就获取td
					Object[] thObject = trNode.evaluateXPath("/th");
					if(thObject!=null&&thObject.length>0){
						TagNode thNode = (TagNode) thObject[0];
						jsonObject.put("key","");
						jsonObject.put("value", thNode.getText().toString());
					}else{
						//获取tr对象数组里面的td
						Object[] tdObject = trNode.evaluateXPath("/td");
						TagNode tdNode1 = (TagNode) tdObject[0];
						TagNode tdNode2 = (TagNode) tdObject[1];
						jsonObject.put("key",tdNode1.getText().toString());
						jsonObject.put("value",tdNode2.getText().toString());
					  }
					 paramsJson.put(jsonObject);
					}
				}
			 page.addField("params", paramsJson.toString());
		} catch (XPatherException e) {
			e.printStackTrace();
		}
	}
}
