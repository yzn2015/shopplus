package com.yzn.sport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("index.do")
    public String index() {
        return "index";
    }

    @RequestMapping("top.do")
    public String top() {
        return "top";
    }

    @RequestMapping("main.do")
    public String main() {
        return "main";
    }

    @RequestMapping("left.do")
    public String left() {
        return "left";
    }

    @RequestMapping("right.do")
    public String right() {
        return "right";
    }

    @RequestMapping("product_main.do")
    public String product_main() {
        return "frame/product_main";
    }

    @RequestMapping("product_left.do")
    public String product_left() {
        return "frame/product_left";
    }

//    @RequestMapping("productList.do")
//    public String productList() {
//        return "product/list";
//    }

}
