package com.example.jcesa.chilakillers;

/**
 * Created by jcesa on 27/05/2017.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DialogoFragment extends DialogFragment {
    public static DialogoFragment newInstance(int title) {
        DialogoFragment frag = new DialogoFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        Dialog dialogo;

        dialogo = new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.logo5)
                .setTitle("Bienvenido!")
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MainActivity) getActivity()).doPositiveClick();
                            }
                        }
                )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MainActivity) getActivity()).doNegativeClick();
                            }
                        }
                )
                .create();
        return dialogo;
    }
}
