package com.example.assignment.edits.itemTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;

public class paperItem implements ItemType{
    private final Bitmap paperBitmap;
    private final ID idNo = ID.paper;
    public paperItem(){
        Bitmap newBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.paperball);
        paperBitmap = Bitmap.createScaledBitmap(newBitmap, newBitmap.getWidth()/2, newBitmap.getHeight()/2, true);
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
