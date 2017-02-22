package me.lcz.littleSpider.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lcz on 2016/11/14.
 */
public class DealJSON {
    public String dealJSON(String JSON){
        Pattern pattern = Pattern.compile("(?<=\"total\":)\\d*");
        Matcher matcher = pattern.matcher(JSON);
        if (matcher.find())
         return matcher.group(0);
        else
            return null;
    }
}
