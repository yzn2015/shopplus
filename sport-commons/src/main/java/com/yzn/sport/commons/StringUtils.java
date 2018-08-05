package com.yzn.sport.commons;

/**
 * @Author: YangZaining
 * @Date: Created in 18:55$ 2018/8/5$
 */
public class StringUtils {
    public static String arrayToString(String[] strs) {
        StringBuilder paramStrs = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            if (i == strs.length - 1) {
                paramStrs.append(strs[i]);
            } else {
                paramStrs.append(strs[i]);
                paramStrs.append(",");
            }
        }
        return paramStrs.toString();
    }
}
