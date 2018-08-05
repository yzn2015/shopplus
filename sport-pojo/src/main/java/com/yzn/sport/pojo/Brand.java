package com.yzn.sport.pojo;

import java.io.Serializable;

public class Brand implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

    private String name;

    private String description;

    private String imgUrl;

    private String webSite;

    private Integer sort;

    private Boolean isDisplay;

    /*
     * 分页属性
     */
    private Integer size;
    private Integer fromLine;

    public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getFromLine() {
		return fromLine;
	}

	public void setFromLine(Integer fromLine) {
		this.fromLine = fromLine;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite == null ? null : webSite.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", webSite='" + webSite + '\'' +
                ", sort=" + sort +
                ", isDisplay=" + isDisplay +
                ", size=" + size +
                ", fromLine=" + fromLine +
                '}';
    }
}