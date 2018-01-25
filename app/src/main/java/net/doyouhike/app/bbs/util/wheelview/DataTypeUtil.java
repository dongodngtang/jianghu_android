package net.doyouhike.app.bbs.util.wheelview;

import java.text.DecimalFormat;

/**
 *
 * 数据类型 转换
 * Created by terry on 5/13/16.
 */
public class DataTypeUtil {


    /**
     * @Description描述: 格式化金额 保留两位小数 0.00
     * @param money
     * @return String
     */
    public static String FormatDouble(double money,String reg) {
        DecimalFormat df = new DecimalFormat(reg);
        return df.format(money);
    }

    /**
     * @Description描述: 格式化金额 保留两位小数 0.00
     * @param money
     * @return String
     */
    public static String FormatString(String money,String reg) {
        double result = 0.00;
        try {
            result = Double.valueOf(money);
        } catch (Exception e) {
            return String.valueOf(result);
        }
        return FormatDouble(result,reg);
    }

}
