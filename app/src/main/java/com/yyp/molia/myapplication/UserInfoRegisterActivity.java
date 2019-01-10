package com.yyp.molia.myapplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

import com.yyp.molia.domain.UserInfo;
import com.yyp.molia.service.UserInfoService;
import com.yyp.molia.utils.HttpUtil;

public class UserInfoRegisterActivity extends Activity {
    // 声明确定添加按钮
    private Button btnAdd;
    // 声明用户名输入框
    private EditText ET_user_name;
    // 声明登录密码输入框
    private EditText ET_password;
    // 声明姓名输入框
    private EditText ET_name;
    // 声明性别输入框
    private EditText ET_sex;
    // 声明用户照片图片框控件
//    private ImageView iv_userPhoto;
//    private Button btn_userPhoto;
    protected int REQ_CODE_SELECT_IMAGE_userPhoto = 1;
    private int REQ_CODE_CAMERA_userPhoto = 2;
    // 出版出生日期控件
    private DatePicker dp_birthday;
    // 声明籍贯输入框
    private EditText ET_jiguan;
    // 声明联系电话输入框
    private EditText ET_telephone;
    // 声明家庭地址输入框
    private EditText ET_address;
    protected String carmera_path;
    /*要保存的用户信息*/
    UserInfo userInfo = new UserInfo();
    /*用户管理业务逻辑层*/
    private UserInfoService userInfoService = new UserInfoService();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置标题
        setTitle("手机客户端-添加用户");
        // 设置当前Activity界面布局
        setContentView(R.layout.userinfo_add);
        ET_user_name = (EditText) findViewById(R.id.ET_user_name);
        ET_password = (EditText) findViewById(R.id.ET_password);
        ET_name = (EditText) findViewById(R.id.ET_name);
        ET_sex = (EditText) findViewById(R.id.ET_sex);

        dp_birthday = (DatePicker)this.findViewById(R.id.dp_birthday);
        ET_jiguan = (EditText) findViewById(R.id.ET_jiguan);
        ET_telephone = (EditText) findViewById(R.id.ET_telephone);
        ET_address = (EditText) findViewById(R.id.ET_address);
        btnAdd = (Button) findViewById(R.id.BtnAdd);
        /*单击添加用户按钮*/
        btnAdd.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    /*验证获取用户名*/
                    if(ET_user_name.getText().toString().equals("")) {
                        Toast.makeText(UserInfoRegisterActivity.this, "用户名输入不能为空!", Toast.LENGTH_LONG).show();
                        ET_user_name.setFocusable(true);
                        ET_user_name.requestFocus();
                        return;
                    }
                    userInfo.setUser_name(ET_user_name.getText().toString());
                    /*验证获取登录密码*/
                    if(ET_password.getText().toString().equals("")) {
                        Toast.makeText(UserInfoRegisterActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
                        ET_password.setFocusable(true);
                        ET_password.requestFocus();
                        return;
                    }
                    userInfo.setPassword(ET_password.getText().toString());
                    /*验证获取姓名*/
                    if(ET_name.getText().toString().equals("")) {
                        Toast.makeText(UserInfoRegisterActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
                        ET_name.setFocusable(true);
                        ET_name.requestFocus();
                        return;
                    }
                    userInfo.setName(ET_name.getText().toString());
                    /*验证获取性别*/
                    if(ET_sex.getText().toString().equals("")) {
                        Toast.makeText(UserInfoRegisterActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
                        ET_sex.setFocusable(true);
                        ET_sex.requestFocus();
                        return;
                    }
                    userInfo.setSex(ET_sex.getText().toString());
                    if(userInfo.getUserPhoto() != null) {
                        //如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
                        UserInfoRegisterActivity.this.setTitle("正在上传图片，稍等...");
                        String userPhoto = HttpUtil.uploadFile(userInfo.getUserPhoto());
                        UserInfoRegisterActivity.this.setTitle("图片上传完毕！");
                        userInfo.setUserPhoto(userPhoto);
                    } else {
                        userInfo.setUserPhoto("upload/noimage.jpg");
                    }
                    /*获取出版日期*/
                    Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
                    userInfo.setBirthday(new Timestamp(birthday.getTime()));
                    /*验证获取籍贯*/
                    if(ET_jiguan.getText().toString().equals("")) {
                        Toast.makeText(UserInfoRegisterActivity.this, "籍贯输入不能为空!", Toast.LENGTH_LONG).show();
                        ET_jiguan.setFocusable(true);
                        ET_jiguan.requestFocus();
                        return;
                    }
                    userInfo.setJiguan(ET_jiguan.getText().toString());
                    /*验证获取联系电话*/
                    if(ET_telephone.getText().toString().equals("")) {
                        Toast.makeText(UserInfoRegisterActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
                        ET_telephone.setFocusable(true);
                        ET_telephone.requestFocus();
                        return;
                    }
                    userInfo.setTelephone(ET_telephone.getText().toString());
                    /*验证获取家庭地址*/
                    if(ET_address.getText().toString().equals("")) {
                        Toast.makeText(UserInfoRegisterActivity.this, "家庭地址输入不能为空!", Toast.LENGTH_LONG).show();
                        ET_address.setFocusable(true);
                        ET_address.requestFocus();
                        return;
                    }
                    userInfo.setAddress(ET_address.getText().toString());
                    /*调用业务逻辑层上传用户信息*/
                    UserInfoRegisterActivity.this.setTitle("正在上传用户信息，稍等...");
                    String result = userInfoService.AddUserInfo(userInfo);
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

                    UserInfoRegisterActivity.this.finish();
                } catch (Exception e) {}
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQ_CODE_CAMERA_userPhoto  && resultCode == Activity.RESULT_OK) {
//            carmera_path = HttpUtil.FILE_PATH + "/carmera_userPhoto.bmp";
//            BitmapFactory.Options opts = new BitmapFactory.Options();
//            opts.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(carmera_path, opts);
////            opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
//            opts.inJustDecodeBounds = false;
//            try {
//                Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
//                String jpgFileName = "carmera_userPhoto.jpg";
//                String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
//                try {
//                    FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
//                    booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件
//                    File bmpFile = new File(carmera_path);
//                    bmpFile.delete();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                this.iv_userPhoto.setImageBitmap(booImageBm);
//                this.iv_userPhoto.setScaleType(ScaleType.FIT_CENTER);
//                this.userInfo.setUserPhoto(jpgFileName);
//            } catch (OutOfMemoryError err) {  }
//        }
//
//        if(requestCode == REQ_CODE_SELECT_IMAGE_userPhoto && resultCode == Activity.RESULT_OK) {
//            Bundle bundle = data.getExtras();
//            String filename =  bundle.getString("fileName");
//            String filepath = HttpUtil.FILE_PATH + "/" + filename;
//            BitmapFactory.Options opts = new BitmapFactory.Options();
//            opts.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(filepath, opts);
////            opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
//            opts.inJustDecodeBounds = false;
//            try {
//                Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
//                this.iv_userPhoto.setImageBitmap(bm);
//                this.iv_userPhoto.setScaleType(ScaleType.FIT_CENTER);
//            } catch (OutOfMemoryError err) {  }
//            userInfo.setUserPhoto(filename);
//        }
//    }
}
