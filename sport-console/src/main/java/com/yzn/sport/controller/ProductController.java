package com.yzn.sport.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import com.yzn.sport.color.ColorService;
import com.yzn.sport.pojo.Product;
import com.yzn.sport.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yzn.sport.brand.BrandService;

@Controller
public class ProductController {

    @Resource
    private ProductService productService;
    @Resource
    private BrandService brandService;
    @Resource
    private ColorService colorService;

    @RequestMapping("productList.do")
    public String productList(Model model, Product product) {
        model.addAttribute("pagination", productService.selectProducts(product));
        model.addAttribute("blist", brandService.selectAllBrand());
        model.addAttribute("product", product);
//        System.out.println(product);
        return "product/list";
    }

    //删除
    @RequestMapping("deleteProduct.do")
    public String deleteProduct(Product product) {
        productService.deleteById(product.getId());
        String url = null;
        try {
            url = "redirect:productList.do?name=" + URLEncoder.encode(product.getName(), "utf-8") + "&isShow=" + product.getIsShow() + "&brandId=" + product.getBrandId() + "&pageNo=" + product.getPageNo();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    //批量删除
    @RequestMapping("deleteProducts.do")
    public String deleteProducts(Long[] ids) {
        productService.deleteByIds(ids);
        return "redirect:productList.do";
    }

    //批量上架
    @RequestMapping("groundingProducts.do")
    public String groundingProducts(Long[] ids) {
        productService.groundingByIds(ids);
        return "redirect:productList.do";
    }

    //批量下架
    @RequestMapping("undercarriageProducts.do")
    public String undercarriageProducts(Long[] ids) {
        productService.undercarriageByIds(ids);
        return "redirect:productList.do";
    }

    //商品添加
    @RequestMapping("toProductAdd.do")
    public String toProductAdd(Model model) {
        model.addAttribute("brands", brandService.selectAllBrand());
        model.addAttribute("colors", colorService.selectAllColor());
        return "product/add";
    }

    //上传多张图片
    @ResponseBody
    @RequestMapping("productLoad.do")
    public List<String> productLoad(@RequestParam(required = false) MultipartFile[] pics, HttpSession session) {
        String path = session.getServletContext().getRealPath("upload");
        List<String> list = new ArrayList<String>();
//        StringBuilder ss = new StringBuilder();
        int cnt =0;
        for (MultipartFile pic : pics) {
            System.out.println(pic.getOriginalFilename());
            try {
                pic.transferTo(new File(path + File.separator + pic.getOriginalFilename()));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            if(cnt != 0) {
//                ss.append(",");
//            }
//            cnt++;
//            ss.append("http://localhost:8080/upload/" + pic.getOriginalFilename());
            list.add("http://localhost:8080/upload/" + pic.getOriginalFilename());
        }
        System.out.println(list);
        return list;
    }

    //添加
    @RequestMapping("productAdd.do")
    public String productAdd(Product product) {
        productService.insertSelective(product);
        return "redirect:productList.do";
    }
}
