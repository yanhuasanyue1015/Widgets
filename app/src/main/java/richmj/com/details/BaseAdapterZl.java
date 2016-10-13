package richmj.com.details;

import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public abstract class BaseAdapterZl<T> extends android.widget.BaseAdapter {
  public   List<T> list;

    public BaseAdapterZl(List<T> list) {
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
