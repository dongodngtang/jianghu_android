package net.doyouhike.app.bbs.ui.home.tag.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.ui.widget.common.dslv.DragSortListView;

import java.util.List;

/**
 * 功能：
 *
 * @author：曾江 日期：16-2-25.
 */
public class SectionAdapter<T extends BaseTag> extends BaseAdapter implements DragSortListView.DropListener {

    private final static int SECTION_DIV = 0;
    private final static int SECTION_GRAG = 1;
    private final static int SECTION_FIXED = 2;

    private List<T> mDragItems;
    private List<T> mFixItems;

//    private int mDivPos;

    private LayoutInflater mInflater;
    private ViewHolder<T> mViewHolder;

    public SectionAdapter(Context context, List<T> dragItems, List<T> fixedItems) {
        super();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDragItems = dragItems;
        mFixItems = fixedItems;
    }

    @Override
    public void drop(int from, int to) {
        if (from != to) {
            T data = mDragItems.remove(dataPosition(from));
            mDragItems.add(dataPosition(to), data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mDragItems.size() + mFixItems.size() + 1;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != getDivPosition();
    }

    public int getDivPosition() {
        return mDragItems.size();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public T getItem(int position) {

        if (position < getDivPosition()) {
            return mDragItems.get(dataPosition(position));
        }

        if (position > getDivPosition()) {
            return mFixItems.get(dataPosition(position));
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getDivPosition()) {
            return SECTION_DIV;
        } else if (position < getDivPosition()) {
            return SECTION_GRAG;
        } else {
            return SECTION_FIXED;
        }
    }

    private int dataPosition(int position) {
        return position > getDivPosition() ? position - getDivPosition() - 1 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int type = getItemViewType(position);

        View v = null;
        if (convertView != null) {
            v = convertView;

            if (type != SECTION_DIV) {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
        } else if (type == SECTION_GRAG) {
            v = mInflater.inflate(R.layout.item_home_tag_drag, parent, false);
            mViewHolder = new ViewHolder(v, type, lister);
            v.setTag(mViewHolder);

        } else if (type == SECTION_FIXED) {
            v = mInflater.inflate(R.layout.item_home_tag_fixed, parent, false);
            mViewHolder = new ViewHolder(v, type, lister);
            v.setTag(mViewHolder);

        } else {
            v = mInflater.inflate(R.layout.item_home_tag_section_div, parent, false);
        }

        if (type != SECTION_DIV) {
            // bind data
            mViewHolder.tvContent.setText(getItem(position).getTag_name());
            mViewHolder.setTag(type, getItem(position));
        }

        return v;
    }

    ViewHolder.ViewHolderLister lister = new ViewHolder.ViewHolderLister<T>() {
        @Override
        public void addItem(T content) {
            if (mFixItems.contains(content)) {
                mFixItems.remove(content);
            }

            mDragItems.add(content);
            notifyDataSetChanged();
        }

        @Override
        public void delItem(T content) {

            if (mDragItems.contains(content)) {
                mDragItems.remove(content);
            }

            mFixItems.add(0, content);
            notifyDataSetChanged();
        }
    };

    static class ViewHolder<T> {
        TextView tvContent;
        ImageView btnAdd;
        ImageView btnDel;
        ViewHolderLister lister;

        public ViewHolder(View view, int type, final ViewHolderLister lister) {
            this.lister = lister;
            tvContent = (TextView) view.findViewById(R.id.tv_home_tag_content);

            switch (type) {
                case SECTION_GRAG:
                    btnDel = (ImageView) view.findViewById(R.id.btn_item_home_tag_del);
                    btnDel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lister.delItem(v.getTag());
                        }
                    });
                    break;
                case SECTION_FIXED:
                    btnAdd = (ImageView) view.findViewById(R.id.btn_item_home_tag_add);
                    btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lister.addItem(v.getTag());
                        }
                    });
                    break;
            }
        }

        public void setTag(int type, T content) {
            switch (type) {
                case SECTION_GRAG:
                    btnDel.setTag(content);
                    if (null != btnAdd) {
                        btnAdd.setTag(null);
                    }
                    break;
                case SECTION_FIXED:
                    btnAdd.setTag(content);
                    if (null != btnDel) {
                        btnDel.setTag(null);
                    }
                    break;
            }
        }

        public interface ViewHolderLister<T> {

            void addItem(T content);

            void delItem(T content);
        }

    }


}