package richmj.com.details;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by 张磊 on 2016/10/11.
 */

public class CustomViewUtils {
    public static float dp2px(float dp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
