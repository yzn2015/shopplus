package com.yzn.font.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzn.sport.commons.RequestUtils;
import com.yzn.sport.pojo.BuyerCart;
import com.yzn.sport.pojo.BuyerItem;
import com.yzn.sport.pojo.Sku;
import com.yzn.sport.sku.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: YangZaining
 * @Date: Created in 22:59$ 2018/8/8$
 */
@Controller
public class CartController {

    @Resource
    private SkuService skuService;
    //添加购物车
    @RequestMapping("/addCart")
    public String addCart(Long sid, Integer buyNum, String url, HttpServletRequest request, HttpServletResponse response) {
        //判断用户是否登陆
        Cookie[] cs = request.getCookies();
        String csessionid = RequestUtils.getCSESSIONID(request, response);
        boolean flag =false;
        String username = null;
        for (Cookie cookie : cs) {
            if(csessionid.equals(cookie.getName())){
                flag = true;
                username = cookie.getValue();
                break;
            }
        }
        //如何存到redis
        if(flag){

        }
        //如何存到cookie中
        ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        if(!flag){
            boolean flag1 = false;
            BuyerCart bc = null;
            for (Cookie cookie : cs) {
                if("buyercart".equals(cookie.getName())) {
                    flag1 = true;
                    try {
                        bc = om.readValue(cookie.getValue(),BuyerCart.class);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
            }
            List<BuyerItem> ls =null;
            if(flag1){//追加
                ls = bc.getItems();

            }
            else {//创建购物车
                ls = new ArrayList<>();
            }
            BuyerItem bi = new BuyerItem();
            bi.setAmount(buyNum);
            Sku sk = skuService.selectByPrimaryKey(sid);
            bi.setSku(sk);
            bi.setIsHave(sk.getStock()>=buyNum);
            ls.add(bi);
            StringWriter stringWriter = new StringWriter();
            try {
                om.writeValue(stringWriter,ls);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Cookie cookie = new Cookie("buyercart",stringWriter.toString());
            cookie.setMaxAge(7*24*60*60);
            response.addCookie(cookie);
        }
        return "redirect:"+url;
    }
}
