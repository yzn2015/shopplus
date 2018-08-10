package com.yzn.font.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzn.sport.cart.CartService;
import com.yzn.sport.color.ColorService;
import com.yzn.sport.commons.RequestUtils;
import com.yzn.sport.pojo.BuyerCart;
import com.yzn.sport.pojo.BuyerItem;
import com.yzn.sport.pojo.Product;
import com.yzn.sport.pojo.Sku;
import com.yzn.sport.product.ProductService;
import com.yzn.sport.sku.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Author: YangZaining
 * @Date: Created in 22:59$ 2018/8/8$
 */
@Controller
public class CartController {

    @Resource
    private SkuService skuService;

    @Resource
    private CartService cartService;
    @Resource
    private ProductService productService;
    @Resource
    private ColorService colorService;

    //添加购物车
    @RequestMapping("/addCart")
    public String addCart(Long sid, Integer buyNum, String url, HttpServletRequest request, HttpServletResponse response) {
        //判断用户是否登陆
        System.out.println(sid+"    "+buyNum+"     "+url);
        Cookie[] cs = request.getCookies();
        String csessionid = RequestUtils.getCSESSIONID(request, response);
        boolean flag = false;
        String username = null;
        for (Cookie cookie : cs) {
            if (csessionid.equals(cookie.getName())) {
                flag = true;
                username = cookie.getValue();
                break;
            }
        }
        //如何存到redis
        if (flag) {
            cartService.addBuyerCartToRedis(sid, buyNum, username);
        }
        //如何存到cookie中
        ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        if (!flag) {
            boolean flag1 = false;
            BuyerCart bc = null;
            for (Cookie cookie : cs) {
                if ("buyercart".equals(cookie.getName())) {
                    flag1 = true;
                    try {
                        bc = om.readValue(URLDecoder.decode(cookie.getValue()), BuyerCart.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            List<BuyerItem> ls = null;
            if (flag1) {//追加
                ls = bc.getItems();
            } else {//创建购物车
                ls = new ArrayList<>();
            }
            BuyerItem bi = new BuyerItem();
            bi.setAmount(buyNum);
            Sku sku = skuService.selectByPrimaryKey(sid);
            sku.setColor(colorService.selectByPrimaryKey(sku.getColorId()));
            bi.setSku(sku);
            bi.setIsHave(sku.getStock() >= buyNum);
            Product product = productService.selectByPrimaryKey(sku.getProductId());
            product.setImgUrls(product.getImgUrl().split(","));
            bi.setProduct(product);
            boolean b = false;
            for (BuyerItem buyerItem : ls) {
                if (buyerItem.equals(bi)) {
                    buyerItem.setAmount(buyerItem.getAmount() + buyNum);
                    b = true;
                    break;
                }
            }
            System.out.println("---------------------" + b);
            if (!b) {
                ls.add(bi);
            }
            //把集合转json
            StringWriter w = new StringWriter();
            BuyerCart bc0 = new BuyerCart();
            bc0.setItems(ls);
            try {
                om.writeValue(w, bc0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Cookie cookie = new Cookie("buyercart", URLEncoder.encode(w.toString()));
            cookie.setMaxAge(7 * 24 * 3600);
            response.addCookie(cookie);
        }
        return "redirect:" + url;
    }

    //toCart
    @RequestMapping("toCart")
    public String toCart(Model model, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cs = request.getCookies();
        String csessionid = RequestUtils.getCSESSIONID(request, response);
        boolean flag = false;
        String username = null;
        for (Cookie cookie : cs) {
            if (csessionid.equals(cookie.getName())) {
                flag = true;
                username = cookie.getValue();
                break;
            }
        }
        if (flag) {
            model.addAttribute("carts", cartService.getBuyerCartFromRedis(username));
        } else {
            for (Cookie cookie : cs) {
                if ("buyercart".equals(cookie.getName())) {
                    ObjectMapper om = new ObjectMapper();
                    om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    try {
                        List<BuyerItem> list = om.readValue(URLDecoder.decode(cookie.getValue()), BuyerCart.class).getItems();
                        model.addAttribute("carts", list);
                        double money = 0;
                        for (BuyerItem buyerItem : list) {
                            money += buyerItem.getAmount()*buyerItem.getSku().getMarketPrice();
                        }
                        model.addAttribute("totalmoney", money);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return "cart";
    }
}
