package com.iel.swsapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iel.swsapp.R;
import com.iel.swsapp.business.NewInfoBiz;

import java.util.List;

/**
 * Class Full Name  : com.iel.swsapp.activity.MoreNewsActivity
 * Author Name      : yxnne
 * Create Time      : 2017/3
 * Project Name     : SWSAPP
 * Descriptions     : 更多新闻，其实是新闻列表
 */
public class MoreNewsActivity extends ActionbarBackBaseActivity {

    private ListView mLvNews;
    private List<NewInfoBiz.NewsInfo> mListInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_news);

        //设置自己的ActionBar
        setMyActionBar(R.id.rl_myactionbar_back,
                R.id.iv_myactionbar_menu,
                R.id.tv_myactionbar_tittle,
                false,getString(R.string.text_app_news));

        //NewsInfo
        mListInfo = NewInfoBiz.getNewsList();

        //ListView
        mLvNews = (ListView) findViewById(R.id.lv_app_news);
        mLvNews.setAdapter(new MyNewsAdapter());

    }
    class MyNewsAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mListInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return mListInfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder ;
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = View.inflate(MoreNewsActivity.this,R.layout.item_list_app_news_info,null);
                viewHolder.tv_tittle = (TextView) convertView.findViewById(R.id.tv_item_news_info_tittle);
                viewHolder.tv_msg = (TextView) convertView.findViewById(R.id.tv_item_news_info_detail);
                viewHolder.tv_msg_time = (TextView) convertView.findViewById(R.id.tv_item_news_info_time);

                viewHolder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_appnews_logo);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_tittle.setText(mListInfo.get(position).getTittle());
            viewHolder.tv_msg.setText(mListInfo.get(position).getTittle());
            viewHolder.tv_msg_time.setText(mListInfo.get(position).getTime());

            viewHolder.iv_logo.setImageResource(mListInfo.get(position).getImgRes());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoreNewsActivity.this,NewsDetailActivity.class);
                    intent.putExtra("url",mListInfo.get(position).getUrl());
                    startActivity(intent);
                }
            });
            return convertView;
        }


        class ViewHolder{

            TextView tv_tittle;
            TextView tv_msg;
            TextView tv_msg_time;
            ImageView iv_logo;
        }

    }

}
