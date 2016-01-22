package com.spider.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageUtils {
	private static Logger logger = LoggerFactory.getLogger(PageUtils.class);
	/**url获取页面的内容
	 * 根据
	 * @param url
	 * @return
	 */
	public static String getContent(String url){
		String content = "";
		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient client = builder.build();
		HttpUriRequest request = new HttpGet(url);
		try {
			long start_time = System.currentTimeMillis();
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity);
			logger.info("页面下载成功：{},耗时：{}",url,System.currentTimeMillis()-start_time);
		} catch (Exception e) {
			logger.error("页面下载失败：{}",url);
		}
		return content;
	}
	
}
