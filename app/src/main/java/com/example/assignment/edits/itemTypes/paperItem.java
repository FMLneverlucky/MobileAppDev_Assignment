package com.example.assignment.edits.itemTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;

public class paperItem implements ItemType{

    public String item_getType() {
        return "paper";
    }

    private final Bitmap paperBitmap;
    private final ID idNo = ID.paper;
    public paperItem(){
        Bitmap newBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.paperball);
        paperBitmap = Bitmap.createScaledBitmap(newBitmap, (int) (newBitmap.getWidth()/2.5f), (int) (newBitmap.getHeight()/2.5f), true);
    }

    @Override
    public Bitmap getBitmap() {
        return paperBitmap;
    }

    @Override
    public ID getIdNum() {
        return idNo;
    }
}
