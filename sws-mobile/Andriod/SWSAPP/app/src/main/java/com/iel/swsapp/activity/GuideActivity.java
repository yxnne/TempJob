package com.iel.swsapp.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iel.swsapp.R;

import java.util.ArrayList;
import java.util.List;
/**
 * Class Full Name  : com.iel.swsapp.activity.GuideActivity
 * Author Name      : yxnne
 * Create Time      : 2017/3
 * Project Name     : SWSAPP
 * Descriptions     : 引导页，可滑动
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    //ViewPager
    private ViewPager mViewPager;
    //放置ViewPager中的View的
    private List<View> mViewList = new ArrayList<>();

    //indicator little point
    private ImageView mIvIndicatorPoint1;
    private ImageView mIvIndicatorPoint2;
    private ImageView mIvIndicatorPoint3;
    //进入App的按键
    private Button mBtnAccessApp;
    //跳过的TextView
    private TextView mTvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //initDatas();
        initViews();
    }

    private void initViews() {
        //指示器初始化
        mIvIndicatorPoint1 = (ImageView) findViewById(R.id.iv_point_indicator_1);
        mIvIndicatorPoint2 = (ImageView) findViewById(R.id.iv_point_indicator_2);
        mIvIndicatorPoint3 = (ImageView) findViewById(R.id.iv_point_indicator_3);

        //跳过和进入初始化
        mTvSkip = (TextView) findViewById(R.id.tv_skipguide_guide);
        mBtnAccessApp = (Button) findViewById(R.id.btn_access_app_guide);
        //给他们设置监听
        mTvSkip.setOnClickListener(this);
        mBtnAccessApp.setOnClickListener(this);

        //初始化View，向ViewPager里面添加
        View glidePage1 = View.inflate(this,R.layout.page_guide_welcome_1,null);
        View glidePage2 = View.inflate(this,R.layout.page_guide_welcome_2,null);
        View glidePage3 = View.inflate(this,R.layout.page_guide_welcome_3,null);
        mViewList.add(glidePage1);
        mViewList.add(glidePage2);
        mViewList.add(glidePage3);

        //ViewPager
        mViewPager = (ViewPager) findViewById(R.id.vp_guide);

        //ViewPager加上adapter
        mViewPager.setAdapter(new GlideAdapter());
        //监听Viewpager
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 这里要进行更新UI的操作
             * @param position 位置
             */
            @Override
            public void onPageSelected(int position) {
                updateUI(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 根据Viewpager的当前页面更新UI
     * @param position 当前页
     */
    private void updateUI(int position) {
        switch (position){
            case 0:
                mBtnAccessApp.setVisibility(View.GONE);
                //mTvSkip.setVisibility(View.VISIBLE);

                mIvIndicatorPoint1.setImageResource(R.drawable.icon_point_on);
                mIvIndicatorPoint2.setImageResource(R.drawable.icon_point_off);
                mIvIndicatorPoint3.setImageResource(R.drawable.icon_point_off);
                break;
            case 1:
                mBtnAccessApp.setVisibility(View.GONE);
                //mTvSkip.setVisibility(View.VISIBLE);

                mIvIndicatorPoint1.setImageResource(R.drawable.icon_point_off);
                mIvIndicatorPoint2.setImageResource(R.drawable.icon_point_on);
                mIvIndicatorPoint3.setImageResource(R.drawable.icon_point_off);

                break;
            case 2:
                mBtnAccessApp.setVisibility(View.VISIBLE);
                //mTvSkip.setVisibility(View.GONE);

                mIvIndicatorPoint1.setImageResource(R.drawable.icon_point_off);
                mIvIndicatorPoint2.setImageResource(R.drawable.icon_point_off);
                mIvIndicatorPoint3.setImageResource(R.drawable.icon_point_on);

                break;
            default:
                break;
        }
    }

    /**
     * 该Activity实现了OnclickListner的方法
     * @param v 点击事件的View
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 这两个按钮都跳转
            case R.id.tv_skipguide_guide:
                break;
            case R.id.btn_access_app_guide:
                Intent intent = new Intent(GuideActivity.this,
                        LoginActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //test:转场动画 api >= 21
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,mViewPager,"glide_to_main").toBundle());
                }else{
                    startActivity(intent);
                }
                finish();
                break;
        }

    }


    /**
     * ViewPager的适配器
     */
    private class GlideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));

            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }
    }
}
