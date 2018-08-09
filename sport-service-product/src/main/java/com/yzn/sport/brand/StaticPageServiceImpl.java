package com.yzn.sport.brand;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * @Author: YangZaining
 * @Date: Created in 20:50$ 2018/8/8$
 */

public class StaticPageServiceImpl implements ServletContextAware {
    private Configuration freeMarkerConfigurer;//模板的位置，字符集

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer.getConfiguration();
    }

    //静态化 商品
    public void productStaticPage(Map<String,Object> root,String id){
        //输出的路径  全路径
        String path = getPath("/html/" + id + ".html");
        System.out.println(path);
        File f = new File(path);
        File parentFile = f.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        Writer out = null;
        try {
            //读文件  UTF-8
            Template template = freeMarkerConfigurer.getTemplate("product.html");

            //输出  UTF-8
            out = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            //处理
            template.process(root, out);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null != out){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //获取路径
    public String getPath(String name){
        return aplication.getRealPath(name);
    }
    //声明
    private ServletContext aplication;

    public void setServletContext(ServletContext servletContext) {
        this.aplication = servletContext;
    }
}
