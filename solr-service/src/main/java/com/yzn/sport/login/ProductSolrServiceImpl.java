package com.yzn.sport.login;

import cn.itcast.common.page.Pagination;
import com.yzn.sport.pojo.Product;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: YangZaining
 * @Date: Created in 15:08$ 2018/8/8$
 */
@Service("productSolrService")
public class ProductSolrServiceImpl implements ProductSolrService {

    @Autowired
    private HttpSolrServer solrServer;

    @Override
    public Pagination selectProductsSolr(String keyword, Integer pageNo, Integer pageSize) {
        if(pageNo==null){
            pageNo=1;
        }
        if(pageSize==null){
            pageSize=24;
        }
        //拼接条件
        StringBuilder params = new StringBuilder();
        List<Product> products = new ArrayList<Product>();
        SolrQuery solrQuery = new SolrQuery();
        //查询条件
        solrQuery.set("q", "name_ik:" + keyword);  //name_ik:运动裤
        params.append("keyword=").append(keyword);
        //过滤
//        if()
        //词高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("name_ik");
        //样式
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        solrQuery.setHighlightSimplePost("</span>");
        //排序
        solrQuery.addSort("price", SolrQuery.ORDER.asc);
        solrQuery.setStart((pageNo - 1) * pageSize);//当前页
        solrQuery.setRows(pageSize);//个数
        //执行查询
        QueryResponse response = null;
        try {
            response = solrServer.query(solrQuery);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        //取高亮
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        //Map K:V 442: Map
        //Map K:V name_ik:List<String>
        //List<String> list 2016年最新款...
        //结果集
        SolrDocumentList docs = response.getResults();
        //发现的条数（总条数）构建分页时用到
        long numFound = docs.getNumFound();
        for (SolrDocument doc : docs) {
            //创建商品对象
            Product product2 = new Product();
            //商品ID
            String id = (String) doc.get("id");
            product2.setId(Long.parseLong(id));
            //商品名称ik
            Map<String, List<String>> map = highlighting.get(id);
            List<String> list = map.get("name_ik");
            product2.setName(list.get(0));
            String imgUrl = (String) doc.get("imgUrl");
            product2.setImgUrl(imgUrl.split(",")[0]);
            product2.setImgUrls(imgUrl.split(","));
            product2.setPrice((Float) doc.get("price"));
            products.add(product2);
        }
        //分页
        System.out.println(products);
        Pagination pagination = new Pagination(pageNo, pageSize, (int) numFound, products);
        //展示界面
        String url = "/Search";
        System.out.println(params);
        pagination.pageView(url, params.toString());
        System.out.println(pagination.getList());
        return pagination;
    }
}
