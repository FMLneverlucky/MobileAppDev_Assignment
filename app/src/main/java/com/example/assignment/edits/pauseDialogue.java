package com.example.assignment.edits;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.GameScene;

public class pauseDialogue extends DialogFragment {
    private static boolean _isShowing = false;
    public static boolean isShowing() {return _isShowing;}

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        _isShowing = true;
        GameActivity.instance.setTimeScale(0); //pause the game
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Paused");
        builder.setPositiveButton("Return to main menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GameActivity.instance.finish();
                GameScene.getCurrent().resetGame(); //idk how to make game reset every time return to menu so am doing scuff :D
            }
        });
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
