package com.js.constant;

/**
 * 正则表达式常量
 * */
public class RegxConstant {

    public static final String PRO_STAR_ORIGIN = "((\\d+)|(\\d+\\.\\d*))outof5stars";
    public static final String PRO_RANK_ORIGIN = "Rank#?((\\d|,)+)in[a-zA-Z0-9|&|,]*";


    public static final String ASIN_REGEX_DETAIL = "(\\w{10})";
    public static final String PRO_STAR_DETAIL = "(\\d+\\.\\d*)|((\\d+))";
    public static final String PRO_RANK_DETAIL = "(\\d+)in(.*)";

    public static final String PRO_RANK_REPLACE_PATTERN_PREFIX = "(\\d+)in";
    public static final String PRO_RANK_REPLACE_PATTERN_SUFFIX = "\\(.*\\)#";


    public static final String ASIN = "ASIN";

}
