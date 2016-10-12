package richmj.com.details;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

    @Override
    public void setUserId(int userId) {
        viewHolder.setUserId(userId);
    }

    @Override
    public void setUserAvatar(String url) {
        viewHolder.setUserAvatar(url);
    }

    @Override
    public void setName(String name) {
        viewHolder.setName(name);
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
        private int userId;
        private String avatarUrl;
        private String name;
        private final LinearLayout rlayFold;
        private TimeUtil timeUtil = new TimeUtil();

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
            rlayFold = (LinearLayout) view.findViewById(R.id.rlayFold);
            commentcivwidth = view.getContext().getResources().getDimensionPixelSize(R.dimen.commentcivwidth);
            ivAddCotentTitle.setOnClickListener(onClickListener);
            rlayFold.setOnClickListener(onClickListener);
            checkboxSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        layContentContainer.animate().translationY(-(layContentContainer.getHeight()));
                    } else {
                        layContentContainer.animate().translationY(0);
                    }
                }
            });

        }


        @Override
        public void setSelf(CommentBean data) {
            this.dataSelf = data;
            try {
                tvTimeTitle.setText(timeUtil.getIntervalTimeFromCreateTime(data.date));
            } catch (ParseException e) {
                e.printStackTrace();
                tvTimeTitle.setText("");
            }
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
        public void setINetOfCommentView(INetOfCommentView iNetOfCommentView) {
            this.iNetOfCommentView = iNetOfCommentView;
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
            tvCommentCount.setText(String.format("评论 (%d条)", commentBeanList.size()));
        }

        private View createCommentIteamView(final CommentBean commentBean) {
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
            try {
                tvTime.setText(timeUtil.getIntervalTimeFromCreateTime(commentBean.date));
            } catch (ParseException e) {
                tvTime.setText("");
                e.printStackTrace();
            }
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
            iteamView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commentBean.idSelf != userId) {
                        createDialog(commentBean).show();
                    }
                }
            });
            return iteamView;
        }

        @Override
        public void addOther(CommentBean commentBean) {
            if (commentBean.idOther == dataSelf.idSelf) {
                commentBeanList.add(commentBean);
                layContentContainer.addView(createCommentIteamView(commentBean));
                tvCommentCount.setText(String.format("评论 (%d)条", commentBeanList.size()));
            } else {
                for (int i = 0; i < commentBeanList.size(); i++) {
                    CommentBean iteam = commentBeanList.get(i);
                    if (dataSelf.idSelf == iteam.idOther && iteam.idSelf == commentBean.idOther) {
                        for (int j = i + 1; j < commentBeanList.size(); j++) {
                            CommentBean iteam2 = commentBeanList.get(i);
                            if (iteam2.idOther == dataSelf.idSelf) {
                                commentBeanList.add(j, commentBean);
                                layContentContainer.addView(createCommentIteamView(commentBean), j);
                                tvCommentCount.setText(String.format("评论 (%d)条", commentBeanList.size()));
                                return;
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void setUserId(int userId) {
            this.userId = userId;
        }

        @Override
        public void setUserAvatar(String url) {
            this.avatarUrl = url;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        private OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewId = v.getId();
                if (viewId == R.id.ivAddCotentTitle) {
                    createDialog(dataSelf).show();
                } else if (viewId == R.id.rlayFold) {
                    checkboxSelect.setChecked(!checkboxSelect.isChecked());
                }
            }
        };
        private Dialog alertDialog;
        CommentBean temp;

        private Dialog createDialog(CommentBean commentBean) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            View limitedtLay = LayoutInflater.from(view.getContext()).inflate(R.layout.addcommentlay, null);
            final LimitEdt limitedtContent = (LimitEdt) limitedtLay.findViewById(R.id.limitedtContent);
            StateBtn btnSend = (StateBtn) limitedtLay.findViewById(R.id.btnSend);
            builder.setView(limitedtLay);
            if (alertDialog == null) {
                alertDialog = builder.create();
            }
            temp = commentBean;
            btnSend.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    addOther(createNewCommentBean(temp, limitedtContent.getText().toString()));
                    ((Activity) (view.getContext())).getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );
                    limitedtContent.setText("");
                    alertDialog.dismiss();
                }
            });
            return alertDialog;
        }

        private CommentBean createNewCommentBean(CommentBean commentBean, String content) {
            CommentBean commentBeanNew = new CommentBean();
            commentBeanNew.idSelf = userId;
            commentBeanNew.idOther = commentBean.idSelf;
            commentBeanNew.avatar = avatarUrl;
            commentBeanNew.comment = content;
            commentBeanNew.date = Calendar.getInstance().getTime();
            commentBeanNew.hasPraised = false;
            commentBeanNew.praiseCount = 0;
            commentBeanNew.nameOther = commentBean.name;
            commentBeanNew.name = name;
            return commentBeanNew;
        }

    }

}
