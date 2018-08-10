package com.yzn.sport.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzn.sport.buyer.BuyerService;
import com.yzn.sport.mapper.BuyerMapper;
import com.yzn.sport.pojo.Buyer;
import com.yzn.sport.pojo.BuyerCart;

import javax.annotation.Resource;

@Service("buyerService")
public class BuyerServiceImpl implements BuyerService {

	@Resource
	private BuyerMapper buyerMapper;
	
	public Buyer selectByUsername(String username) {
		return buyerMapper.selectByUsername(username);
	}
	
	

}
