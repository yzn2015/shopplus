package com.yzn.font.controller;

import com.yzn.sport.solr.ProductSolrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author: YangZaining
 * @Date: Created in 15:52$ 2018/8/8$
 */
@Controller
public class ProductSolrController {

    @Resource
    private ProductSolrService productSolrService;

    @RequestMapping("Search")
    public String search(Model model, String keyword,Integer pageNo,Integer pageSize){
        model.addAttribute("pagination",productSolrService.selectProductsSolr(keyword,pageNo,pageSize));
        return "search";
    }
}
