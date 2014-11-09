package com.petrovdevelopment.dice.activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petrovdevelopment.dice.R;

public class ConfirmDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setStyle(STYLE_NO_FRAME, getTheme());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View confirmLayout = inflateLayout(inflater, container, savedInstanceState);

        setOkHandler(confirmLayout);
        setCancelHandler(confirmLayout);
        customizeView(confirmLayout);
        setCanceledOnTouchOutside();
        return confirmLayout;
    }

    /**
     * Set the dialog to be cancelled if clicked outside of its area
     */
    protected void setCanceledOnTouchOutside() {
        getDialog().setCanceledOnTouchOutside(true);
    }

    /**
     * Inflate a layout, to be used by the dialog view.
     *
     */
    protected View inflateLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View confirmLayout = (ViewGroup) inflater.inflate(R.layout.dialog_confirm, container, false);
        return confirmLayout;
    }


    /**
     * Close the parent activity
     *
     * @param view
     */
    protected void setOkHandler(View view) {
        // close the activity
        View okButton = view.findViewById(R.id.okButton);
        if (okButton != null) {
//            okButton.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   U.log(this, "OK");
//                }
//            });
         //   getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

    }

    /**
     * Dismiss the fragment
     *
     * @param view
     */
    protected void setCancelHandler(View view) {
        View cancelButton = view.findViewById(R.id.cancelButton);
        if (cancelButton != null) {
          // getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

//            cancelButton.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ConfirmDialog.this.dismiss();
//                }
//            });
        }

    }

    /**
     * A method to be overwritten to provide some custom behavior
     *
     * @param view
     */
    protected void customizeView(View view) {
        // do nothing
    }
}