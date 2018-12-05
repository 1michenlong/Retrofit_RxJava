package com.example.tecl.rxjavademo.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MyBehavior extends CoordinatorLayout.Behavior<TextView>{

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency) {
        //如果dependency是Button的实例, 说明它就是我们所需要的Dependency
        return dependency instanceof Button;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency) {
        //根据dependency的位置, 设置TextView的位置
        child.setX(dependency.getX());
        child.setY(dependency.getY()+200);
        return true;
    }
}
