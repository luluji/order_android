package com.yyp.molia.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyp.molia.utils.Declare;

public class MainMenuUserActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("手机客户端-主界面");
        setContentView(R.layout.main_menu);

        Button btn_userinfo = findViewById(R.id.btn_userinfo);
        Button btn_doctorlist = findViewById(R.id.btn_doctorlist);
        Button btn_orderinfo = findViewById(R.id.btn_orderinfo);

        btn_userinfo.setOnClickListener(userInfoListener);
        btn_doctorlist.setOnClickListener(doctorListener);
        btn_orderinfo.setOnClickListener(orderInfoListener);

   }

    OnClickListener userInfoListener = new OnClickListener() {
        public void onClick(View v) {
            Declare declare = (Declare) MainMenuUserActivity.this.getApplication();
            // 获取用户名
            String user_name = declare.getUserName();
            Intent intent = new Intent();
            intent.setClass(MainMenuUserActivity.this, UserInfoDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("user_name", user_name);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    OnClickListener doctorListener = new OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
             //启动医生管理Activity
            intent.setClass(MainMenuUserActivity.this, DoctorListActivity.class);
            startActivity(intent);
        }
    };
    OnClickListener orderInfoListener = new OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            // 启动预约管理Activity
            intent.setClass(MainMenuUserActivity.this, OrderInfoDetailActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.add(0, 1, 1, "重新登入");
        menu.add(0, 2, 2, "退出");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == 1) {//重新登入
            Intent intent = new Intent();
            intent.setClass(MainMenuUserActivity.this,
                    MainActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == 2) {//退出
            System.exit(0);
        }
        return true;
    }
}

