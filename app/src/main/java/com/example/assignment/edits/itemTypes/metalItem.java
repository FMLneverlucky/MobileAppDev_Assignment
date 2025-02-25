package com.example.assignment.edits.itemTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;

public class metalItem extends ItemType {
    @Override
    public String get_itemType() {
        return "metal";
    }
    public metalItem(){
        IdNum = ID.metal;
        Bitmap newBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.metal_can);
        itemBitmap = Bitmap.createScaledBitmap(newBitmap, (int) (newBitmap.getWidth()/1.5f), (int) (newBitmap.getHeight()/1.5f), true);
    }

}
