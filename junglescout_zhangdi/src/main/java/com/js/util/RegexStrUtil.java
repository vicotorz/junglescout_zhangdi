package com.js.util;

import com.js.constant.CommonConstant;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexStrUtil {


    public static ArrayList<String> getRegexStr(String str, ArrayList<String> regexStrList) {
        ArrayList<String> resultList = new ArrayList<>();
        for (String targetPattern : regexStrList) {
            Pattern p = Pattern.compile(targetPattern);
            Matcher m = p.matcher(str);
            resultList.add(m.find() ? m.group() : CommonConstant.EMPTY_STR);

        }
        return resultList;
    }

    public static String getRegexStrDetail(String str, String regexStr) {
        Pattern p = Pattern.compile(regexStr);
        Matcher m = p.matcher(str);
        return m.find() ? m.group() : CommonConstant.EMPTY_STR;
    }


}
