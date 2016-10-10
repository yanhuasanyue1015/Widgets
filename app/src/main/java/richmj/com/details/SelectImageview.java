package richmj.com.details;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by 张磊 on 2016/10/10.
 */

public class SelectImageview extends ImageView {
    private int NoResId = -100;
    private Drawable drawableChecked;
    private Drawable drawableUnChecked;
    private final int CheckedState = 1;
    private final int UncheckedState = 2;
    private int CheckState = CheckedState;

    public SelectImageview(Context context) {
        super(context);

    }

    public SelectImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttribute(attrs);
    }

    public SelectImageview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(attrs);
    }

    private void initAttribute(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.SelectImageview);
        try {
            int iv_state_checked = typedArray.getResourceId(R.styleable.SelectImageview_state_checked, NoResId);
            int iv_state_unchecked = typedArray.getResourceId(R.styleable.SelectImageview_state_unchecked, NoResId);
            if (iv_state_checked != NoResId) {
                drawableChecked = ContextCompat.getDrawable(getContext(), iv_state_checked);
            }
            if (iv_state_unchecked != NoResId) {
                drawableUnChecked = ContextCompat.getDrawable(getContext(), iv_state_unchecked);
            }
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (drawableChecked != null && drawableUnChecked != null) {
            setImageDrawable(drawableUnChecked);
            CheckState = UncheckedState;
        }
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawableChecked != null && drawableUnChecked != null) {
                    switch (CheckState) {
                        case CheckedState:
                            setImageDrawable(drawableUnChecked);
                            CheckState = UncheckedState;
                            break;
                        case UncheckedState:
                            setImageDrawable(drawableChecked);
                            CheckState = CheckedState;
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void setOnClickListener(final OnClickListener l) {
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                l.onClick(v);
                if (drawableChecked != null && drawableUnChecked != null) {
                    switch (CheckState) {
                        case CheckedState:
                            setImageDrawable(drawableUnChecked);
                            CheckState = UncheckedState;
                            break;
                        case UncheckedState:
                            setImageDrawable(drawableChecked);
                            CheckState = CheckedState;
                            break;
                    }
                }
            }
        };
        super.setOnClickListener(onClickListener);
    }
}
