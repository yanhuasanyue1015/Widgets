package richmj.com.details;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张磊 on 2016/10/9.
 */

public class PhotosView extends FrameLayout implements IPhotosView {
    private List<String> urlList = new ArrayList<>();
    //默认只有一个照片的时候 默认照片宽度
    private final int DefaultMaxwidth_if_oneInDp = 230;
    //默认只有一个照片的时候 默认的照片高度
    private final int DefaultMaxheight_if_oneInDp = 230;
    //多张照片的时候 默认的照片高度
    private final int DefaultHeight_if_multiInDp = 90;
    //多张照片的时候 默认的照片宽度
    private final int DefaultWidth_if_multiInDp = 90;
    //水平默认间隙
    private final int DefaultHorizontal_grapInDp = 5;
    //垂直默认间隙
    private final int DefaultVertical_grapInDp = 5;
    //默认列数
    private final int DefaultColumnCount = 3;
    private final int DeFaultNoSetInt = -1;
    private int maxwidth_if_one;
    private int maxheight_if_one;
    private int height_if_multi;
    private int width_if_multi;
    private int horizontal_grap;
    private int vertical_grap;
    private int column;
    private int maxcount;
    private IteamClickListener onIteamClickListener;

    public PhotosView(Context context) {
        super(context);
        initView();
    }

    public PhotosView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initAttribute(attrs);
    }

    public PhotosView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAttribute(attrs);
    }

    private void initView() {
        View parentView = createParentView();
        addView(parentView);
    }

    private void initAttribute(AttributeSet attribute) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attribute, R.styleable.PhotosView);
        try {
            maxwidth_if_one = typedArray.getDimensionPixelSize(R.styleable.PhotosView_maxwidth_if_one, (int) dp2px(DefaultMaxwidth_if_oneInDp));
            maxheight_if_one = typedArray.getDimensionPixelSize(R.styleable.PhotosView_maxheight_if_one, (int) dp2px(DefaultMaxheight_if_oneInDp));
            height_if_multi = typedArray.getDimensionPixelSize(R.styleable.PhotosView_height_if_multi, (int) dp2px(DefaultHeight_if_multiInDp));
            width_if_multi = typedArray.getDimensionPixelSize(R.styleable.PhotosView_width_if_multi, (int) dp2px(DefaultWidth_if_multiInDp));
            horizontal_grap = typedArray.getDimensionPixelSize(R.styleable.PhotosView_horizontal_grap, (int) dp2px(DefaultHorizontal_grapInDp));
            vertical_grap = typedArray.getDimensionPixelSize(R.styleable.PhotosView_vertical_grap, (int) dp2px(DefaultVertical_grapInDp));
            column = typedArray.getInteger(R.styleable.PhotosView_column, DefaultColumnCount);
            maxcount = typedArray.getInteger(R.styleable.PhotosView_maxcount, DeFaultNoSetInt);
        } finally {
            typedArray.recycle();
        }
    }

    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    private View createParentView() {
        LinearLayout linearLay = new LinearLayout(getContext());
        FrameLayout.LayoutParams layoutParam = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        linearLay.setLayoutParams(layoutParam);
        linearLay.setOrientation(LinearLayout.VERTICAL);
        return linearLay;
    }

    private View createRowView(boolean hasMaragin) {
        LinearLayout linearLay = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (hasMaragin) {
            layoutParam.topMargin = vertical_grap;
        }
        linearLay.setLayoutParams(layoutParam);
        linearLay.setOrientation(LinearLayout.HORIZONTAL);
        return linearLay;
    }

    private ImageView createCellViewIfMulti(boolean hasMaragin, String url) {
        ImageView cellView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (hasMaragin) {
            layoutParams.leftMargin = horizontal_grap;
        }
        cellView.setLayoutParams(layoutParams);
        Picasso.with(getContext()).load(url).resize(width_if_multi, height_if_multi).centerCrop().into(cellView);
        return cellView;
    }

    private ImageView createCellViewIfOne(String path) {
        final ImageView cellView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cellView.setLayoutParams(layoutParams);
        Picasso.with(getContext()).load(path).resize(maxwidth_if_one, maxheight_if_one).onlyScaleDown().centerInside().into(cellView);
        cellView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onIteamClickListener != null) {
                    onIteamClickListener.onClick(cellView, 0);
                }
            }
        });
        return cellView;
    }

    @Override
    public void setUrls(List<String> urls) {
        String[] arrayUrl = new String[urls.size()];
        for (int i = 0; i < urls.size(); i++) {
            arrayUrl[i] = urls.get(i);
        }
        setUrls(arrayUrl);
    }

    @Override
    public void setUrls(String... urls) {
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            temp.add(urls[i]);
        }
        if (maxcount != DeFaultNoSetInt && urls.length > maxcount) {
            while (temp.size() > maxcount) {
                temp.remove(maxcount);
            }
        }
        urlList.clear();
        urlList.addAll(temp);
        View childAt = getChildAt(0);
        ViewGroup viewParent = (ViewGroup) childAt;
        if (childAt instanceof ViewGroup) {
            viewParent = (ViewGroup) childAt;
        }
        if (viewParent == null) {
            return;
        }
        viewParent.removeAllViews();
        if (temp.size() == 1) {
            ImageView cellViewIfOne = createCellViewIfOne(temp.get(0));
            viewParent.addView(cellViewIfOne);
            return;
        } else if (temp == null || temp.size() == 0) {
            return;
        } else {
            viewParent.addView(createRowView(false));
        }
        for (int i = 0; i < temp.size(); i++) {
            final int position = i;
            ViewGroup viewGroupEnd = (ViewGroup) viewParent.getChildAt(viewParent.getChildCount() - 1);
            if (viewGroupEnd.getChildCount() == (column)) {
                viewParent.addView(createRowView(true));
            }
            viewGroupEnd = (ViewGroup) viewParent.getChildAt(viewParent.getChildCount() - 1);
            final ImageView cellViewIfMulti;
            if (viewGroupEnd.getChildCount() == 0) {
                cellViewIfMulti = createCellViewIfMulti(false, temp.get(i));
            } else {
                cellViewIfMulti = createCellViewIfMulti(true, temp.get(i));
            }
            cellViewIfMulti.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onIteamClickListener != null) {
                        onIteamClickListener.onClick(cellViewIfMulti, position);
                    }
                }
            });
            viewGroupEnd.addView(cellViewIfMulti);
        }
    }

    @Override
    public void setOnIteamClickListener(IteamClickListener onIteamClickListener) {
        this.onIteamClickListener = onIteamClickListener;
    }
}
