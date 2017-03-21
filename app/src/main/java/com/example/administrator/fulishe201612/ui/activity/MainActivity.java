package com.example.administrator.fulishe201612.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;

import com.example.administrator.fulishe201612.R;
import com.example.administrator.fulishe201612.application.FuLiCenterApplication;
import com.example.administrator.fulishe201612.application.I;
import com.example.administrator.fulishe201612.fragments.Fenlei;
import com.example.administrator.fulishe201612.fragments.Gouwuche;
import com.example.administrator.fulishe201612.fragments.Jingxuan;
import com.example.administrator.fulishe201612.fragments.Wo;
import com.example.administrator.fulishe201612.fragments.Xinpin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPagerAdapter viewPagerAdapter;
    Fragment[] mFragments;

    @BindView(R.id.btnXinpin)
    RadioButton btnXinpin;
    @BindView(R.id.btnJingxuan)
    RadioButton btnJingxuan;
    @BindView(R.id.btnFenlei)
    RadioButton btnFenlei;
    @BindView(R.id.btnGouwuche)
    RadioButton btnGouwuche;
    @BindView(R.id.btnGeren)
    RadioButton btnGeren;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        initData();
        initView();
        setListener();


    }

    Wo wo;

    private void initView() {
        mFragments = new Fragment[5];
        mFragments[0] = new Xinpin();
        mFragments[1] = new Jingxuan();
        mFragments[2] = new Fenlei();
        mFragments[3] = new Gouwuche();
        wo = new Wo();
        mFragments[4] = wo;

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments);
        viewPager.setAdapter(viewPagerAdapter);
    }


    private void initData() {

    }

    private void setListener() {
        btnXinpin.setOnClickListener(this);
        btnJingxuan.setOnClickListener(this);
        btnFenlei.setOnClickListener(this);
        btnGouwuche.setOnClickListener(this);
        btnGeren.setOnClickListener(this);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        btnXinpin.setChecked(true);
                        viewPager.setCurrentItem(0);

                        break;
                    case 1:
                        btnJingxuan.setChecked(true);
                        viewPager.setCurrentItem(1);
                        break;
                    case 2:
                        btnFenlei.setChecked(true);
                        viewPager.setCurrentItem(2);
                        break;
                    case 3:
                        if (FuLiCenterApplication.getUser() == null) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivityForResult(intent, I.REQUEST_CODE_LOGIN_FROM_CART);

                        }
                        btnGouwuche.setChecked(true);
                        viewPager.setCurrentItem(3);
                        break;
                    case 4:
                        if (FuLiCenterApplication.getUser() == null) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivityForResult(intent, I.REQUEST_CODE_LOGIN);
                        }
                        btnGeren.setChecked(true);
                        viewPager.setCurrentItem(4);

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnXinpin:
                viewPager.setCurrentItem(0);
                break;
            case R.id.btnJingxuan:
                viewPager.setCurrentItem(1);
                break;
            case R.id.btnFenlei:
                viewPager.setCurrentItem(2);
                break;
            case R.id.btnGouwuche:
                viewPager.setCurrentItem(3);

                break;
            case R.id.btnGeren:

                viewPager.setCurrentItem(4);
                break;
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        Fragment[] fragments;

        public ViewPagerAdapter(FragmentManager fm, Fragment[] fragments) {

            super(fm);
            this.fragments = fragments;
        }

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_persionlcenter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (FuLiCenterApplication.getUser() != null) {
            if (requestCode == I.REQUEST_CODE_LOGIN) {
                btnGeren.setChecked(true);
                viewPager.setCurrentItem(4);
            }
            if (requestCode == I.REQUEST_CODE_LOGIN_FROM_CART) {
                btnGouwuche.setChecked(true);

                viewPager.setCurrentItem(3);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if(index == 4 && FuLiCenterApplication.getUser()==null){
//            index = 0;
//        }
//        setFragment();
//    }
}
