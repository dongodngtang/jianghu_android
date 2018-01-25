package net.doyouhike.app.bbs.biz.presenter;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by terry on 4/5/16.
 */
public class test {



    public static void main(String[] ss){
        String a = "http://dev.static.doyouhike.com/files/2016/04/15/f/f89a0e19ab8095ffc1ea37942856be38.jpg";
        String parm = "http|ftp|https|www://";
        Pattern pattern = Pattern.compile(parm);
        Matcher matcher = pattern.matcher(a);
        if(matcher.matches())
            System.out.print("true");
        else
            System.out.print("false");
        System.out.print(parm);
        return ;
    }
}
