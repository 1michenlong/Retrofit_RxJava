package com.example.tecl.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity{
    private Button mDependencyButton;
    private TextView mChildText;

    int[] temp = new int[]{0, 0};
    Boolean ismove = false;
    int downX = 0;
    int downY = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second2_activity);
//        xmlForSecondActivity();  R.layout.second_activity
    }


    private void xmlForSecondActivity(){
        mDependencyButton=findViewById(R.id.id_dependency);
        mChildText=findViewById(R.id.id_child);

        mDependencyButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventMotion = event.getAction();
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();

                switch (eventMotion){
                    case MotionEvent.ACTION_DOWN:
                        temp[0] = (int) event.getX();
                        temp[1] = y - v.getTop();
                        downX = (int) event.getRawX();
                        downY = (int) event.getRawY();
                        ismove = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        v.layout(x - temp[0], y - temp[1], x + v.getWidth() - temp[0], y - temp[1] + v.getHeight());
                        if (Math.abs(downX - x) > 5 || Math.abs(downY - y) > 5)
                            ismove = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!ismove)
                            Toast.makeText(SecondActivity.this, "你点击了这个按钮", Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
    }
}
