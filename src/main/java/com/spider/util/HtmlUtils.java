package com.spider.util;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class HtmlUtils {
	/**
	 * 获取指定标签的内容
	 * @param rootNode
	 * @param xpath
	 * @return
	 */
	public static String getText(TagNode rootNode,String XPath){
		String value = "";
		Object[] evaliateXPath;
		try {
			evaliateXPath = rootNode.evaluateXPath(XPath);
			if(evaliateXPath.length>0){
				TagNode titleNode = (TagNode) evaliateXPath[0];
				value = titleNode.getText().toString();
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return value;
	}
	/**
	 * 获取标签中指定属性的值
	 * @param rootNode
	 * @param xpath
	 * @param attr
	 * @return
	 */
	public static String getAttributeByName(TagNode rootNode,String XPath,String attr){
		String value = "";
		Object[] evaliateXPath;
		try {
			evaliateXPath = rootNode.evaluateXPath(XPath);
			if(evaliateXPath.length>0){
				TagNode picNode = (TagNode) evaliateXPath[0];
				value = picNode.getAttributeByName(attr);
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return value;
	}
}
