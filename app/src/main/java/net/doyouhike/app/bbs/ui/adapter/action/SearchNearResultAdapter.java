package net.doyouhike.app.bbs.ui.adapter.action;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.LocationInfo;
import net.doyouhike.app.bbs.util.GetCityIDUtils;

import java.util.List;

/**
 * 搜索附近地点时，搜索结果的列表适配器
 *
 * @author wu-yoline
 */
public class SearchNearResultAdapter extends BaseAdapter {

    private Context context;
    private List<PoiItem> locations;
    private OnClickItemListener onClickItemListener;
    private LayoutInflater inflater;
    private LocationInfo cityInfo;

    public SearchNearResultAdapter(Context context, List<PoiItem> locations,
                                   OnClickItemListener onClickItemListener, LocationInfo cityInfo) {
        this.context = context;
        this.locations = locations;
        this.onClickItemListener = onClickItemListener;
        this.inflater = LayoutInflater.from(context);
        this.cityInfo = cityInfo;
    }

    @Override
    public int getCount() {
        if (locations != null) {
            return locations.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (locations != null && position < locations.size()) {
            return locations.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHandler handler = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_search_near_result,
                    null);
            handler = new ViewHandler();
            handler.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            handler.tvDetail = (TextView) convertView
                    .findViewById(R.id.tv_detail);
            convertView.setTag(handler);
        } else {
            handler = (ViewHandler) convertView.getTag();
        }

        PoiItem poiItem = locations.get(position);
        if (poiItem != null) {
            handler.tvName.setText(poiItem.getTitle());
            handler.tvDetail.setText(poiItem.getSnippet());
            LocationInfo info = getLocationInfo(poiItem);
            setClickItem(info, handler, convertView);
        }

        return convertView;
    }

    private LocationInfo getLocationInfo(PoiItem poiItem) {
        LocationInfo info = new LocationInfo();
        if (cityInfo != null) {
            info.setAltitude(cityInfo.getAltitude());
        }
        info.setCity(poiItem.getCityName());
        info.setCityCode(poiItem.getCityCode());
        info.setLatitude(poiItem.getLatLonPoint().getLatitude());
        if (poiItem.getTitle().equals(poiItem.getCityName())) {
            info.setLocation(poiItem.getCityName());
        } else {
            info.setLocation(poiItem.getCityName() + "·" + poiItem.getTitle());
        }

        info.setLongitude(poiItem.getLatLonPoint().getLongitude());
        info.setPoiId(poiItem.getPoiId());
        int cityId = GetCityIDUtils.getCityID(context, poiItem.getCityName());
        info.setCity_id(cityId + "");
        info.setSnippet(poiItem.getSnippet());
        return info;
    }

    private void setClickItem(final LocationInfo info,
                              final ViewHandler handler, View v) {
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.tvName.setTextColor(0xFF2B96E8);
                handler.tvDetail.setTextColor(0xFF2B96E8);
                onClickItemListener.onclickItem(info);
            }
        });
    }

    public List<PoiItem> getLocations() {
        return locations;
    }

    public void setLocations(List<PoiItem> locations) {
        this.locations = locations;
    }

    public OnClickItemListener getOnClickItemListener() {
        return onClickItemListener;
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public LocationInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(LocationInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    private static class ViewHandler {
        private TextView tvName;
        private TextView tvDetail;
    }

    public interface OnClickItemListener {
        public void onclickItem(LocationInfo info);
    }

}
