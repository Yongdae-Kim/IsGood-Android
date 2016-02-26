package com.scratchback.isgood.util;

import android.app.Activity;

import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperToast;

/**
 * Created by useruser on 16. 2. 19..
 */
public class ToastSystem {

    public static String NOT_READY_FUNCTION = "아직 준비되지 않은 기능입니다.";

    public static String NO_CONTENT = "내용을 입력해주세요.";

    private static ToastSystem toastSystem;
    private SuperActivityToast superActivityToast;

    private ToastSystem() {

    }

    public static ToastSystem getInstacne() {
        if (toastSystem == null) {
            toastSystem = new ToastSystem();
        }
        return toastSystem;
    }

    public void show(Activity activity, String msg) {
        superActivityToast = new SuperActivityToast(activity);
        superActivityToast.setText(msg);
        superActivityToast.setDuration(SuperToast.Duration.SHORT);
        superActivityToast.show();
    }

    public void dismiss() {
        superActivityToast.dismiss();

    }
}