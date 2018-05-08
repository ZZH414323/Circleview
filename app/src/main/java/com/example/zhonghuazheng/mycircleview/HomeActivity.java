package com.example.zhonghuazheng.mycircleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.zhonghuazheng.mycircleview.circle.Circleview;

import java.util.Random;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    Circleview hwv;
    Button btn;
    Random rand;//随机数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        hwv=  findViewById(R.id.hwv);
        btn=  findViewById(R.id.btn);

        rand = new Random();
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                //点击事件中，调用动的方法
                hwv.changeAngle((float) rand.nextInt(62));
                break;
        }
    }
}
