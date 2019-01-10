package com.yyp.molia.myapplication;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyp.molia.service.OrderInfoService;
import com.yyp.molia.utils.Declare;
import com.yyp.molia.domain.Doctor;

import com.yyp.molia.service.DoctorService;
import com.yyp.molia.utils.HttpUtil;

public class DoctorDetailActivity extends Activity {
    // 声明返回按钮
    private Button btnReturn;
    // 申明预约医生按钮
    private Button btnOrder;

    // 声明医生编号控件
    private TextView TV_doctorNo;
    // 声明登录密码控件
    private TextView TV_password;
    // 声明所在科室控件
    private TextView TV_departmentObj;
    // 声明姓名控件
    private TextView TV_name;
    // 声明性别控件
    private TextView TV_sex;
    // 声明医生照片图片框
    private ImageView iv_doctorPhoto;
    // 声明学历控件
    private TextView TV_education;
    // 声明入院日期控件
    private TextView TV_inDate;
    // 声明联系电话控件
    private TextView TV_telephone;
    // 声明每日出诊次数控件
    private TextView TV_visiteTimes;
    // 声明附加信息控件
    private TextView TV_memo;
    /* 要保存的医生信息 */
    Doctor doctor = new Doctor();
    /* 医生管理业务逻辑层 */
    private DoctorService doctorService = new DoctorService();
    private String doctorNo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置标题
        setTitle("手机客户端-查看医生详情");
        // 设置当前Activity界面布局
        setContentView(R.layout.doctor_detail);
        // 通过findViewById方法实例化组件
        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnOrder = (Button) findViewById(R.id.btnOrder);
        TV_doctorNo = (TextView) findViewById(R.id.TV_doctorNo);
        TV_password = (TextView) findViewById(R.id.TV_password);
        TV_departmentObj = (TextView) findViewById(R.id.TV_departmentObj);
        TV_name = (TextView) findViewById(R.id.TV_name);
        TV_sex = (TextView) findViewById(R.id.TV_sex);
        iv_doctorPhoto = (ImageView) findViewById(R.id.iv_doctorPhoto);
        TV_education = (TextView) findViewById(R.id.TV_education);
        TV_inDate = (TextView) findViewById(R.id.TV_inDate);
        TV_telephone = (TextView) findViewById(R.id.TV_telephone);
        TV_visiteTimes = (TextView) findViewById(R.id.TV_visiteTimes);
        TV_memo = (TextView) findViewById(R.id.TV_memo);
        Bundle extras = this.getIntent().getExtras();
        doctorNo = extras.getString("doctorNo");
        initViewData();
        btnReturn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DoctorDetailActivity.this.finish();
            }
        });
        btnOrder.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // 添加预约信息
                OrderInfoService orderInfoService = new OrderInfoService();
                orderInfoService.UpdateOrderInfo(doctorNo);
            }
        });

        this.btnOrder.setVisibility(View.VISIBLE);

    }
    /* 初始化显示详情界面的数据 */
    private void initViewData() {
        doctor = doctorService.GetDoctor(doctorNo);
        this.TV_doctorNo.setText(doctor.getDoctorNo());
        this.TV_password.setText(doctor.getPassword());
        this.TV_departmentObj.setText(doctor.getDepartment());
        this.TV_name.setText(doctor.getName());
        this.TV_sex.setText(doctor.getSex());
        this.TV_education.setText(doctor.getEducation());
        Date inDate = new Date(doctor.getInDate().getTime());
        String inDateStr = (inDate.getYear() + 1900) + "-" + (inDate.getMonth()+1) + "-" + inDate.getDate();
        this.TV_inDate.setText(inDateStr);
        this.TV_telephone.setText(doctor.getTelephone());
        this.TV_visiteTimes.setText(doctor.getVisiteTimes() + "");
        this.TV_memo.setText(doctor.getMemo());
    }
}

