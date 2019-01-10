package com.yyp.molia.myapplication;

import java.util.Date;
import com.yyp.molia.domain.OrderInfo;
import com.yyp.molia.service.OrderInfoService;
import com.yyp.molia.domain.UserInfo;
import com.yyp.molia.service.UserInfoService;
import com.yyp.molia.domain.Doctor;
import com.yyp.molia.service.DoctorService;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OrderInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明预约id控件
	private TextView TV_orderId;
	// 声明预约用户控件
	private TextView TV_uesrObj;
	// 声明预约医生控件
	private TextView TV_doctor;
	// 声明预约日期控件
	private TextView TV_orderDate;
	// 声明预约时间段控件
	private TextView TV_timeSlotObj;
	// 声明出诊状态控件
	private TextView TV_visiteStateObj;
	// 声明医生说明控件
	private TextView TV_introduce;
	/* 要保存的预约信息 */
	OrderInfo orderInfo = new OrderInfo();
	/* 预约管理业务逻辑层 */
	private OrderInfoService orderInfoService = new OrderInfoService();
	private UserInfoService userInfoService = new UserInfoService();
	private DoctorService doctorService = new DoctorService();
	private int orderId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-查看预约详情");
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_detail);
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_orderId = (TextView) findViewById(R.id.TV_orderId);
		TV_uesrObj = (TextView) findViewById(R.id.TV_uesrObj);
		TV_doctor = (TextView) findViewById(R.id.TV_doctor);
		TV_orderDate = (TextView) findViewById(R.id.TV_orderDate);
		TV_timeSlotObj = (TextView) findViewById(R.id.TV_timeSlotObj);
		TV_visiteStateObj = (TextView) findViewById(R.id.TV_visiteStateObj);
		TV_introduce = (TextView) findViewById(R.id.TV_introduce);
		Bundle extras = this.getIntent().getExtras();
		orderId = extras.getInt("orderId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OrderInfoDetailActivity.this.finish();
			}
		});
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
		orderInfo = orderInfoService.GetOrderInfo(orderId);
		this.TV_orderId.setText(orderInfo.getOrderId() + "");
		UserInfo uesrObj = userInfoService.GetUserInfo(orderInfo.getUesrObj());
		this.TV_uesrObj.setText(uesrObj.getName());
		Doctor doctor = doctorService.GetDoctor(orderInfo.getDoctor());
		this.TV_doctor.setText(doctor.getName());
		Date orderDate = new Date(orderInfo.getOrderDate().getTime());
		String orderDateStr = (orderDate.getYear() + 1900) + "-" + (orderDate.getMonth()+1) + "-" + orderDate.getDate();
		this.TV_orderDate.setText(orderDateStr);


//		VisitState visiteStateObj = visitStateService.GetVisitState(orderInfo.getVisiteStateObj());
//		this.TV_visiteStateObj.setText(visiteStateObj.getVisitStateName());
		this.TV_introduce.setText(orderInfo.getIntroduce());
	}
}
