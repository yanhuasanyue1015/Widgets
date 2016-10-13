package richmj.com.details;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by 张磊 on 2016/9/20.
 */
public abstract class AbsViewHolder<T> {
    public View view;
    protected Context context;
    protected int position;
    protected T iteamData;

    public AbsViewHolder(int res, Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(res, null);
        onBindViews();
    }

    public AbsViewHolder(View view) {
        this.view = view;
        this.context = view.getContext();
        onBindViews();
    }

    protected void onBindViews() {
    }

    protected View findViewById(@IdRes int resID) {
        return view.findViewById(resID);
    }

    public void setIteam(int position, T iteamData) {
        this.position = position;
        this.iteamData = iteamData;
    }

    protected abstract void bindView(int position, T iteamData);
}
