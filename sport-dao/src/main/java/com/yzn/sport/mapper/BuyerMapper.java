package com.yzn.sport.mapper;

import com.yzn.sport.pojo.Buyer;

public interface BuyerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Buyer record);

    int insertSelective(Buyer record);

    Buyer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Buyer record);

    int updateByPrimaryKey(Buyer record);
}