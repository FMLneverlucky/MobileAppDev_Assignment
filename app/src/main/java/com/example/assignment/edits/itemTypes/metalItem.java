package com.example.assignment.edits.itemTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;

public class metalItem implements ItemType{
    @Override
    public String item_getType() {
        return "metal";
    }

    private final Bitmap paperBitmap;
    private final ID idNo = ID.metal;
    public metalItem(){
        Bitmap newBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.metal_can);
        paperBitmap = Bitmap.createScaledBitmap(newBitmap, (int) (newBitmap.getWidth()/1.5f), (int) (newBitmap.getHeight()/1.5f), true);
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
