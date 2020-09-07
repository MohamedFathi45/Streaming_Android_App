package com.example.army_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;



public class DownloadLatestVersionDialog extends AppCompatDialogFragment {

    String link;
    public  DownloadLatestVersionDialog(String link){
        this.link = link;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(getActivity().getString(R.string.update)).
        setMessage(getActivity().getString(R.string.update_the_app_statment))
                .setPositiveButton(getActivity().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(link));
                            startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(getActivity() , "Error, Application is going to terminate" , Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }
                }).setNegativeButton(getActivity().getString(R.string.exit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        });
        return alertDialogBuilder.create();
    }
}
