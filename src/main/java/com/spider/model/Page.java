package com.spider.model;

import java.util.HashMap;
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
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public void addField(String key,String value){
		this.map.put(key, value);
	}
}
