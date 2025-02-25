package com.example.assignment.edits.itemTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;

public class defaultItem extends ItemType {
    public defaultItem(){
        IdNum = ID.none;
    Bitmap bitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.default_item_texture);
        itemBitmap = Bitmap.createScaledBitmap(bitmap, (int)bitmap.getWidth() /2, bitmap.getHeight() /2, true);
    //halved size of sprite here
    }

    public String get_itemType() {
        return "default";
    }
}
