package com.yyp.molia.myapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.yyp.molia.domain.Doctor;
import com.yyp.molia.service.DoctorService;
import com.yyp.molia.utils.Declare;
import com.yyp.molia.utils.DoctorSimpleAdapter;
import com.yyp.molia.utils.HttpUtil;

public class DoctorListActivity extends Activity {
    DoctorSimpleAdapter adapter;
    ListView lv;
    List<Map<String, Object>> list;
    String doctorNo;
    /* 医生操作业务逻辑层对象 */
    DoctorService doctorService = new DoctorService();
    /*保存查询参数条件的医生对象*/
    private Doctor queryConditionDoctor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_list);
        Declare declare = (Declare) getApplicationContext();
        String username = declare.getUserName();
        if (username == null) {
            setTitle("当前位置--医生列表");
        } else {
            setTitle("您好：" + username + "   当前位置---医生列表");
        }
//        Bundle extras = this.getIntent().getExtras();
//        if(extras != null)
//            queryConditionDoctor = (Doctor)extras.getSerializable("queryConditionDoctor");
        setViews();
    }

    private void setViews() {
        lv = (ListView) findViewById(R.id.h_list_view);
        list = getDatas();
        try {
            adapter = new DoctorSimpleAdapter(this, list,
                    R.layout.doctor_list_item,
                    new String[] { "doctorNo","departmentObj","name","sex","doctorPhoto","education","inDate","visiteTimes" },
                    new int[] { R.id.tv_doctorNo,R.id.tv_departmentObj,R.id.tv_name,R.id.tv_sex,R.id.iv_doctorPhoto,R.id.tv_education,R.id.tv_inDate,R.id.tv_visiteTimes,});
            lv.setAdapter(adapter);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        // 添加长按点击
        lv.setOnCreateContextMenuListener(doctorListItemListener);
        lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                String doctorNo = list.get(arg2).get("doctorNo").toString();
                Intent intent = new Intent();
                intent.setClass(DoctorListActivity.this, DoctorDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("doctorNo", doctorNo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    private OnCreateContextMenuListener doctorListItemListener = new OnCreateContextMenuListener() {
        public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
            Declare declare = (Declare) DoctorListActivity.this.getApplication();
//            if(declare.getIdentify().equals("admin")) {
//                menu.add(0, 0, 0, "编辑医生信息");
//                menu.add(0, 1, 0, "删除医生信息");
//            }
        }
    };

    // 长按菜单响应函数
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        return super.onContextItemSelected(item);
    }


    private List<Map<String, Object>> getDatas() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            /* 查询医生信息 */
            List<Doctor> doctorList = doctorService.QueryDoctor(queryConditionDoctor);
            for (int i = 0; i < doctorList.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("doctorNo", doctorList.get(i).getDoctorNo());
                map.put("departmentObj", doctorList.get(i).getDepartment());
                map.put("name", doctorList.get(i).getName());
                map.put("sex", doctorList.get(i).getSex());
                map.put("education", doctorList.get(i).getEducation());
                map.put("inDate", doctorList.get(i).getInDate());
                map.put("visiteTimes", doctorList.get(i).getVisiteTimes());
                list.add(map);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Declare declare = (Declare) DoctorListActivity.this.getApplication();
//        if(declare.getIdentify().equals("admin")) {
//            menu.add(0, 1, 1, "添加医生");
//            menu.add(0, 2, 2, "查询医生");
//            menu.add(0, 3, 3, "返回主界面");
//        } else {
            menu.add(0, 1, 1, "查询医生");
            menu.add(0, 2, 2, "返回主界面");
//        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Declare declare = (Declare) DoctorListActivity.this.getApplication();

            if (item.getItemId() == 1) {
                /*查询医生信息*/
                Intent intent = new Intent();
                intent.setClass(DoctorListActivity.this, DoctorQueryActivity.class);
                startActivity(intent);
                DoctorListActivity.this.finish();
            } else if (item.getItemId() == 2) {
                /*返回主界面*/
                Intent intent = new Intent();
                intent.setClass(DoctorListActivity.this, MainMenuUserActivity.class);
                startActivity(intent);
                DoctorListActivity.this.finish();
            }

        return true;
    }
}
