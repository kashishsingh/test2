package com.example.test2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatDialogFragment;

public class Reset_Password extends AppCompatDialogFragment
{
    private EditText editTextEmail;
    private ResetPasswordListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

      View view = inflater.inflate(R.layout.reset_password,null);

                builder.setView(view)
                .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String email = editTextEmail.getText().toString().trim();
                        if(!TextUtils.isEmpty(email))
                            listener.changePassword(email);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        editTextEmail = view.findViewById(R.id.editText_reset);
        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ResetPasswordListener) context;
        } catch (Exception e) {
            //
        }
    }

    public interface ResetPasswordListener
    {
        void changePassword(String email);
    }
}
