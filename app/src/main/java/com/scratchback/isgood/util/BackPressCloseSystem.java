package com.scratchback.isgood.util;

import android.app.Activity;

/**
 * Created by useruser on 16. 2. 19..
 */
public class BackPressCloseSystem {
    private long backKeyPressedTime = 0;

    private Activity activity;

    public BackPressCloseSystem(Activity activity) {
        this.activity = activity;
    }

    public void onBackPressed() {

        if (isAfter2Seconds()) {
            backKeyPressedTime = System.currentTimeMillis();
            // 현재시간을 다시 초기화

            ToastSystem.getInstacne().show(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.");

            return;
        }

        if (isBefore2Seconds()) {
            programShutdown();
            ToastSystem.getInstacne().dismiss();
        }
    }

    private Boolean isAfter2Seconds() {
        return System.currentTimeMillis() > backKeyPressedTime + 2000;
        // 2초 지났을 경우
    }

    private Boolean isBefore2Seconds() {
        return System.currentTimeMillis() <= backKeyPressedTime + 2000;
        // 2초가 지나지 않았을 경우
    }

    private void programShutdown() {
        activity.moveTaskToBack(true);
        activity.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
