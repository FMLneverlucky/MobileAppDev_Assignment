package com.example.assignment.edits;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.GameScene;

public class mainMenu extends Activity implements View.OnClickListener {
    private Button _startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        _startButton = findViewById(R.id.StartButton);
        _startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == _startButton)
        {
            startActivity(new Intent().setClass(this, GameActivity.class));
            GameScene.enter(AppGameScene.class);
        }
    }
}
