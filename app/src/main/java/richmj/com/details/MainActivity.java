package richmj.com.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    PhotosView photoView;
    private SelectImageview selectiv;
    private de.hdodenhof.circleimageview.CircleImageView civ1;
    private de.hdodenhof.circleimageview.CircleImageView civ2;
    private de.hdodenhof.circleimageview.CircleImageView civ3;
    private de.hdodenhof.circleimageview.CircleImageView civ4;
    private CirclesView civs;
    private android.widget.LinearLayout activitymain;
    private CommentView commentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.activitymain = (LinearLayout) findViewById(R.id.activity_main);
        this.civs = (CirclesView) findViewById(R.id.civs);
        this.civ4 = (CircleImageView) findViewById(R.id.civ4);
        this.civ3 = (CircleImageView) findViewById(R.id.civ3);
        this.civ2 = (CircleImageView) findViewById(R.id.civ2);
        this.civ1 = (CircleImageView) findViewById(R.id.civ1);
        this.selectiv = (SelectImageview) findViewById(R.id.selectiv);
        photoView = (PhotosView) findViewById(R.id.photoView);
        commentView = (CommentView) findViewById(R.id.commentView);
      /*  photoView.setUrls("http://pic5.wed114.cn/20150514/2015051417031316662956.jpg"
        ); */
        photoView.setUrls("http://scimg.jb51.net/allimg/160831/102-160S1164951M1.jpg",
                "http://scimg.jb51.net/allimg/160831/102-160S1164951M1.jpg",
                "http://scimg.jb51.net/allimg/160831/102-160S1164951M1.jpg", "http://scimg.jb51.net/allimg/160831/102-160S1164951M1.jpg"
        );
        commentView.setName("我是帅锅");
        commentView.setUserId(7);
        commentView.setUserAvatar("http://pic5.wed114.cn/20150514/2015051417031316662956.jpg");

//        photoView.setUrls("http://photo.enterdesk.com/2009-4-21/200901241609531378.png");
//        photoView.setUrls("http://img4.imgtn.bdimg.com/it/u=1984460196,3484293998&fm=21&gp=0.jpg", "http://img4.imgtn.bdimg.com/it/u=1984460196,3484293998&fm=21&gp=0.jpg", "http://img4.imgtn.bdimg.com/it/u=1984460196,3484293998&fm=21&gp=0.jpg", "http://img4.imgtn.bdimg.com/it/u=1984460196,3484293998&fm=21&gp=0.jpg", "http://img4.imgtn.bdimg.com/it/u=1984460196,3484293998&fm=21&gp=0.jpg");
//        photoView.setUrls("http://img4.imgtn.bdimg.com/it/u=1984460196,3484293998&fm=21&gp=0.jpg");
        photoView.setOnIteamClickListener(new IteamClickListener() {
            @Override
            public void onClick(View view, int position) {
                showToast(String.format("第%d个", position));
            }
        });
        civs.setUrls(
                "http://img3.imgtn.bdimg.com/it/u=8453116,2287725467&fm=11&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=8453116,2287725467&fm=11&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=8453116,2287725467&fm=11&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=8453116,2287725467&fm=11&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=8453116,2287725467&fm=11&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=8453116,2287725467&fm=11&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=8453116,2287725467&fm=11&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=8453116,2287725467&fm=11&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=8453116,2287725467&fm=11&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=8453116,2287725467&fm=11&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=8453116,2287725467&fm=11&gp=0.jpg"
        );
        civs.setCirclesViewClickListener(new CirclesViewClickListener() {
            @Override
            public void clickDots(View dotsView) {
                showToast("点击了省略号");
            }

            @Override
            public void clickNoDots(View view, int position) {
                showToast("点击了第" + position + "个");
            }
        });
        CommentBean commentBean = new CommentBean();
        commentBean.name = "张磊";
        commentBean.avatar = "http://img3.imgtn.bdimg.com/it/u=8453116,2287725467&fm=11&gp=0.jpg";
        commentBean.comment = "不喜欢你的言论";
        commentBean.date = Calendar.getInstance().getTime();
        commentBean.hasPraised = true;
        commentBean.praiseCount = 100;
        commentBean.idOther = 10;
        commentBean.nameOther = "王二";
        commentBean.idSelf = 11;
        commentView.setSelf(commentBean);
        List<CommentBean> commentBeanList = new ArrayList<>();
        Random random=new Random();

        for (int i = 0; i < 11; i++) {
            CommentBean commentBeanTemp = new CommentBean();
            commentBeanTemp.name = "张磊"+count;
            commentBeanTemp.avatar = "http://img3.imgtn.bdimg.com/it/u=8453116,2287725467&fm=11&gp=0.jpg";
            commentBeanTemp.comment = "不喜欢你的言论";
            Calendar instance = Calendar.getInstance();
            Date time = instance.getTime();
            time.setTime(time.getTime()-random.nextInt(1000000));
            commentBeanTemp.date = time;
            commentBeanTemp.hasPraised = true;
            commentBeanTemp.praiseCount = 100;
            commentBeanTemp.idOther = 11;
            commentBeanTemp.nameOther = "王二";
            commentBeanTemp.idSelf = count;
            commentBeanList.add(commentBeanTemp);
            count++;
        }
        commentView.setOthers(commentBeanList);
    }

    private int count = 12;

    private void showToast(String mes) {
        Toast.makeText(this, mes, Toast.LENGTH_SHORT).show();
    }
}
