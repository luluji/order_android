package com.yyp.molia.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yyp.molia.utils.Declare;
import com.yyp.molia.utils.HttpUtil;

import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private Button cancelBtn,loginBtn,exitBtn,registerBtn;
    // 声明用户名、密码输入框
    private EditText userEditText,pwdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置当前Activity界面布局
        setContentView(R.layout.activity_main);
        // 设置标题
        setTitle("手机客户端-登入");
        // 通过findViewById方法实例化组件
        cancelBtn = (Button)findViewById(R.id.cancelButton);
        // 通过findViewById方法实例化组件
        loginBtn = (Button)findViewById(R.id.loginButton);
        exitBtn = (Button)findViewById(R.id.exitButton);
        registerBtn = (Button)findViewById(R.id.registerButton);
        // 通过findViewById方法实例化组件
        userEditText = (EditText)findViewById(R.id.userEditText);
        // 通过findViewById方法实例化组件
        pwdEditText = (EditText)findViewById(R.id.pwdEditText);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userEditText.setText("");
                pwdEditText.setText("");
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.exit(0);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UserInfoRegisterActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Declare declare = (Declare) getApplicationContext();

                try {
                    String url = HttpUtil.BASE_URL
                            + "LoginServlet?userName="
                            + URLEncoder.encode(
                            URLEncoder.encode(userEditText.getText().toString(), "UTF-8"), "UTF-8")+"&password="
                            + URLEncoder.encode(
                            URLEncoder.encode(pwdEditText.getText().toString(), "UTF-8"), "UTF-8");
                    // 查询返回结果
                    String result = "22";//HttpUtil.queryStringForPost(url);
                    System.out.println("=========================  "+result);
                    if(!result.equals("0")){
                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@");

                        //declare.setId(Integer.parseInt(result));

                        declare.setUserName(userEditText.getText().toString());
                        Toast.makeText(getApplicationContext(), "登入成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,MainMenuUserActivity.class);
                        startActivity(intent);
                        MainActivity.this.finish();

                    }else{
                        Toast.makeText(getApplicationContext(), "登入失败", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.i("LoginActivity",e.toString());
                }

            }
        });
    }
}
