package richmj.com.details;

import java.util.List;

/**
 * Created by 张磊 on 2016/10/10.
 */

public interface ICommentView {
    void setSelf(CommentBean commentBean);

    void setOthers(CommentBean[] commentBean);

    void setOthers(List<CommentBean> commentBean);

    void addOther(CommentBean commentBean);
}
