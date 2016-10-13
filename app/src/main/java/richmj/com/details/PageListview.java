package richmj.com.details;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by 张磊 on 2016/10/13.
 * <p>
 * 参考了：
 * http://www.jianshu.com/p/4821a57e7d91
 */

public class PageListview extends ListView implements IPageListview {
    private OnScrollListener onScrollListenerTemp;
    private int mListViewHeight;
    private View endView;
    private PageListviewListener pageListviewListener;
    private TextView tvHint;
    private String hintIsEnd = "已经是最后一页";
    private String hintLoadMore = "正在加载更多...";
    private boolean isLoadingData = false;
    private ProgressBar progressbar;

    public PageListview(Context context) {
        super(context);
        initScrollListener();
        initViewHeight();
        initEndView();
    }

    public PageListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScrollListener();
        initViewHeight();
        initEndView();
    }

    public PageListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScrollListener();
        initViewHeight();
        initEndView();
    }


    private void initViewHeight() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mListViewHeight = getHeight();
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    private void initScrollListener() {
        super.setOnScrollListener(onScrollListener);
    }


    @Override
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListenerTemp = onScrollListener;
    }

    private AbsListView.OnScrollListener onScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (onScrollListenerTemp != null) {
                onScrollListenerTemp.onScrollStateChanged(view, scrollState);
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (onScrollListenerTemp != null) {
                onScrollListenerTemp.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
            if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                View lastVisibleItemView = getChildAt(getChildCount() - 1);
                i(getChildCount() - 1);
                if (lastVisibleItemView != null && lastVisibleItemView.getBottom() >= mListViewHeight) {
                    if (pageListviewListener != null && !isLoadingData) {
                        if (!pageListviewListener.noMoreData(getAdapter().getCount() - 1)) {
                            endView.setVisibility(VISIBLE);
                            tvHint.setText(hintLoadMore);
                            pageListviewListener.end();
                            progressbar.setVisibility(VISIBLE);
                            isLoadingData = true;
                        } else {
                            progressbar.setVisibility(GONE);
                            endView.setVisibility(VISIBLE);
                            tvHint.setText(hintIsEnd);
                        }
                    }
                }
            }
        }
    };

    private void i(int position) {
        Log.i(this.getClass().getName(), String.format("位置：%d", position));
    }

    @Override
    public void setPageListviewListener(PageListviewListener pageListviewListener) {
        this.pageListviewListener = pageListviewListener;
    }

    @Override
    public void completeLoadMore() {
        endView.setVisibility(GONE);
        isLoadingData = false;
    }

    private void initEndView() {
        endView = LayoutInflater.from(getContext()).inflate(R.layout.endview, null);
        tvHint = (TextView) endView.findViewById(R.id.tvHint);
        progressbar = (ProgressBar) endView.findViewById(R.id.progressbar);
        addFooterView(endView);
    }
}
