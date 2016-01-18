package com.spider.dao.impl;

import java.io.IOException;
import java.util.Map;

import com.spider.dao.Storeable;
import com.spider.model.Page;
import com.spider.util.HbaseUtils;

public class HBaseStoreableImpl implements Storeable{
	HbaseUtils hbaseUtils = new HbaseUtils();
	public void store(Page page) {
		String goodsId = page.getGoodsId();
		Map<String, String> map = page.getMap();
		try {
			hbaseUtils.put(HbaseUtils.TABLE_NAME, goodsId, HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_DATA_URL, page.getUrl());
			hbaseUtils.put(HbaseUtils.TABLE_NAME, goodsId, HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_PIC_URL, map.get("picUrl"));
			hbaseUtils.put(HbaseUtils.TABLE_NAME, goodsId, HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_PRICE, map.get("price"));
			hbaseUtils.put(HbaseUtils.TABLE_NAME, goodsId, HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_TITLE, map.get("title"));
			hbaseUtils.put(HbaseUtils.TABLE_NAME, goodsId, HbaseUtils.COLUMNFAMILY_2, HbaseUtils.COLUMNFAMILY_2_PARAM, map.get("params"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
