package com.example.assignment.edits;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.assignment.mgp2d.core.GameActivity;

public class BackDialogue extends DialogFragment {
    private static boolean _isShowing = false;
    public static boolean isShowing() {return _isShowing;}

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        _isShowing = true;
        GameActivity.instance.setTimeScale(0); //pause the game
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Paused");
        builder.setPositiveButton("Return to main menu", ((dialog, which) -> GameActivity.instance.finish()));
        builder.setNegativeButton("Resume", null);
        return builder.create();
    } //build dialog to be contained in dialogFragment

    //code below is for behaviour when dialog closes
    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        _isShowing = false;
        GameActivity.instance.setTimeScale(1);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        _isShowing = false;
        GameActivity.instance.setTimeScale(1);
    }
}
