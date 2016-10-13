package richmj.com.details;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 张磊 on 2016/9/20.
 */
public abstract class BaseAdapterZlHigh<T> extends BaseAdapterZl<T> {

    public BaseAdapterZlHigh(List<T> list) {
        super(list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AbsViewHolder<T> iViewHolder;
        if (convertView == null) {
            iViewHolder = createViewHolder(parent.getContext());
            convertView = iViewHolder.view;
            convertView.setTag(iViewHolder);
        } else {
            iViewHolder = (AbsViewHolder<T>) convertView.getTag();
            convertView = iViewHolder.view;
        }
        iViewHolder.setIteam(position, list.get(position));
        iViewHolder.bindView(position, list.get(position));
        return convertView;
    }

    protected abstract AbsViewHolder<T> createViewHolder(Context context);
}
