package richmj.com.details;

import java.util.List;

/**
 * Created by 张磊 on 2016/10/10.
 */

public interface ICirclesView {
    void setUrls(String... urls);

    void setUrls(List<String> urls);

    int getMaxNum();

    void setCirclesViewClickListener(CirclesViewClickListener circlesViewClickListener);
}
