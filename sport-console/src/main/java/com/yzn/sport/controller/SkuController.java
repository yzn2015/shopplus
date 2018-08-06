package com.yzn.sport.controller;

import com.yzn.sport.pojo.Sku;
import com.yzn.sport.sku.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @RequestMapping("skuUpdate.do")
    public String updateByPrimaryKey(Long id,Float marketPrice,Float price,Float deliveFee,Integer stock,Integer upperLimit){
        System.out.println(id+"---"+marketPrice+"---"+price+"---"+deliveFee+"---"+stock+"---"+"---"+upperLimit+"---"+"---"+"---");
        Sku k = skuService.selectByPrimaryKey(id);
        k.setPrice(price);
        k.setDeliveFee(deliveFee);
        k.setMarketPrice(marketPrice);
        k.setStock(stock);
        k.setUpperLimit(upperLimit);
        skuService.updateByPrimaryKeySelective(k);
        return "redirect:skuList.do?productId="+k.getProductId();
    }

}
