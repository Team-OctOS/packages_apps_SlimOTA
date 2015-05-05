package com.fusionjack.slimota.dialog;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * Created by fusionjack on 05.05.15.
 */
public class OTADialogHandler extends Handler {

    public static final int MSG_SHOW_DIALOG = 0;
    public static final int MSG_CLOSE_DIALOG = 1;

    private static final String DIALOG_TAG = OTADialogFragment.class.getName();

    private Context mContext;

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_SHOW_DIALOG:
                mContext = (Context) msg.obj;
                if (mContext instanceof Activity) {
                    Activity activity = (Activity) mContext;

                    FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
                    Fragment prev = getOTADialogFragment();
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    OTADialogFragment dialog = OTADialogFragment.newInstance();
                    dialog.show(ft, DIALOG_TAG);
                }
                break;
            case MSG_CLOSE_DIALOG:
                OTADialogFragment dialog = getOTADialogFragment();
                if (dialog != null) {
                    dialog.dismissAllowingStateLoss();
                }
                break;
            default:
                break;
        }
    }

    private OTADialogFragment getOTADialogFragment() {
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            Fragment fragment = activity.getFragmentManager().findFragmentByTag(DIALOG_TAG);
            return (OTADialogFragment) fragment;
        }
        return null;
    }
}
