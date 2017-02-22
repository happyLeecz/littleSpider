package me.lcz.littleSpider.tool;

import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;

/**
 * Created by lcz on 2016/11/10.
 */

public class DealParams {
    /*
    public static void dealH(String htmlPage) {
//        Document doc = Jsoup.parse(htmlPage);
//        Elements links = doc.select("a");
//        int i = 0;
//        for (Element link : links) {
//
//            //String linkHref = link.attr("abs:href");
//            String linkHref = link.absUrl("href");
//            String linkText = link.text();
//           // LinkedQueue.addUnVisitedUrl(linkHref);
//            if(linkHref.trim()=="")
//                continue;
//            System.out.println((++i) + " | "+linkHref);
//        }
         String s = null;
         Pattern pattern = Pattern.compile("(?<=<a.{0,50}href=\")[^#].{0,50}?(?=\")");
         Matcher matcher = pattern.matcher(htmlPage);
        int i = 0;
        while(matcher.find()){
           s = matcher.group(0);
            if(s.charAt(0) == '/') {
                s = "http://www.tieba.baidu.com" + s;
                System.out.println((++i) + " | "+ s);
                continue;
            }
            if(s.charAt(0)=='h'){
                System.out.println((++i) + " | "+ s);
                continue;
            }
            continue;


        }

        }
    */
    public static  String getParamsAndEncSeckeyForCommentAPI()  {
        //私钥，随机16位字符串（自己可改）
        String secKey = "cd859f54539b24b7";
        String text = "{\"username\": \"\", \"rememberLogin\": \"true\", \"password\": \"\"}";
        String modulus = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";
        String nonce = "0CoJUm6Qyw8W8jud";
        String pubKey = "010001";
        String returnString = null;

        //2次AES加密，得到params
        try {
            String params = EncryptTools.encrypt(EncryptTools.encrypt(text, nonce), secKey);

        StringBuffer stringBuffer = new StringBuffer(secKey);
        //逆置私钥
        secKey = stringBuffer.reverse().toString();
        String hex = Hex.encodeHexString(secKey.getBytes());
        BigInteger bigInteger1 = new BigInteger(hex, 16);
        BigInteger bigInteger2 = new BigInteger(pubKey, 16);
        BigInteger bigInteger3 = new BigInteger(modulus, 16);
        //RSA加密计算
        BigInteger bigInteger4 = bigInteger1.pow(bigInteger2.intValue()).remainder(bigInteger3);
        String encSecKey= Hex.encodeHexString(bigInteger4.toByteArray());
        //字符填充
        encSecKey= EncryptTools.zfill(encSecKey, 256);
        returnString = params + "   " +encSecKey;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return returnString;
        }
        //评论获取
//        Document document = Jsoup.connect("http://music.163.com/weapi/v1/resource/comments/R_SO_4_436016526/").cookie("appver", "1.5.0.75771")
//                .header("Referer", "http://music.163.com/").data("params", params).data("encSecKey", encSecKey)
//                .ignoreContentType(true).post();
//        System.out.println("评论：" + document.text());
    }


    }




