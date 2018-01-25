package net.doyouhike.app.bbs.ui.adapter.live;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.ui.activity.me.ChooseImageActivity;
import net.doyouhike.app.bbs.util.StrUtils;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.util.ArrayList;
import java.util.List;

public class ChooseImageAdapter extends BaseAdapter {

    private Context context;
    private List<String> paths = new ArrayList<String>();
    private int chooseType;

    private int maxChooseCount = 9;


    private OnSelectPicListener onSelectPicListener;

    private List<String> selected;
    private ChooseImageActivity activity;

    public ChooseImageAdapter(Context context, List<String> pathsList,
                              int chooseType, List<String> selected) {
        this.context = context;
        this.paths = pathsList;
        this.chooseType = chooseType;
        this.selected = selected;
        this.activity = (ChooseImageActivity) context;

    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public Object getItem(int position) {
        return paths.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @SuppressLint({"InflateParams", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_choose_image, null);
        }

        RelativeLayout llytChoose = (RelativeLayout) convertView
                .findViewById(R.id.rlyt_choose_image_take);
        ImageView chooseImageIv = (ImageView) convertView
                .findViewById(R.id.iv_choose_image);
        ImageView camera = (ImageView) convertView
                .findViewById(R.id.iv_choose_camera);
        final View chooseImageTakeV = convertView
                .findViewById(R.id.iv_choose_image_take);


        // 项数据显示
        final String pathStr = paths.get(position);
        Glide.clear(chooseImageIv);
        GlideHelper.displayLocalImage(context, chooseImageIv, pathStr);

        if (chooseType == ChooseImageActivity.CHOOSE_TYPE_HEAD) {
            chooseImageTakeV.setVisibility(View.GONE);
            llytChoose.setVisibility(View.GONE);
        } else {
            chooseImageTakeV.setVisibility(View.VISIBLE);
            llytChoose.setVisibility(View.VISIBLE);
        }

        if (checkIsExist(pathStr)) {
            chooseImageTakeV.setSelected(true);
        } else {
            chooseImageTakeV.setSelected(false);
        }
        llytChoose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrRemovePic(pathStr, chooseImageTakeV);
            }
        });


        return convertView;
    }

    public void addPisPata(String path) {
        if (activity.firstCount < maxChooseCount) {
            selected.add(0, path);
        } else {
            StringUtil.showSnack(
                    context,
                    StrUtils.getResourcesStr(context,
                            R.string.can_t_more_9_pics));
        }
    }

    public void addOrRemovePic(String path, View chooseImageTakeV) {

        if (selected == null) {
            selected = new ArrayList<>();
        }
        boolean isExist = selected.remove(path);
        if (!isExist) {
            if (activity.firstCount < maxChooseCount) {
                selected.add(path);
                activity.firstCount++;
                chooseImageTakeV.setSelected(true);
                if (onSelectPicListener != null) {
                    onSelectPicListener.onSelected(path, activity.firstCount);
                }
            } else {
                StringUtil.showSnack(
                        context,
                        StrUtils.getResourcesStr(context,
                                R.string.can_t_more_9_pics));
            }
        } else {
            chooseImageTakeV.setSelected(false);
            activity.firstCount--;
            if (onSelectPicListener != null) {
                onSelectPicListener.onUnselected(path, activity.firstCount);
            }
        }
    }

    public boolean checkIsExist(String path) {
        if (path != null && selected != null) {
            for (int i = 0; i < selected.size(); i++) {
                if (path.equals(selected.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> getSelected() {
        return selected;
    }

    public void setSelected(List<String> selected) {
        this.selected = selected;
    }

    public OnSelectPicListener getOnSelectPicListener() {
        return onSelectPicListener;
    }

    public void setOnSelectPicListener(OnSelectPicListener onSelectPicListener) {
        this.onSelectPicListener = onSelectPicListener;
    }

    public interface OnSelectPicListener {
        void onSelected(String path, int count);

        void onUnselected(String path, int count);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }
}
