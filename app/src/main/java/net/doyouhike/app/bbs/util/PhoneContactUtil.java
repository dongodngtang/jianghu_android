/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: PhoneContactUtil.java
 * Author: Zhangkai
 * Version: 1.0
 * Create: 2015-11-03
 *
 */

package net.doyouhike.app.bbs.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.entity.PhoneContactInfo;

import java.util.ArrayList;

public class PhoneContactUtil {

    public static ArrayList<PhoneContactInfo> getConatctList(Context context) {
        final String[] PHONES_PROJECTION = new String[]{Phone.DISPLAY_NAME,
                Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID};
        ArrayList<PhoneContactInfo> contactList = new ArrayList<PhoneContactInfo>();

        try {
            final int PHONES_DISPLAY_NAME_INDEX = 0;
            final int PHONES_NUMBER_INDEX = 1;
            final int PHONES_PHOTO_ID_INDEX = 2;
            final int PHONES_CONTACT_ID_INDEX = 3;

            //检查 是否 具有 读取联系人 权限
            if (!checkPermission("android.permission.READ_CONTACTS"))
                return null;
            // 获取手机联系人
            Cursor cur = context.getContentResolver().query(Phone.CONTENT_URI,
                    PHONES_PROJECTION, null, null, null);

            if (cur == null) {
                return null;
            }


            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                // 得到手机号码
                String phoneNumber = cur.getString(PHONES_NUMBER_INDEX);

                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber) || phoneNumber.startsWith("0")) {
                    continue;
                }

                // 去除手机号码中间的所有空格
                phoneNumber = phoneNumber.replaceAll(" ", "");
                phoneNumber = phoneNumber.replaceAll("^((\\+{0,1}86){0,1})", "");

                // 得到联系人名称
                String contactName = cur.getString(PHONES_DISPLAY_NAME_INDEX);

                // 得到联系人ID
                Long contactid = cur.getLong(PHONES_CONTACT_ID_INDEX);

                // 得到联系人头像ID
                Long photoid = cur.getLong(PHONES_PHOTO_ID_INDEX);

                PhoneContactInfo info = new PhoneContactInfo();
                info.name = contactName;
                info.phoneNum = phoneNumber;
                info.contactId = contactid;
                info.photoId = photoid;

                contactList.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        return contactList;
    }

    /**
     * TODO: 检查 应用 是否 具有某项 权限
     * 日期: 3/24/16
     *
     * @author zhulin
     */
    public static boolean checkPermission(String permission) {
        boolean result = false;
        try {
            PackageManager pm = MyApplication.getInstance().getPackageManager();
            result = (PackageManager.PERMISSION_GRANTED ==
                    pm.checkPermission(permission, MyApplication.getInstance().getPackageName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
