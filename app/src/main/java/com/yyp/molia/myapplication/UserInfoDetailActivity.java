package com.yyp.molia.myapplication;



import java.util.Date;
import com.yyp.molia.domain.UserInfo;
import com.yyp.molia.service.UserInfoService;
import com.yyp.molia.utils.HttpUtil;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoDetailActivity extends Activity {
    // 声明返回按钮
    private Button btnReturn;
    // 声明用户名控件
    private TextView TV_user_name;
    // 声明登录密码控件
    private TextView TV_password;
    // 声明姓名控件
    private TextView TV_name;
    // 声明性别控件
    private TextView TV_sex;

    // 声明出生日期控件
    private TextView TV_birthday;
    // 声明籍贯控件
    private TextView TV_jiguan;
    // 声明联系电话控件
    private TextView TV_telephone;
    // 声明家庭地址控件
    private TextView TV_address;
    /* 要保存的用户信息 */
    UserInfo userInfo = new UserInfo();
    /* 用户管理业务逻辑层 */
    private UserInfoService userInfoService = new UserInfoService();
    private String user_name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置标题
        setTitle("手机客户端-查看用户详情");
        // 设置当前Activity界面布局
        setContentView(R.layout.userinfo_detail);
        // 通过findViewById方法实例化组件
        btnReturn = (Button) findViewById(R.id.btnReturn);
        TV_user_name = (TextView) findViewById(R.id.TV_user_name);
        TV_password = (TextView) findViewById(R.id.TV_password);
        TV_name = (TextView) findViewById(R.id.TV_name);
        TV_sex = (TextView) findViewById(R.id.TV_sex);
        TV_birthday = (TextView) findViewById(R.id.TV_birthday);
        TV_jiguan = (TextView) findViewById(R.id.TV_jiguan);
        TV_telephone = (TextView) findViewById(R.id.TV_telephone);
        TV_address = (TextView) findViewById(R.id.TV_address);
        Bundle extras = this.getIntent().getExtras();
        user_name = extras.getString("user_name");
        initViewData();
        btnReturn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                UserInfoDetailActivity.this.finish();
            }
        });
    }
    /* 初始化显示详情界面的数据 */
    private void initViewData() {
        userInfo = userInfoService.GetUserInfo(user_name);
        this.TV_user_name.setText(userInfo.getUser_name());
        this.TV_password.setText(userInfo.getPassword());
        this.TV_name.setText(userInfo.getName());
        this.TV_sex.setText(userInfo.getSex());
        byte[] userPhoto_data = null;
        Date birthday = new Date(userInfo.getBirthday().getTime());
        String birthdayStr = (birthday.getYear() + 1900) + "-" + (birthday.getMonth()+1) + "-" + birthday.getDate();
        this.TV_birthday.setText(birthdayStr);
        this.TV_jiguan.setText(userInfo.getJiguan());
        this.TV_telephone.setText(userInfo.getTelephone());
        this.TV_address.setText(userInfo.getAddress());
    }
}
