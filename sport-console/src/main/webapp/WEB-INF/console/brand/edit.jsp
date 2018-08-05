<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>babasport-edit</title>
</head>
<script>
    function upload() {
        $('#jvForm').attr("action", "upload.do").ajaxSubmit(function (data) {
            $('#allUrl').attr("src", data);
            $('#imgUrl').val(data);
            $('#jvForm').attr("action","editBrand.do");
        });

    }
</script>
<body>
<div class="box-positon">
    <div class="rpos">当前位置: 品牌管理 - 修改</div>
    <form class="ropt">
        <input type="submit" onclick="this.form.action='v_list.shtml';" value="返回列表" class="return-button"/>
    </form>
    <div class="clear"></div>
</div>
<div class="body-box" style="float:right">
    <form id="jvForm" action="editBrand.do" method="post">
        <input type="hidden" value="${brand.id}" name="id"/>
        <input type="hidden" value="${name}" name="name0"/>
        <input type="hidden" value="${isDisplay}" name="isDisplay0"/>
        <input type="hidden" value="${pageNo}" name="pageNo"/>
        <table cellspacing="1" cellpadding="2" width="100%" border="0" class="pn-ftable">
            <tbody>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    <span class="pn-frequired">*</span>
                    品牌名称:
                </td>
                <td width="80%" class="pn-fcontent">
                    <input type="text" class="required" name="name" maxlength="100" value="${brand.name}"/>
                </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    <span class="pn-frequired">*</span>
                    上传商品图片(90x150尺寸):
                </td>
                <td width="80%" class="pn-fcontent">
                    注:该尺寸图片必须为90x150。
                </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h"></td>
                <td width="80%" class="pn-fcontent">
                    <img width="100" height="100" id="allUrl" src="${brand.imgUrl}"/>
                    <input type="hidden" name="imgUrl" id="imgUrl" value="${brand.imgUrl}"/>
                    <input type="file" name="file" onchange="upload()" />
                </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    品牌描述:
                </td>
                <td width="80%" class="pn-fcontent">
                    <input type="text" class="required" name="description" maxlength="80" size="60"
                           value="${brand.description}"/>
                </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    排序:
                </td>
                <td width="80%" class="pn-fcontent">
                    <input type="text" class="required" name="sort" maxlength="80" value="${brand.sort}"/>
                </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    是否可用:
                </td>
                <td width="80%" class="pn-fcontent">
                    <input type="radio" name="isDisplay" value="1" checked="checked"/>可用
                    <input type="radio" name="isDisplay" value="0" ${brand.isDisplay?"":"checked='checked'"}/>不可用
                </td>
            </tr>
            </tbody>
            <tbody>
            <tr>
                <td class="pn-fbutton" colspan="2">
                    <input type="submit" class="submit" value="提交"/> &nbsp; <input type="reset" class="reset"
                                                                                   value="重置"/>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>