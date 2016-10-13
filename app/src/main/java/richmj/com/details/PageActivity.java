package richmj.com.details;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PageActivity extends AppCompatActivity {
    private final int AddData = 1;
    private PageListview pageLv;
    private android.widget.RelativeLayout activitymain2;
    private List<String> container = new ArrayList<>();
    private BaseAdapterZlHigh<String> adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case AddData:
                    for (int i = 0; i < 20; i++) {
                        container.add(String.format("第%d个", container.size() + 1));
                    }
                    adapter.notifyDataSetChanged();
                    pageLv.completeLoadMore();
                    break;
            }
        }
    };

    {
        for (int i = 0; i < 100; i++) {
            container.add(String.format("第%d个", i));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.activitymain2 = (RelativeLayout) findViewById(R.id.activity_main2);
        this.pageLv = (PageListview) findViewById(R.id.pageLv);
        adapter = new BaseAdapterZlHigh<String>(container) {

            @Override
            protected AbsViewHolder<String> createViewHolder(Context context) {
                return new AbsViewHolder<String>(R.layout.iteam_tv, context) {
                    private TextView tvContent;

                    @Override
                    protected void onBindViews() {
                        super.onBindViews();
                        tvContent = (TextView) findViewById(R.id.tvIteam);
                    }

                    @Override
                    protected void bindView(int position, String iteamData) {
                        tvContent.setText(iteamData);
                    }
                };
            }
        };
        pageLv.setAdapter(adapter);
        pageLv.setPageListviewListener(new PageListviewListener() {
            @Override
            public boolean noMoreData(int position) {
                if (position >= 600) {
                    return true;
                }
                return false;
            }

            @Override
            public void end() {
                i(pageLv.getChildCount()-1);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(AddData);
                    }
                });
                thread.start();
                showToast("到达最底部了");
            }
        });
    }

    private void showToast(String mes) {
        Toast.makeText(this, mes, Toast.LENGTH_SHORT).show();
    }

    private void i(int postion) {
        Log.i("位置", String.format("当前的位置:%d", postion));
    }
}
