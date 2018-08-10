package com.yzn.sport.brand;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.itcast.common.page.Pagination;
import com.yzn.sport.product.ProductService;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yzn.sport.commons.StringUtils;
import com.yzn.sport.mapper.ColorMapper;
import com.yzn.sport.mapper.ProductMapper;
import com.yzn.sport.mapper.SkuMapper;
import com.yzn.sport.pojo.Color;
import com.yzn.sport.pojo.Product;
import com.yzn.sport.pojo.Sku;

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
        if (plist != null) {
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

    @Autowired
    private HttpSolrServer solrServer;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StaticPageServiceImpl staticPageService;
    @Autowired
    private ColorMapper colorMapper;
//    @Resource
//    private JmsTemplate jmsTemplate;

    public void groundingByIds(Long[] ids) {
        productMapper.groundingByIds(ids);
        //向solr中存入数据
        solradd(ids);
        //jmsTemplate.send();
//        for(final Long id:ids){
//            jmsTemplate.send(new MessageCreator() {
//
//                public Message createMessage(Session session) throws JMSException {
//
//                    return session.createTextMessage(String.valueOf(id));
//                }
//            });
//        }

        //静态化
        for(Long id:ids){
            Map<String, Object>  root = new HashMap<String, Object>();

            List<Sku> skus = skuMapper.selectByProductId(id);
            float minPrice = skuMapper.selectMinPriceByProductId(id);

            Product product = productMapper.selectByPrimaryKey(id);
            product.setPrice(minPrice);
            product.setImgUrls(product.getImgUrl().split(","));
            Set<Color> colors = new HashSet<>();
            for (Sku sku : skus) {
                colors.add(colorMapper.selectByPrimaryKey(sku.getColorId()));
            }
            root.put("product", product);
            root.put("skus", skus);
            root.put("colors", colors);
            staticPageService.productStaticPage(root, String.valueOf(id));
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
        if (record.getIsCommend() == null) {
            record.setIsCommend(0);
        }
        if (record.getIsHot() == null) {
            record.setIsHot(0);
        }
        if (record.getIsNew() == null) {
            record.setIsNew(0);
        }
        record.setCreateTime(new java.util.Date());
        System.out.println(record);
        productMapper.insertSelective(record);

    }

    private void solradd(Long[] ids) {
        for (Long id : ids) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", id);//商品Id
            Product product = productMapper.selectByPrimaryKey(id);
            sid.addField("brandId", product.getBrandId());//品牌id
            sid.addField("name_ik", product.getName());//商品名称
            sid.addField("imgUrl", product.getImgUrl());//图片
            sid.addField("price", skuMapper.selectMinPriceByProductId(id));
            try {
                solrServer.add(sid);
                solrServer.commit();
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public Product selectByPrimaryKey(Long id) {
        return productMapper.selectByPrimaryKey(id);
    }

}
