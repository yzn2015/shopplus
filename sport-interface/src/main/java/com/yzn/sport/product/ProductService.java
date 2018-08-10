package com.yzn.sport.product;


import cn.itcast.common.page.Pagination;
import com.yzn.sport.pojo.Product;


public interface ProductService {

    public Pagination selectProducts(Product record);

    public void deleteById(Long id);

    public void deleteByIds(Long[] ids);

    public void groundingByIds(Long[] ids);

    public void undercarriageByIds(Long[] ids);

    public void insertSelective(Product record);

    public Product selectByPrimaryKey(Long id);
}
