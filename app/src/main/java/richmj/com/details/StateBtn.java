package richmj.com.details;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by 张磊 on 2016/10/12.
 */

public class StateBtn extends Button {
    private int Default_Bgcolor_Enable;
    private int Default_Bgcolor_Unable;
    private int Default_Bgcolor_pressed;
    private int Default_Tvcolor_Enable;
    private int Default_Tvcolor_Unable;
    private int Bgcolor_Enable;
    private int Bgcolor_Unable;
    private int Bgcolor_pressed;
    private int Tvcolor_Enable;
    private int Tvcolor_Unable;


    public StateBtn(Context context) {
        super(context);
    }

    public StateBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttribute(attrs);
    }

    public StateBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(attrs);
    }

    private void initAttribute(AttributeSet attributeSet) {
        Default_Bgcolor_Enable = ContextCompat.getColor(getContext(), R.color.blue_base);
        Default_Bgcolor_Unable = ContextCompat.getColor(getContext(), R.color.white);
        Default_Bgcolor_pressed = ContextCompat.getColor(getContext(), R.color.blue_deeper_base);
        Default_Tvcolor_Unable = ContextCompat.getColor(getContext(), R.color.gray);
        Default_Tvcolor_Enable = ContextCompat.getColor(getContext(), R.color.white);
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.StateBtn);
        try {
            Bgcolor_Enable = typedArray.getColor(R.styleable.StateBtn_bgcolor_enable, Default_Bgcolor_Enable);
            Bgcolor_Unable = typedArray.getColor(R.styleable.StateBtn_bgcolor_unable, Default_Bgcolor_Unable);
            Bgcolor_pressed = typedArray.getColor(R.styleable.StateBtn_bgcolor_pressed, Default_Bgcolor_pressed);
            Tvcolor_Enable = typedArray.getColor(R.styleable.StateBtn_tvcolor_enable, Default_Tvcolor_Enable);
            Tvcolor_Unable = typedArray.getColor(R.styleable.StateBtn_tvcolor_unable, Default_Tvcolor_Unable);
        } finally {
            typedArray.recycle();
        }
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed},
                getBgDrawable(Bgcolor_pressed));
        states.addState(new int[]{android.R.attr.state_enabled},
                getBgDrawable(Bgcolor_Enable));
        states.addState(new int[]{},
                getBgDrawable(Bgcolor_Unable));
        setBackgroundDrawable(states);

        int[][] colorstates = new int[][]{
                new int[]{android.R.attr.state_enabled},
                new int[]{},
        };
        int[] colors = {Tvcolor_Enable, Tvcolor_Unable};
        ColorStateList colorStateList = new ColorStateList(colorstates, colors);
        setTextColor(colorStateList);
    }

    private Drawable getBgDrawable(int bgColor) {
        GradientDrawable shap = new GradientDrawable();
        shap.setShape(GradientDrawable.RECTANGLE);
        shap.setCornerRadius(CustomViewUtils.dp2px(4, getContext()));
        shap.setColor(bgColor);
        return shap;
    }
}
