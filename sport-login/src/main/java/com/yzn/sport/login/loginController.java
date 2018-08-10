package com.yzn.sport.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzn.sport.buyer.BuyerService;
import com.yzn.sport.cart.CartService;
import com.yzn.sport.commons.RequestUtils;
import com.yzn.sport.pojo.Buyer;
import com.yzn.sport.pojo.BuyerCart;
import com.yzn.sport.pojo.BuyerItem;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.security.MessageDigest;

/**
 * @Author: YangZaining
 * @Date: Created in 16:17$ 2018/8/9$
 */
@Controller
public class loginController {


    @Autowired
    private BuyerService buyerService;
    @Autowired
    private CartService cartService;

    @RequestMapping("/index.aspx")
    public String index(Model model, String ReturnUrl) {
        model.addAttribute("ReturnUrl", ReturnUrl);
        return "login";
    }

    @RequestMapping("/login.aspx")
    public String login(Model model, Buyer buyer, String ReturnUrl, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String password = buyer.getPassword();
        MessageDigest instance = MessageDigest.getInstance("MD5");
        byte[] digest = instance.digest(password.getBytes());
        char[] encodeHex = Hex.encodeHex(digest);
        String password0 = new String(encodeHex);
        Buyer buyer0 = buyerService.selectByUsername(buyer.getUsername());
        boolean flag = false;
        if (buyer0 != null) {
            if (buyer0.getPassword().equals(password0)) {
                flag = true;
            }
        }
        if (!flag) {
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        } else {
            //将用户名存到cookie
            Cookie cookie = new Cookie(RequestUtils.getCSESSIONID(request, response), buyer0.getUsername());
            //把cookie中购物车信息存到redis
            Cookie[] cookies = request.getCookies();
            //存到cookie中
            ObjectMapper om = new ObjectMapper();
            om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            BuyerCart bc = null;
            for (Cookie cookie2 : cookies) {
                if (cookie2.getName().equals("buyercart")) {
                    bc = om.readValue(URLDecoder.decode(cookie2.getValue()), BuyerCart.class);
                    break;
                }
            }
            if (bc != null) {
                for (BuyerItem bi : bc.getItems()) {
                    cartService.addBuyerCartToRedis(bi.getSku().getId(), bi.getAmount(), buyer0.getUsername());
                }
            }
            return "redirect:" + ReturnUrl;
        }
    }
}
