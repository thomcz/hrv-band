package hrv.band.app.view.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import hrv.band.app.R;
import hrv.band.app.storage.SQLite.SQLController;

/**
 * Copyright (c) 2017
 * Created by Julian Martin on 19.01.2017
 *
 * Dialog asking the user to import his data.
 */
public class ImportFragment extends DialogFragment {

    /**
     * Returns a new instance of this fragment.
     * @return a new instance of this fragment.
     */
    public static ImportFragment newInstance() {
        return new ImportFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View view =  View.inflate(getActivity(), R.layout.dialog_simple_text, null);

        TextView textView = (TextView) view.findViewById(R.id.dialog_textview);
        textView.setText(getResources().getString(R.string.sentence_import_db));

         builder.setView(view)
                .setPositiveButton(R.string.common_import, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        importDB();
                    }
                })
                .setNegativeButton(R.string.common_cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ImportFragment.this.getDialog().cancel();
                    }
                });
        builder.setTitle(getResources().getString(R.string.common_import));
        return builder.create();
    }

    /**
     * Imports the user database.
     */
    private void importDB() {
        String saveDir = getActivity().getFilesDir() + "/export.sql";
        SQLController sql = new SQLController();

        try {
            int duration = Toast.LENGTH_SHORT;

            if(!sql.importDB(saveDir, getActivity())) {
                CharSequence text = getResources().getText(R.string.sentence_import_failed);
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), text, duration);
                toast.show();
            }
            else {
                CharSequence text = getResources().getText(R.string.sentence_import_worked);
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), text, duration);
                toast.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
