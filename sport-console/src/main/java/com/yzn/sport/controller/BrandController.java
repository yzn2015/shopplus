package com.yzn.sport.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.yzn.sport.brand.BrandService;
import com.yzn.sport.pojo.Brand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class BrandController {
    @Resource
    private BrandService brandService;

    @RequestMapping("brandList.do")
    public String brandList(Model model, String name, String isDisplay, Integer pageNo, Integer pageSize) {
        model.addAttribute("pagination", brandService.selectBrands(name, isDisplay, pageNo, pageSize));
        model.addAttribute("name", name);
        model.addAttribute("isDisplay", isDisplay == null || "null".equals(isDisplay) ? "" : isDisplay);
        return "brand/list";
    }

    //去添加页面
    @RequestMapping("toBrandAdd.do")
    public String toBrandAdd() {
        return "brand/add";
    }

    //删除
    @RequestMapping("deleteBrand.do")
    public String deleteBrand(Long id, String name, String isDisplay, Integer pageNo, Integer pageSize) {
        brandService.deleteByPrimaryKey(id);
        return "redirect:brandList.do?name=" + name + "&isDisplay=" + isDisplay + "&pageNo=" + pageNo + "&pageSize=" + pageSize;
    }

    //批量删除
    @RequestMapping("deleteBrands.do")
    public String deleteBrands(Long[] ids, String name, String isDisplay, Integer pageNo, Integer pageSize) {
        brandService.deleteBrandsByIds(ids);
        return "redirect:brandList.do?name=" + name + "&isDisplay=" + isDisplay + "&pageNo=" + pageNo + "&pageSize=" + pageSize;
    }

    //图片上传
    @ResponseBody
    @RequestMapping("upload.do")
    public String upload(MultipartFile file, HttpSession session) {
        String path = session.getServletContext().getRealPath("upload");
        try {
            file.transferTo(new File(path + File.separator + file.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return "http://localhost:8080/upload/" + file.getOriginalFilename();
    }
    //添加

    @RequestMapping("addBrand.do")
    public String addBrand(Brand brand) {
        brandService.insertSelective(brand);
        return "redirect:brandList.do";
    }

    //去修改页面
    @RequestMapping("toUpdate.do")
    public String toUpdate(Long id, String name, Integer isDisplay, Integer pageNo, Model model) {
//        System.out.println(id +"--"+name+"--"+isDisplay+"--"+pageNo+"--"+model);
        model.addAttribute("brand", brandService.selectByPrimaryKey(id));
        model.addAttribute("name", name);
        model.addAttribute("isDisplay", isDisplay);
        model.addAttribute("pageNo", pageNo);
        return "brand/edit";

    }

    //修改
    @RequestMapping("editBrand.do")
    public String editBrand(Brand brand, String name0, String imgUrl, Integer isDisplay0, Integer pageNo) {
        System.out.println(imgUrl);
        System.out.println(brand);
        brandService.updateByPrimaryKeySelective(brand);
        String returnUrl = "";
        try {
            returnUrl = "redirect:brandList.do?name=" + URLEncoder.encode(name0, "utf-8") + "&isDisplay=" + isDisplay0 + "&pageNo=" + pageNo;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(returnUrl);
        return returnUrl;
    }

}
