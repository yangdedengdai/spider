package com.spider.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page {
	//商品
	private String goodsId;
	//页面原始url
	private String url;
	//页面原始的内容
	private String content;
	//商品的规格参数
	private Map<String,String> map = new HashMap<String, String>();
	//存储当前页的商品URL和下一页的URL
	private List<String> urlList = new ArrayList<String>();
	
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Map<String, String> getMap() {
		return map;
	}
	//不用set方法
	public void addField(String key,String value){
		this.map.put(key, value);
	}
	public List<String> getUrlList() {
		return urlList;
	}
	//不用set方法
	public void addUrl(String url){
		this.urlList.add(url);
	}

}
