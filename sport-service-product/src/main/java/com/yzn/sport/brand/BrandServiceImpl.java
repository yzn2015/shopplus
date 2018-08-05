package com.yzn.sport.brand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzn.sport.mapper.BrandMapper;
import com.yzn.sport.pojo.Brand;

import cn.itcast.common.page.Pagination;

import javax.annotation.Resource;

@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService {

    private BrandMapper brandMapper;
    private RedisTemplate redisTemplate;

    @Autowired
    public BrandServiceImpl(BrandMapper brandMapper, RedisTemplate redisTemplate) {
        this.brandMapper = brandMapper;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    @Override
    public Pagination selectBrands(String name, String isDisplay, Integer pageNo, Integer pageSize) {
        Brand brand = new Brand();
        if (!"".equals(name)) {
            brand.setName(name);
        }
        if (isDisplay != null) {
            brand.setIsDisplay("1".equals(isDisplay));
        }

        //分页

        if (pageSize != null) {
            brand.setSize(pageSize);
        } else {
            brand.setSize(5);
        }
        if (pageNo != null) {
            brand.setFromLine((pageNo - 1) * brand.getSize());
        } else {
            brand.setFromLine(0);
            pageNo = 1;
        }
        List<Brand> brands = brandMapper.selectBrands(brand);
        Pagination pagination = new Pagination(pageNo, brand.getSize(), brandMapper.selectBrandcount(brand), brands);
        StringBuffer params = new StringBuffer();
        params.append("size=5");
        if (name != null && !name.equals("")) {
            params.append("&name=" + name);
        }
        if (isDisplay != null) {
            params.append("&isDisplay=" + isDisplay);
        }
        String url = "brandList.do";
        pagination.pageView(url, params.toString());
        return pagination;
    }

    public void deleteByPrimaryKey(Long id) {
        brandMapper.deleteByPrimaryKey(id);

    }

    public void deleteBrandsByIds(Long[] ids) {
        brandMapper.deleteBrandsByIds(ids);

    }

    public void insertSelective(Brand record) {
//        record.setId(redisTemplate.inc);
        brandMapper.insertSelective(record);

    }

    public Brand selectByPrimaryKey(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    public void updateByPrimaryKeySelective(Brand record) {
        brandMapper.updateByPrimaryKeySelective(record);

    }

    public List<Brand> selectAllBrand() {
        return brandMapper.selectAllBrand();
    }


}
