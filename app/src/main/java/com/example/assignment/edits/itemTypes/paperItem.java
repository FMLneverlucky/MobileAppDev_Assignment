package com.example.assignment.edits.itemTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;

public class paperItem extends ItemType {
    @Override
    public String get_itemType() {
        return "paper";
    }

    public paperItem(){
        IdNum = ID.paper;
        Bitmap newBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.paperball);
        itemBitmap = Bitmap.createScaledBitmap(newBitmap, (int) (newBitmap.getWidth()/2.5f), (int) (newBitmap.getHeight()/2.5f), true);
    }

}
