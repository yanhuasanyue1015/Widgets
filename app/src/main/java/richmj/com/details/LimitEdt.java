package richmj.com.details;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by 张磊 on 2016/10/12.
 */

public class LimitEdt extends FrameLayout implements ILimitEdt {
    private int NoInt = -10;
    private TextView tvTint;
    private EditText edtContent;
    private String hint;
    private int maxinputcount;

    public LimitEdt(Context context) {
        super(context);
        initView();
    }

    public LimitEdt(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initAttribute(attrs);
    }

    public LimitEdt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAttribute(attrs);
    }

    private void initView() {
        View.inflate(getContext(), R.layout.limitview, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvTint = (TextView) findViewById(R.id.tvTint);
        edtContent = (EditText) findViewById(R.id.edtContent);
        edtContent.setHint(hint);
        edtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (maxinputcount != NoInt) {
                    if (s.length() > maxinputcount) {
                        edtContent.setText(s.subSequence(0, maxinputcount));
                        edtContent.setSelection(edtContent.length());
                    }
                    tvTint.setText(String.format("%d/%d", edtContent.getText().length(), maxinputcount));
                }

            }
        });
        if (maxinputcount == NoInt) {
            tvTint.setVisibility(GONE);
            edtContent.setPadding(edtContent.getPaddingLeft(), edtContent.getPaddingTop(), edtContent.getPaddingRight(), edtContent.getPaddingTop());
        }else{
            tvTint.setText(String.format("%d/%d", edtContent.getText().length(), maxinputcount));
        }
    }

    private void initAttribute(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.LimitEdt);
        try {
            hint = typedArray.getString(R.styleable.LimitEdt_hint);
            maxinputcount = typedArray.getInteger(R.styleable.LimitEdt_maxinputcount, NoInt);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void setText(CharSequence charSequence) {
        if (maxinputcount != NoInt) {
            if (charSequence.length() > maxinputcount) {
                edtContent.setText(charSequence.subSequence(0, maxinputcount));
            }else{
                edtContent.setText(charSequence);
            }
        } else {
            edtContent.setText(charSequence);
        }
    }

    @Override
    public CharSequence getText() {
        return edtContent.getText();
    }
}
