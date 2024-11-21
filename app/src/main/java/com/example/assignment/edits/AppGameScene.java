package com.example.assignment.edits;

import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.ColorInt;

import com.example.assignment.mgp2d.core.GameScene;

public class AppGameScene extends GameScene {


    @Override
    public void onUpdate(float dt) {

    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#fcd977"));
    }
}
