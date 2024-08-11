package com.auto.autodroid;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;


public class AutoAccessibilityService extends AccessibilityService {
    private static final String TAG = "cloudy/AutoAccessibilityService";
    private static final String WECHAT_READ_ACTIVITY = "com.tencent.weread.ReaderFragmentActivity";
    private int mCurPage = 0;
    private static final int MAX_PAGE_COUNT = 300;
    private boolean mSwipeLeft = true;

    private final Runnable mSwipeTask = new Runnable() {
        @Override
        public void run() {
            mCurPage++;
            if (mCurPage >= MAX_PAGE_COUNT) {
                Log.d(TAG, "change direction!!" + ",max_count=" + MAX_PAGE_COUNT);
                mSwipeLeft = !mSwipeLeft;
                mCurPage = 0;
            }
            performSwipe(mSwipeLeft);
            mHandler.postDelayed(this, 3000);
        }
    };

    private final Handler mHandler = new Handler();

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "onServiceConnected");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String readActivity = event.getClassName().toString();
        if (AutoAccessibilityService.WECHAT_READ_ACTIVITY.equals(readActivity)) {
            int eventType = event.getEventType();
            if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                Log.d(TAG, "wechat_read window state changed !");
                mHandler.post(mSwipeTask);
            }
        }
    }

    public void performSwipe(boolean left) {
        Path path = new Path();
        //create a gesture path screen size: 1240x2772
        if (left) {
            path.moveTo(900, 1000);
            path.lineTo(500, 1000);
        } else {
            path.moveTo(500, 1000);
            path.lineTo(900, 1000);
        }

        //gesture latency=0ms, keep_time = 500ms
        GestureDescription.StrokeDescription swipeLeftStroke =
                new GestureDescription.StrokeDescription(path, 0, 100);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(swipeLeftStroke);
        dispatchGesture(builder.build(), null, null);
        String orientation = left ? "next" : "back";
        Log.d(TAG, "scroll " + orientation + " done !!");
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
        mHandler.removeCallbacks(mSwipeTask);
    }
}
