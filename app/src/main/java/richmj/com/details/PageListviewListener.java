package richmj.com.details;

/**
 * Created by 张磊 on 2016/10/13.
 */

public interface PageListviewListener {
    boolean noMoreData(int position);

    void end();
}
