package net.doyouhike.app.bbs.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.util.linktext.event.Clickable;
import net.doyouhike.app.bbs.util.linktext.event.TextClickListener;
import net.doyouhike.app.bbs.util.linktext.linkutil.LinkTextUtil;
import net.doyouhike.app.library.ui.snackbar.SnackBarItem;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author terry
 * @TODO:
 * @date: $date$ $time$
 */
public class StringUtil {

    private static final String TAG = StringUtil.class.getSimpleName();
    public static final int SERIAL_LENGTH = 8;
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public StringUtil() {
    }

    public static String[] toArray(String target, String delim) {
        if (target == null) {
            return new String[0];
        } else {
            StringTokenizer st = new StringTokenizer(target, delim);
            String[] result = new String[st.countTokens()];

            for (int i = 0; st.hasMoreTokens(); ++i) {
                result[i] = st.nextToken();
            }

            return result;
        }
    }

    public static String arrayToStr(String[] arr) {
        StringBuilder sb = new StringBuilder();
        if (arr == null) {
            return "";
        } else {
            for (int i = 0; i < arr.length; ++i) {
                sb.append(arr[i]);
                sb.append("\r\n");
            }

            return sb.toString();
        }
    }

    public static String replaceStr(String str, String oldStr, String newStr) {
        int s = 0;
        boolean e = false;
        int ol = oldStr.length();

        StringBuffer result;
        int e1;
        for (result = new StringBuffer(); (e1 = str.indexOf(oldStr, s)) >= 0; s = e1 + ol) {
            result.append(str.substring(s, e1));
            result.append(newStr);
        }

        result.append(str.substring(s));
        return result.toString();
    }

    public static String convertToStr(Object obj) {
        String str = "";
        if (!isEmptyObj(obj)) {
            str = obj.toString().trim();
        }

        return str;
    }

    public static String convertDbStrToStr(Object obj) {
        String str = "";
        if (!isEmptyObj(obj)) {
            str = obj.toString().trim().toLowerCase();
        }

        if (str.contains("_")) {
            String[] tmp = str.split("_");
            int length = tmp.length;
            StringBuilder sb = new StringBuilder();
            sb.append(tmp[0]);

            for (int i = 1; i < length; ++i) {
                String strtmp = tmp[i];
                sb.append(strtmp.substring(0, 1).toUpperCase() + strtmp.substring(1, strtmp.length()));
            }

            str = sb.toString();
        }

        return str;
    }

    public static String convertToStrs(Object obj) {
        String str = " ";
        if (!isEmptyObj(obj)) {
            str = obj.toString().trim();
        }

        return str;
    }

    public static String parseDateToString(Object obj, String format) {
        if (obj == null) {
            return "";
        } else {
            if (isEmpty(format)) {
                format = "yyyy-MM-dd HH:mm";
            }

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(obj);
        }
    }

    public static boolean isNotEmptyObj(Object obj) {
        return !isEmptyObj(obj);
    }

    public static boolean isEmptyObj(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        } else if (obj instanceof String) {
            return ((String) obj).trim().length() == 0;
        } else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        } else if (!(obj instanceof Object[])) {
            return false;
        } else {
            Object[] object = (Object[]) obj;
            boolean empty = true;

            for (int i = 0; i < object.length; ++i) {
                if (!isEmptyObj(object[i])) {
                    empty = false;
                    break;
                }
            }

            return empty;
        }
    }

    public static String convertDecToStr(Object obj, String code) {
        String str = convertToStr(obj);

        try {
            if (!str.equals("")) {
                str = URLDecoder.decode(str, code);
            }
        } catch (Exception var3) {
            ;
        }

        return str;
    }

    public static String wrapClauses(String[] strs) {
        if (strs != null && strs.length != 0) {
            String rel = "(";
            StringBuilder sb = new StringBuilder();
            sb.append(rel);

            for (int i = 0; i < strs.length; ++i) {
                sb.append("\'" + strs[i] + "\',");
            }

            rel = sb.toString();
            rel = removeEnd(rel, ",");
            return rel + ")";
        } else {
            return "(\'\')";
        }
    }

    public static String wrapClause(String[] strs) {
        String rel = "";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < strs.length; ++i) {
            sb.append("\'");
            sb.append(strs[i]);
            sb.append("\'");
        }

        rel = sb.toString();
        rel = removeEnd(rel, ",");
        return rel;
    }

    public static String wrapClauseList(Map<String, List<String>> map) {
        String rel = "";
        StringBuilder sb = new StringBuilder();
        if (!isEmptyObj(map)) {
            Iterator iter = map.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                sb.append(" " + (String) entry.getKey() + " in " + wrapMidList((List) entry.getValue()) + " and");
            }

            rel = sb.toString();
            rel = removeEnd(rel, "and");
        }

        return rel;
    }

    public static String wrapClauseStringArray(Map<String, String[]> map) {
        String rel = "";
        if (!isEmptyObj(map)) {
            StringBuilder sb = new StringBuilder();
            Iterator iter = map.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                sb.append(" " + (String) entry.getKey() + " in " + wrapClauses((String[]) entry.getValue()) + " and");
            }

            rel = sb.toString();
            rel = removeEnd(rel, "and");
        }

        return rel;
    }

    public static String wrapClauseString(Map<String, String> map) {
        String rel = "";
        StringBuilder sb = new StringBuilder();
        if (!isEmptyObj(map)) {
            Iterator iter = map.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                sb.append(" " + (String) entry.getKey() + " = " + (String) entry.getValue() + " and");
            }

            rel = sb.toString();
            rel = removeEnd(rel, "and");
        }

        return rel;
    }

    public static String wrapMidList(List<String> detailMidList) {
        if (detailMidList != null && detailMidList.size() > 0) {
            String imidStr = "(";
            StringBuilder sb = new StringBuilder();
            sb.append(imidStr);
            Iterator iter = detailMidList.iterator();

            while (iter.hasNext()) {
                String str = (String) iter.next();
                if (iter.hasNext()) {
                    sb.append("\'");
                    sb.append(str);
                    sb.append("\'");
                } else {
                    sb.append("\'");
                    sb.append(str);
                    sb.append("\')");
                }
            }

            return sb.toString();
        } else {
            return "(\'\')";
        }
    }

    public static String toInsql(String[] values) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < values.length; ++i) {
            sb.append("\'");
            sb.append(values[i]);
            sb.append("\'");
            if (i != values.length - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    public static List<String> coverToList(String data) {
        ArrayList list = new ArrayList();
        if (data != null && !data.trim().equals("")) {
            String[] datas = data.split(",");
            int num = datas.length;

            for (int i = 0; i < num; ++i) {
                list.add(datas[i]);
            }
        }

        return list;
    }

    public static String removeEnd(String str, String remove) {
        return !str.equals("") && !remove.equals("") ? (str.endsWith(remove) ? str.substring(0, str.length() - remove.length()) : str) : str;
    }

    public static String convertBig5ToUnicode(Object obj) {
        try {
            return new String(convertToStr(obj).getBytes("Big5"), "ISO8859_1");
        } catch (UnsupportedEncodingException var2) {
            Log.e(TAG, "Exception", var2);
            return "";
        }
    }

    public static String convertToUnicode(Object obj) {
        try {
            return new String(convertToStr(obj).getBytes(), "ISO8859_1");
        } catch (UnsupportedEncodingException var2) {
            Log.e(TAG, "Exception", var2);
            return "";
        }
    }

    public static String convertUnicodeToBig5(Object obj) {
        try {
            return new String(convertToStr(obj).getBytes("ISO8859_1"), "Big5");
        } catch (UnsupportedEncodingException var2) {
            Log.e(TAG, "Exception", var2);
            return "";
        }
    }

    public static String convertUnicodeToGbk(Object obj) {
        try {
            return new String(convertToStr(obj).getBytes("utf-8"), "GBK");
        } catch (UnsupportedEncodingException var2) {
            Log.e(TAG, "Exception", var2);
            return "";
        }
    }

    public static String convertUnicodeToUtf8(Object obj) {
        try {
            return new String(convertToStr(obj).getBytes("ISO8859_1"), "utf-8");
        } catch (UnsupportedEncodingException var2) {
            Log.e(TAG, "Exception", var2);
            return "";
        }
    }

    public static String convertUtf8ToUnicode(Object obj) {
        try {
            return new String(convertToStr(obj).getBytes("utf-8"), "ISO8859_1");
        } catch (UnsupportedEncodingException var2) {
            Log.e(TAG, "Exception", var2);
            return "";
        }
    }

    public static String convertLatin1ToUtf8(Object obj) {
        try {
            return new String(convertToStr(obj).getBytes("Latin1"), "utf-8");
        } catch (UnsupportedEncodingException var2) {
            Log.e(TAG, "Exception", var2);
            return "";
        }
    }

    public static String convertToUtf8(Object obj) {
        try {
            return new String(convertToStr(obj).getBytes(), "utf-8");
        } catch (UnsupportedEncodingException var2) {
            Log.e(TAG, "Exception", var2);
            return "";
        }
    }

    public static String convertBig5ToUtf8(Object obj) {
        try {
            return new String(convertToStr(obj).getBytes("Big5"), "utf-8");
        } catch (UnsupportedEncodingException var2) {
            Log.e(TAG, "Exception", var2);
            return "";
        }
    }

    public static String convertUtfToBig5(Object obj) {
        try {
            return new String(convertToStr(obj).getBytes("utf-8"), "Big5");
        } catch (UnsupportedEncodingException var2) {
            Log.e(TAG, "Exception", var2);
            return "";
        }
    }

    public static String splitList(List<String> list) {
        String result = "";
        StringBuilder sb = new StringBuilder();
        if (isEmptyObj(list)) {
            Iterator iter = list.iterator();

            while (iter.hasNext()) {
                String var10000 = (String) iter.next();
                sb.append((String) iter.next());
                sb.append("##");
            }

            result = sb.toString();
            result = removeEnd(result, "##");
        }

        return result;
    }

    public static String trim2KindsSpace(String param) {
        param = param.trim();
        if (isEmpty(param)) {
            return param;
        } else {
            while (param.charAt(0) == 12288) {
                param = param.substring(1, param.length()).trim();
            }

            while (param.endsWith("　")) {
                param = param.substring(0, param.length() - 1).trim();
            }

            return param;
        }
    }

    public static String replaceCharacter(String value) {
        if (value == null) {
            return null;
        } else {
            char[] objTargetChar = new char[]{'－', '—', '(', ')', '０', '１', '２', '３', '４', '５', '６', '７', '８', '９', '？', '＆'};
            char[] objReplaceChar = new char[]{'-', '-', '（', '）', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '?', '&'};
            int i = 0;

            for (int j = objTargetChar.length; i < j; ++i) {
                value = value.replace(objTargetChar[i], objReplaceChar[i]);
            }

            return value;
        }
    }

    public static String cutString(String value, int charNumber) {
        return charNumber < value.length() ? value.substring(0, charNumber) + "..." : value;
    }

    public static String qChangeToB(String qjString) {
        char[] c = qjString.toCharArray();

        for (int i = 0; i < c.length; ++i) {
            if (c[i] == 12288) {
                c[i] = 32;
            } else if (c[i] > '\uff00' && c[i] < '｟') {
                c[i] -= 'ﻠ';
            }
        }

        return new String(c);
    }

    public static String bChangeToQ(String bjString) {
        char[] c = bjString.toCharArray();

        for (int i = 0; i < c.length; ++i) {
            if (c[i] == 32) {
                c[i] = 12288;
            } else if (c[i] < 127) {
                c[i] += 'ﻠ';
            }
        }

        return new String(c);
    }

    public static String valueOf(Object o) {
        return o == null ? "" : o.toString();
    }

    public static String valueOf(Object o, String defaultValue) {
        return o == null ? defaultValue : o.toString();
    }

    public static String cutAtStopCharater(String src) {
        int iIndex = src.indexOf(0);
        return iIndex >= 0 ? src.substring(0, iIndex) : src;
    }

    public static String firstLetterUpper(String s) {
        return s != null && s.length() != 0 ? Character.toUpperCase(s.charAt(0)) + s.substring(1) : "";
    }

    public static String nullToEmptyChar(String mayNullString) {
        return !isEmpty(mayNullString) && !"0.0".equals(mayNullString) ? (mayNullString.endsWith(".0") ? mayNullString.substring(0, mayNullString.length() - 2) : (mayNullString.endsWith(".00") ? mayNullString.substring(0, mayNullString.length() - 3) : mayNullString)) : "";
    }

    public static String zeroToEmptyChar(double mayNullString) {
        if (mayNullString == 0.0D) {
            return "";
        } else {
            String strDeleteZero = String.valueOf(mayNullString);
            return strDeleteZero.endsWith(".0") ? strDeleteZero.substring(0, strDeleteZero.length() - 2) : (strDeleteZero.endsWith(".00") ? strDeleteZero.substring(0, strDeleteZero.length() - 3) : String.valueOf(mayNullString));
        }
    }

    public static String zeroSubToString(String mayZeroString) {
        return !isEmpty(mayZeroString) && !"0.0".equals(mayZeroString) ? (mayZeroString.endsWith(".0") ? mayZeroString.substring(0, mayZeroString.length() - 2) : (mayZeroString.endsWith(".000000") ? mayZeroString.substring(0, mayZeroString.length() - 4) : mayZeroString)) : "";
    }

    public static String getNullOrEmptyText(Object value) {
        return value != null && value.toString().length() != 0 ? value.toString() : "无";
    }

    public static List<String> distinct(List<String> values) {
        HashMap tab = new HashMap();
        ArrayList result = new ArrayList();
        Iterator var4 = values.iterator();

        while (var4.hasNext()) {
            String e = (String) var4.next();
            if (!tab.containsKey(e)) {
                tab.put(e, e);
                result.add(e);
            }
        }

        return result;
    }

    public static boolean isBlank(String str) {
        if (str != null && str.length() != 0) {
            int strLen = str.length();

            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(String str) {
        if (str != null && str.length() != 0) {
            int strLen = str.length();

            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0 || str.equals("null");
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String toTrim(String s) {
        String result = "";
        if (null != s && !"".equals(s)) {
            result = s.replaceAll("[　*| *| *|//s*]*", "");
        }
        return result;
    }

    public static void showSnack(Context context, int msgId) {
        if (context instanceof Activity) {
            Activity mActivity = (Activity) context;
            int txtColor = context.getResources().getColor(R.color.color_snack);
            new SnackBarItem.Builder(mActivity)
                    .setMessageResource(msgId)
                    .setDuration(1000)
                    .setSnackBarMessageColor(txtColor)
                    .show();
        } else {
            Toast.makeText(context, context.getString(msgId), Toast.LENGTH_SHORT).show();
        }
    }

    public static void showSnack(Context context, String msg) {
        if (context instanceof Activity) {
            Activity mActivity = (Activity) context;
            int txtColor = context.getResources().getColor(R.color.color_snack);
            new SnackBarItem.Builder(mActivity)
                    .setMessage(msg)
                    .setDuration(1000)
                    .setSnackBarMessageColor(txtColor)
                    .show();
        } else {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }

    }

    public static void setText(Context context, TextView tv,
                               ArrayList<String> str, ArrayList<Integer> color,
                               ArrayList<Float> text_size_list, TextClickListener clickListener) {
        // 累加数组所有的字符串为一个字符串
        StringBuffer long_str = new StringBuffer();
        for (int i = 0; i < str.size(); i++) {
            long_str.append(str.get(i));
        }
        LinkTextUtil linkTextUtil = new LinkTextUtil();
        SpannableStringBuilder builder = linkTextUtil.getSpannableString(long_str.toString());
        // 设置不同字符串的点击事件
        for (int i = 0; i < str.size(); i++) {
            int p = i;
            int star = long_str.toString().indexOf(str.get(i));
            int end = star + str.get(i).length();
            builder.setSpan(new Clickable(clickListener, p), star, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        // 设置不同字符串的颜色
        ArrayList<ForegroundColorSpan> foregroundColorSpans = new ArrayList<ForegroundColorSpan>();
        for (int i = 0; i < color.size(); i++) {
            foregroundColorSpans.add(new ForegroundColorSpan(color.get(i)));
        }
        for (int i = 0; i < str.size(); i++) {
            int star = long_str.toString().indexOf(str.get(i));
            int end = star + str.get(i).length();
            builder.setSpan(foregroundColorSpans.get(i), star, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        builder = linkTextUtil.setUrlString(builder);
        // 设置不同字符串的字号
        ArrayList<AbsoluteSizeSpan> absoluteSizeSpans = new ArrayList<AbsoluteSizeSpan>();
        for (int i = 0; i < color.size(); i++) {
            absoluteSizeSpans.add(new AbsoluteSizeSpan(sp2px(context,
                    text_size_list.get(i))));
        }
        for (int i = 0; i < str.size(); i++) {
            int star = long_str.toString().indexOf(str.get(i));
            int end = star + str.get(i).length();
            builder.setSpan(absoluteSizeSpans.get(i), star, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        // 设置点击后的颜色为透明，否则会一直出现高亮
        tv.setHighlightColor(Color.TRANSPARENT);
        tv.setClickable(true);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setText(builder);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int toInt(String value) {
        if (isEmpty(value)) {
            return 0;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }


    /**
     * 获取字符长度 中文字符按照2个字符计算
     *
     * @param cs 字符
     * @return 字符长度
     */
    public static int getSequenceLength(CharSequence cs) {
        int length = 0;
        if (cs == null || cs.length() == 0) return length;
        char c;
        for (int i = 0; i < cs.length(); i++) {
            c = cs.charAt(i);
            if (isChinese(c)) {
                length += 2;
            } else {
                length++;
            }
        }
        return length;
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 是否为手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNum(String mobiles) {

        if (isBlank(mobiles)) {
            return false;
        }

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,1,5-9])|(17[6，7,8]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    /**
     * 客户端ip
     *
     * @return
     */
    public static String getHostIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr
                        .hasMoreElements(); ) {
                    InetAddress inetAddress = ipAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
