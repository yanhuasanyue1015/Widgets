package richmj.com.details;

import java.util.List;

/**
 * Created by 张磊 on 2016/10/9.
 */

public interface IPhotosView {
    void setUrls(List<String> urls);

    void setUrls(String ... urls);

    void setOnIteamClickListener(IteamClickListener onIteamClickListener);
}
