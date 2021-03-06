package com.yzn.sport.brand;

import com.yzn.sport.mapper.SkuMapper;
import com.yzn.sport.pojo.Sku;
import com.yzn.sport.sku.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: YangZaining
 * @Date: Created in 18:45$ 2018/8/5$
 */
@Service("skuService")
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuMapper skuMapper;

    public List<Sku> selectByProductId(Long productId) {
        return skuMapper.selectByProductId(productId);
    }

    @Override
    public Sku selectByPrimaryKey(Long Id) {
        return skuMapper.selectByPrimaryKey(Id);
    }

    @Override
    public void updateByPrimaryKeySelective(Sku sku) {
        skuMapper.updateByPrimaryKeySelective(sku);
    }
}
