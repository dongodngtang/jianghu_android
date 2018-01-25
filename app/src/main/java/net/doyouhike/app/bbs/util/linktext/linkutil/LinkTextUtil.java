package net.doyouhike.app.bbs.util.linktext.linkutil;

import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StyleSpan;

import net.doyouhike.app.bbs.util.linktext.UrlClickableSpan;
import net.doyouhike.app.library.ui.widgets.linkify.URLSpanNoUnderline;

import java.util.regex.Pattern;

/**
 * 功能：直播 评论的链接处理
 *
 * @author：曾江 日期：16-3-8.
 */
public class LinkTextUtil extends BaseLinkUtil {
    @Override
    protected void applyLink(LinkSpec link, Spannable text) {

        UrlClickableSpan imageSpan = new UrlClickableSpan(link.url);
        text.setSpan(imageSpan, link.start, link.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        text.setSpan(new URLSpanNoUnderline(link.url), link.start, link.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


    @Override
    public Pattern getPattern() {
        return Pattern.compile("(?:(?:[a-zA-z]+://){1}|(?:(?:W|w){3}\\.))[a-zA-Z0-9]*\\.[a-zA-Z0-9\\.?/\\-;:@#%^_~=+,<>&$]*");
//        return Pattern.compile("(?:(?:[a-zA-z]+://){1}|(?:(?:W|w){3}\\.))[a-z,A-Z,0-9]*\\.(?:(?:[a-z,A-Z,0-9]*)|(?:[a-z,A-Z,0-9]*/[a-z|A-Z|0-9|\\._/?:@^~#%&=+$]*))");
    }
}
