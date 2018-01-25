package net.doyouhike.app.library.ui.widgets.linkify;

/**
 * 作者：luochangdong on 16/8/18
 * 描述：
 */
public interface SmartTextCallback {
    void hashTagClick(String hashTag);
    void mentionClick(String mention);
    void emailClick(String email);
    void phoneNumberClick(String phoneNumber);
    void webUrlClick(String webUrl);
}
