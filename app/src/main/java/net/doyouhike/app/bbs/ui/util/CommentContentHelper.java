package net.doyouhike.app.bbs.ui.util;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.linktext.linkutil.LinkTextUtil;
import net.doyouhike.app.library.ui.widgets.linkify.SmartTextClickMovement;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 设置评论内容格式,工具类
 * Created by zengjiang on 16/6/15.
 */
public class CommentContentHelper {


    public static void setReplyToContent(final TextView textView, final String content, final String replyToNickname, final String replyToUserId) {

        Observable.create(new Observable.OnSubscribe<SpannableStringBuilder>() {
            @Override
            public void call(Subscriber<? super SpannableStringBuilder> subscriber) {
                SpannableStringBuilder spannable;
                if (replyToUserId != null && replyToUserId != "0") {
                    //如果是回复评论
                    spannable = getReplyContent(content, replyToNickname, replyToUserId);
                } else {
                    //发布评论
                    spannable = new LinkTextUtil().getSpannableString(content);
                }
                subscriber.onNext(spannable);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SpannableStringBuilder>() {
                    @Override
                    public void call(SpannableStringBuilder spannableStringBuilder) {
                        textView.setMovementMethod(SmartTextClickMovement.getInstance());
                        textView.setText(spannableStringBuilder);
                    }
                });


    }


    private static SpannableStringBuilder getReplyContent(String content, String replyToUser, final String replyToUserId) {

        if (null == replyToUser) {
            replyToUser = "";
        }

        String str = new StringBuffer().append("回复").append(replyToUser).append(": ").append(content).toString();
        SpannableStringBuilder spannable = new LinkTextUtil().getSpannableString(str);


        int strEnd = 2 + replyToUser.length();

        spannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ActivityRouter.openOtherPageActivity(widget.getContext(), String.valueOf(replyToUserId));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        }, 2, replyToUser.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#BDBDBD")),
                strEnd, strEnd + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#BDBDBD")),
                0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FF48ADA0")),
                2, replyToUser.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannable;
    }
}
