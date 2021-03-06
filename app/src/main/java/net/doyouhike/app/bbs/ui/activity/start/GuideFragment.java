package net.doyouhike.app.bbs.ui.activity.start;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：luochangdong on 16/8/25
 * 描述：通用引导页
 */
public class GuideFragment extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    private int layoutResId;

    private View.OnClickListener nextListener;

    public void setNextListener(View.OnClickListener nextListener){
        this.nextListener = nextListener;
    }

    public static GuideFragment newInstance(int layoutResId) {
        GuideFragment sampleSlide = new GuideFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID)) {
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutResId, container, false);
        view.setOnClickListener(nextListener);

        return view;
    }
}
