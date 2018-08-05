package com.yzn.sport.mapper;

import java.util.List;

import com.yzn.sport.pojo.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKeyWithBLOBs(Product record);

    int updateByPrimaryKey(Product record);
    
    List<Product> selectProducts(Product record);
    
    int selectProductcount(Product record);
    
    int deleteById(Long id);
    
    int deleteByIds(Long[] ids);
    
    int groundingByIds(Long[] ids);
    
    int undercarriageByIds(Long[] ids);
}