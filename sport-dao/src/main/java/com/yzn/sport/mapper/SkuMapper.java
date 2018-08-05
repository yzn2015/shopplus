package com.yzn.sport.mapper;

import java.util.List;

import com.yzn.sport.pojo.Sku;

public interface SkuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Sku record);

    int insertSelective(Sku record);

    Sku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);
    
    List<Sku> selectByProductId(Long productId);
}