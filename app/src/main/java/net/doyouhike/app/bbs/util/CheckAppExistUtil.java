package net.doyouhike.app.bbs.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnClickL;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.ui.util.TipUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZouWenJie
 * 检测某款app是否安装
 */
public class CheckAppExistUtil {
   /**
 * @param context
 * @param packageName
 * @return
 * 检测是否安装某款app
 */
public static boolean checkAppExist(Context context,String packageName){
	   PackageManager pm = context.getPackageManager();
	   List<PackageInfo> pInfo = pm.getInstalledPackages(0);
	   List<String> pName=new ArrayList<String>();
	   if(pInfo!=null){
		   for (int i = 0; i < pInfo.size(); i++) {
			String pn=pInfo.get(i).packageName;
			pName.add(pn);
		}
	   }
	   return pName.contains(packageName);
   }

   public static void showAlertDialog(Context context){


	   TipUtil.alert(context
			   , context.getString(R.string.wechat_notice)
			   , context.getString(R.string.wechat_not_install));

   }
}
