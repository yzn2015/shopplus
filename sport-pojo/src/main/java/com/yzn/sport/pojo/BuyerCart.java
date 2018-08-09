package com.yzn.sport.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: YangZaining
 * @Date: Created in 23:20$ 2018/8/8$
 */
public class BuyerCart implements Serializable{

	

	private static final long serialVersionUID = 1L;
	//1：商品结果集 List<BuyerItem> 
	private List<BuyerItem> items = new ArrayList<BuyerItem>();
	
	

	public List<BuyerItem> getItems() {
		return items;
	}

	public void setItems(List<BuyerItem> items) {
		this.items = items;
	}
	
	
	
	
}
