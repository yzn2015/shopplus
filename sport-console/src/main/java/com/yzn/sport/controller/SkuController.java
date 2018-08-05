package com.yzn.sport.controller;

import com.yzn.sport.sku.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.annotation.Resource;

@Controller
public class SkuController {

    @Resource
    private SkuService skuService;

    @RequestMapping("skuList.do")
    public String selectSkusByProductId(Model model, Long productId) {
        model.addAttribute("skus", skuService.selectByProductId(productId));
        return "sku/list";
    }
}
