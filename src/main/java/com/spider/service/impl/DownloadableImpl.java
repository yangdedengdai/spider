package com.spider.service.impl;

import java.io.IOException;




import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.spider.model.Page;
import com.spider.service.Downloadable;

public class DownloadableImpl implements Downloadable{

	public Page download(String url) {
		Page page = new Page();
//		HttpHost proxy = new HttpHost("110.73.6.167", 8132);
//		HttpClientBuilder builder = HttpClients.custom().setProxy(proxy);
		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient client = builder.build();
		HttpUriRequest request = new HttpGet(url);
		try {
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			page.setContent(EntityUtils.toString(entity));
			page.setUrl(url);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}

}
