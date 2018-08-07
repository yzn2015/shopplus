package com.yzn.sport.brand;

import cn.itcast.common.page.Pagination;
import com.yzn.sport.commons.StringUtils;
import com.yzn.sport.mapper.ProductMapper;
import com.yzn.sport.pojo.Product;
import com.yzn.sport.product.ProductService;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: YangZaining
 * @Date: Created in 18:39$ 2018/8/5$
 */

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;


    public Pagination selectProducts(Product record) {
        if (record.getName() != null && "".equals(record.getName())) {
            record.setName(null);
        }
        if (record.getBrandId() != null && record.getBrandId() == 0) {
            record.setBrandId(null);
        }
        if (record.getIsShow() != null && record.getIsShow() == 2) {
            record.setIsShow(null);
        }
        if (record.getSize() == null || "".equals(record.getSize())) {
            record.setSize(5);
        }
        if (record.getPageNo() == null || "".equals(record.getPageNo())) {
            record.setPageNo(1);
        }
        record.setFromLine((record.getPageNo() - 1) * record.getSize());
        List<Product> plist = productMapper.selectProducts(record);
        if(plist!=null) {
            for (Product product : plist) {
                product.setImgUrls(product.getImgUrl().split(","));
            }
        }
        Pagination pagination = new Pagination(record.getPageNo(), record.getSize(), productMapper.selectProductcount(record), plist);
        StringBuilder params = new StringBuilder();
        params.append("size=5");
        if (record.getName() != null) {
            params.append("&name=");
            params.append(record.getName());
        }
        if (record.getBrandId() != null) {
            params.append("&brandId=");
            params.append(record.getBrandId());
        }
        if (record.getIsShow() != null) {
            params.append("&isShow=");
            params.append(record.getIsShow());
        }
        String url = "productList.do";
        pagination.pageView(url, params.toString());
        return pagination;
    }


    public void deleteById(Long id) {
        productMapper.deleteById(id);
    }


    public void deleteByIds(Long[] ids) {
        productMapper.deleteByIds(ids);
    }

    @Resource
    private HttpSolrServer solrServer;

    public void groundingByIds(Long[] ids) {
        productMapper.groundingByIds(ids);
        //向solr中存入数据
        SolrInputDocument sid = new SolrInputDocument();
//        for(Long id :ids){
            sid.addField("id",100);
//        }
        try {
            solrServer.add(sid);
            solrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void undercarriageByIds(Long[] ids) {
        productMapper.undercarriageByIds(ids);
    }


    public void insertSelective(Product record) {
        record.setImgUrl(StringUtils.arrayToString(record.getImgUrls()));
        record.setColors(StringUtils.arrayToString(record.getColorss()));
        record.setSizes(StringUtils.arrayToString(record.getSizess()));
        record.setIsDel(1);
        record.setIsShow(0);
        if(record.getIsCommend()==null) {
            record.setIsCommend(0);
        }
        if(record.getIsHot()==null){
            record.setIsHot(0);
        }
        if(record.getIsNew()==null){
            record.setIsNew(0);
        }
        record.setCreateTime(new java.util.Date());
        System.out.println("wqeeqwewqewq");
        System.out.println(record);
        System.out.println("aaaaaaaaaaa");
        productMapper.insertSelective(record);

    }

}
