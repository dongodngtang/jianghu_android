package net.doyouhike.app.bbs.util.linktext.linkutil;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.util.Linkify;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能：文本链接处理器
 *
 * @author：曾江 日期：16-3-8.
 */
public abstract class BaseLinkUtil {

    public BaseLinkUtil() {

    }

    public SpannableStringBuilder getSpannableString(CharSequence content) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(content);

        ArrayList<LinkSpec> links = new ArrayList<LinkSpec>();


        gatherLinks(links, spannable, getPattern(),
                new String[]{"http://", "https://", "rtsp://"},
                Linkify.sUrlMatchFilter, null);

        for (LinkSpec link : links) {
            applyLink(link, spannable);
        }

        return spannable;
    }

    public SpannableStringBuilder setUrlString(SpannableStringBuilder spannable){
        ArrayList<LinkSpec> links = new ArrayList<LinkSpec>();


        gatherLinks(links, spannable, getPattern(),
                new String[]{"http://", "https://", "rtsp://"},
                Linkify.sUrlMatchFilter, null);

        for (LinkSpec link : links) {
            applyLink(link, spannable);
        }

        return spannable;
    }




    protected abstract void applyLink(LinkSpec link, Spannable text) ;


    private final void gatherLinks(ArrayList<LinkSpec> links,
                                   Spannable s, Pattern pattern, String[] schemes,
                                   Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        Matcher m = pattern.matcher(s);

        while (m.find()) {
            int start = m.start();
            int end = m.end();

            if (matchFilter == null || matchFilter.acceptMatch(s, start, end)) {
                LinkSpec spec = new LinkSpec();
                String url = makeUrl(m.group(0), schemes, m, transformFilter);

                spec.url = url;
                spec.start = start;
                spec.end = end;
                spec.domain=getDomain(url);
                links.add(spec);
            }
        }
    }

    private String getDomain(String url) {

        Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");

        Matcher m = p.matcher(url);

        if (m.find()) {

            System.out.println(m.group());

            return m.group();
        }

        return "";
    }

    private static final String makeUrl(String url, String[] prefixes,
                                        Matcher m, Linkify.TransformFilter filter) {
        if (filter != null) {
            url = filter.transformUrl(m, url);
        }

        boolean hasPrefix = false;

        for (int i = 0; i < prefixes.length; i++) {
            if (url.regionMatches(true, 0, prefixes[i], 0,
                    prefixes[i].length())) {
                hasPrefix = true;

                // Fix capitalization if necessary
                if (!url.regionMatches(false, 0, prefixes[i], 0,
                        prefixes[i].length())) {
                    url = prefixes[i] + url.substring(prefixes[i].length());
                }

                break;
            }
        }

        if (!hasPrefix) {
            url = prefixes[0] + url;
        }

        return url;
    }

    public Pattern getPattern() {
        return Patterns.WEB_URL;
    }


    class LinkSpec {
        String url;
        int start;
        int end;
        String domain;
    }


}
