package com.example.assignment.edits;

import android.graphics.Bitmap;

public class singleType extends ItemEntity{
    //init different single item enums + their assets
    //abstract this shit because multiple item types have different assets
    protected enum ID{
        paper
    }
    protected Bitmap asset;
    protected void init()
    {

    }
}
