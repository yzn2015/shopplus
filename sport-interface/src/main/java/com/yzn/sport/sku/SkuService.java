package com.yzn.sport.sku;

import java.util.List;

import com.yzn.sport.pojo.Sku;

public interface SkuService {

	public List<Sku> selectByProductId(Long productId);
}
