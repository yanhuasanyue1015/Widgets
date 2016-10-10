package richmj.com.details;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 张磊 on 2016/10/10.
 */

public class CirclesView extends RelativeLayout implements ICirclesView {
    //默认显示的最多个数
    private final int DefaultMaxNum = 6;
    //默认每个头像的边长
    private final int DefaultWidthInDp = 40;
    private int ivMaxNum = DefaultMaxNum;
    private int civWidth;
    private int marginRight;
    private int marginleft;


    public CirclesView(Context context) {
        super(context);
    }

    public CirclesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttribute(attrs);
    }

    public CirclesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(attrs);
    }

    private void initAttribute(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.CirclesView);
        try {
            ivMaxNum = typedArray.getInteger(R.styleable.CirclesView_max_num, DefaultMaxNum);
            civWidth = typedArray.getDimensionPixelSize(R.styleable.CirclesView_width, (int) dp2px(DefaultWidthInDp));
            marginRight = civWidth * 3 / 4;
            marginleft = civWidth- marginRight;
        } finally {
            typedArray.recycle();
        }
    }

    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    @Override
    public void setUrls(String... urls) {
        setParentSize(urls.length);
        if (urls.length > ivMaxNum) {
            CircleImageView endView = createEndView();
            RelativeLayout.LayoutParams endViewlayoutParams = (LayoutParams) endView.getLayoutParams();
            endViewlayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
            endView.setLayoutParams(endViewlayoutParams);
            addView(endView);
            for (int i = 0; i < ivMaxNum; i++) {
                CircleImageView cellView = createCellView(urls[i]);
                RelativeLayout.LayoutParams cellViewlayoutParams = (LayoutParams) cellView.getLayoutParams();
                cellViewlayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
                cellViewlayoutParams.setMargins(0, 0, civWidth * 3 / 4 * (i + 1), 0);
                cellView.setLayoutParams(cellViewlayoutParams);
                addView(cellView);
            }
        } else {
            for (int i = 0; i < urls.length; i++) {
                CircleImageView cellView = createCellView(urls[i]);
                RelativeLayout.LayoutParams cellViewlayoutParams = (LayoutParams) cellView.getLayoutParams();
                cellViewlayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
                if (i != 0) {
                    cellViewlayoutParams.setMargins(0, 0, civWidth * 3 / 4 * i, 0);
                }
                cellView.setLayoutParams(cellViewlayoutParams);
                addView(cellView);
            }
        }
    }

    private CircleImageView createCellView(String url) {
        CircleImageView circleImageView = createCiv();
        Picasso.with(getContext()).load(url).resize(civWidth, civWidth).onlyScaleDown().centerInside().into(circleImageView);
        return circleImageView;
    }

    private CircleImageView createEndView() {
        CircleImageView civ = createCiv();
        civ.setImageResource(R.mipmap.more_h);
        return civ;
    }

    private CircleImageView createCiv() {
        CircleImageView circleImageView = new CircleImageView(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(civWidth, civWidth);
        circleImageView.setLayoutParams(layoutParams);
        circleImageView.setBorderWidth((int) dp2px(2));
        circleImageView.setBorderColor(Color.WHITE);
        return circleImageView;
    }

    private void setParentSize(int count) {
        if (count > ivMaxNum) {
//            civWidth
        } else {

        }
    }
}
