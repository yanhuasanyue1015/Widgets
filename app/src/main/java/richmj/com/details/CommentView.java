package richmj.com.details;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
        viewHolder.setSelf(commentBean);
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

    @Override
    public void setINetOfCommentView(INetOfCommentView iNetOfCommentView) {
        viewHolder.setINetOfCommentView(iNetOfCommentView);
    }

    public static class ViewHolder implements ICommentView {
        private TextView tvTimeTitle;
        private TextView tvNameTitle;
        private CircleImageView civAvater;
        private TextView tvCotentTitle;
        private ImageView ivAddCotentTitle;
        private TextView tvCommentCount;
        private CheckBox checkboxSelect;
        private View view;
        private TextView tvPraiseCount;
        private LinearLayout layContentContainer;
        private final int commentcivwidth;
        private final SelectImageview selectivPraise;
        private CommentBean dataSelf;
        private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        private List<CommentBean> commentBeanList;
        private INetOfCommentView iNetOfCommentView;
        private final Dialog dialog;

        public ViewHolder(View view) {
            this.view = view;
            tvTimeTitle = (TextView) view.findViewById(R.id.tvTimeTitle);
            tvNameTitle = (TextView) view.findViewById(R.id.tvNameTitle);
            civAvater = (CircleImageView) view.findViewById(R.id.civAvater);
            tvCotentTitle = (TextView) view.findViewById(R.id.tvCotentTitle);
            ivAddCotentTitle = (ImageView) view.findViewById(R.id.ivAddCotentTitle);
            tvCommentCount = (TextView) view.findViewById(R.id.tvCommentCount);
            checkboxSelect = (CheckBox) view.findViewById(R.id.checkboxSelect);
            tvPraiseCount = (TextView) view.findViewById(R.id.tvPraiseCount);
            selectivPraise = (SelectImageview) view.findViewById(R.id.selectivPraise);
            layContentContainer = (LinearLayout) view.findViewById(R.id.layContentContainer);
            commentcivwidth = view.getContext().getResources().getDimensionPixelSize(R.dimen.commentcivwidth);
            ivAddCotentTitle.setOnClickListener(onClickListener);
            checkboxSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        layContentContainer.setVisibility(VISIBLE);
                    } else {
                        layContentContainer.setVisibility(GONE);
                    }
                }
            });
            dialog = createDialog();
        }


        @Override
        public void setSelf(CommentBean data) {
            this.dataSelf = data;
            tvTimeTitle.setText(simpleDateFormat.format(data.date));
            tvNameTitle.setText(data.name);
            Picasso.with(view.getContext()).load(data.avatar).resize(commentcivwidth, commentcivwidth).onlyScaleDown().into(civAvater);
            tvCotentTitle.setText(data.comment);
            tvPraiseCount.setText(data.praiseCount + "");
            selectivPraise.setCheckedState(data.hasPraised);
            selectivPraise.setStateChanggeListener(new ISelectViewStateChanggeListener() {
                @Override
                public void changgeTo(boolean checked) {
                    if (checked) {
                        dataSelf.praiseCount += 1;
                        dataSelf.hasPraised = true;
                    } else {
                        dataSelf.praiseCount -= 1;
                        dataSelf.hasPraised = false;
                    }
                    tvPraiseCount.setText(dataSelf.praiseCount + "");
                    if (iNetOfCommentView != null) {
                        iNetOfCommentView.updata(dataSelf);
                    }
                }
            });
        }

        @Override
        public void setOthers(CommentBean[] commentBeans) {
            List<CommentBean> commentBeanList = new ArrayList<>();
            commentBeanList.addAll(Arrays.asList(commentBeans));
            setOthers(commentBeanList);
        }

        @Override
        public void setOthers(List<CommentBean> commentBeans) {
            this.commentBeanList = commentBeans;
            layContentContainer.removeAllViews();
            List<CommentBean> tempList = new ArrayList<>();
            Iterator<CommentBean> iterator = commentBeans.iterator();
            while (iterator.hasNext()) {
                CommentBean next = iterator.next();
                if (next.idOther != dataSelf.idSelf) {
                    tempList.add(next);
                    iterator.remove();
                }
            }
            if (tempList.size() != 0) {
                for (int i = tempList.size() - 1; i >= 0; i++) {
                    CommentBean commentBeanTemp = tempList.get(i);
                    for (int j = 0; j < commentBeans.size(); j++) {
                        CommentBean commentBeanTemp2 = commentBeans.get(j);
                        if (commentBeanTemp2.idSelf == commentBeanTemp.idOther) {
                            commentBeans.add(j + 1, commentBeanTemp);
                            break;
                        }
                    }
                }
            }
            for (CommentBean iteam : commentBeans) {
                layContentContainer.addView(createCommentIteamView(iteam));
            }
            tvCommentCount.setText(String.format("评论 (%d)条", commentBeanList.size()));
        }

        private View createCommentIteamView(CommentBean commentBean) {
            View iteamView = LayoutInflater.from(view.getContext()).inflate(R.layout.iteam_comment, null);
            if (layContentContainer.getChildCount() != 0) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.topMargin = (int) CustomViewUtils.dp2px(10, view.getContext());
                iteamView.setLayoutParams(layoutParams);
            }
            CircleImageView civAvater = (CircleImageView) iteamView.findViewById(R.id.civAvater);
            TextView tvName = (TextView) iteamView.findViewById(R.id.tvName);
            TextView tvTime = (TextView) iteamView.findViewById(R.id.tvTime);
            TextView tvTo = (TextView) iteamView.findViewById(R.id.tvTo);
            TextView tvToWho = (TextView) iteamView.findViewById(R.id.tvToWho);
            TextView tvContent = (TextView) iteamView.findViewById(R.id.tvContent);
            TextView tvdot = (TextView) iteamView.findViewById(R.id.tvdot);
            Picasso.with(view.getContext()).load(commentBean.avatar).resize(commentcivwidth, commentcivwidth).onlyScaleDown().into(civAvater);
            tvName.setText(commentBean.name);
            tvTime.setText(simpleDateFormat.format(commentBean.date));
            if (commentBean.idOther != dataSelf.idSelf) {
                tvTo.setVisibility(VISIBLE);
                tvToWho.setVisibility(VISIBLE);
                tvdot.setVisibility(VISIBLE);
                tvToWho.setText(commentBean.nameOther);
            } else {
                tvTo.setVisibility(GONE);
                tvToWho.setVisibility(GONE);
                tvdot.setVisibility(GONE);
            }
            tvContent.setText(commentBean.comment);
            return iteamView;
        }

        @Override
        public void addOther(CommentBean commentBean) {
            if (commentBean.idOther == dataSelf.idSelf) {
                commentBeanList.add(commentBean);
                layContentContainer.addView(createCommentIteamView(commentBean));
            } else {
                for (int i = 0; i < commentBeanList.size(); i++) {
                    CommentBean iteam = commentBeanList.get(i);
                    if (iteam.idOther == commentBean.idSelf) {
                        for (int j = i + 1; j < commentBeanList.size(); j++) {
                            CommentBean iteam2 = commentBeanList.get(i);
                            if (iteam2.idOther == dataSelf.idSelf) {
                                commentBeanList.add(j, commentBean);
                                layContentContainer.addView(createCommentIteamView(commentBean), j);
                            }
                        }
                    }
                }
            }
            tvCommentCount.setText(String.format("评论 (%d)条", commentBeanList.size()));
        }

        @Override
        public void setINetOfCommentView(INetOfCommentView iNetOfCommentView) {
            this.iNetOfCommentView = iNetOfCommentView;
        }

        private OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewId = v.getId();
                if (viewId == R.id.ivAddCotentTitle) {
                    dialog.show();
                }
            }
        };

        private Dialog createDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            View limitedtLay = LayoutInflater.from(view.getContext()).inflate(R.layout.addcommentlay, null);
            LimitEdt limitedtContent = (LimitEdt) limitedtLay.findViewById(R.id.limitedtContent);
            Button btnSend = (Button) limitedtLay.findViewById(R.id.btnSend);
            builder.setView(limitedtLay);
            final AlertDialog alertDialog = builder.create();
            btnSend.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            return alertDialog;
        }
    }
}
