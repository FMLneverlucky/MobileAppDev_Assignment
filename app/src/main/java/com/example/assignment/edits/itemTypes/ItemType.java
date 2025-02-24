package com.example.assignment.edits.itemTypes;

import android.graphics.Bitmap;

public interface ItemType {

    public enum ID{
        none,   //default value, for debugging
        paper,
        plastic,
        metal,
        glass
    };
    public ID IdNum = null;

    public Bitmap getBitmap();
    public ID getIdNum();
    public String item_getType();
}
