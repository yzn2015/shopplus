package com.yzn.sport.login;

import cn.itcast.common.page.Pagination;

public interface ProductSolrService {

    public Pagination selectProductsSolr(String keyword,Integer pageNo,Integer pageSize );
}
