package richmj.com.details;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 张磊 on 2016/10/10.
 */

public class CommentView extends FrameLayout implements ICommentView {
    private ViewHolder viewHolder;

    public CommentView(Context context) {
        super(context);
        initView();
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        View.inflate(getContext(), R.layout.commentlay, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        viewHolder = new ViewHolder(this);
    }

    @Override
    public void setSelf(CommentBean commentBean) {
        viewHolder.setData(commentBean);
    }

    @Override
    public void setOthers(CommentBean[] commentBean) {
        viewHolder.setOthers(commentBean);
    }

    @Override
    public void setOthers(List<CommentBean> commentBean) {
        viewHolder.setOthers(commentBean);
    }

    @Override
    public void addOther(CommentBean commentBean) {
        viewHolder.addOther(commentBean);
    }

    public static class ViewHolder implements ICommentView {
        private TextView tvTimeTitle;
        private TextView tvNameTitle;
        private CircleImageView civAvater;
        private TextView tvCotentTitle;
        private ImageView ivAddCotentTitle;
        private LinearLayout layAddPraise;
        private TextView tvCommentCount;
        private CheckBox checkboxSelect;
        private View view;
        private TextView tvPraiseCount;
        private final int commentcivwidth;
        private final SelectImageview selectivPraise;

        public ViewHolder(View view) {
            this.view = view;
            tvTimeTitle = (TextView) view.findViewById(R.id.tvTimeTitle);
            tvNameTitle = (TextView) view.findViewById(R.id.tvNameTitle);
            civAvater = (CircleImageView) view.findViewById(R.id.civAvater);
            tvCotentTitle = (TextView) view.findViewById(R.id.tvCotentTitle);
            ivAddCotentTitle = (ImageView) view.findViewById(R.id.ivAddCotentTitle);
            layAddPraise = (LinearLayout) view.findViewById(R.id.layAddPraise);
            tvCommentCount = (TextView) view.findViewById(R.id.tvCommentCount);
            checkboxSelect = (CheckBox) view.findViewById(R.id.checkboxSelect);
            tvPraiseCount = (TextView) view.findViewById(R.id.tvPraiseCount);
            selectivPraise = (SelectImageview) view.findViewById(R.id.selectivPraise);
            commentcivwidth = view.getContext().getResources().getDimensionPixelSize(R.dimen.commentcivwidth);
        }

        public void setData(CommentBean data) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
            tvTimeTitle.setText(simpleDateFormat.format(data));
            tvNameTitle.setText(data.name);
            Picasso.with(view.getContext()).load(data.avatar).resize(commentcivwidth, commentcivwidth).onlyScaleDown().into(civAvater);
            tvCotentTitle.setText(data.comment);
            tvPraiseCount.setText(data.praiseCount + "");
            selectivPraise.setCheckedState(data.hasPraised);
        }

        @Override
        public void setSelf(CommentBean commentBean) {

        }

        @Override
        public void setOthers(CommentBean[] commentBean) {

        }

        @Override
        public void setOthers(List<CommentBean> commentBean) {

        }

        @Override
        public void addOther(CommentBean commentBean) {

        }

        private OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewId = v.getId();
                if (viewId == R.id.ivAddCotentTitle) {
                } else if (viewId == R.id.layAddPraise) {

                }
            }
        };
    }
}
