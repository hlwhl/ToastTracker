package com.linwei.electronicdream.toasttracker.Services;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.view.accessibility.AccessibilityEvent;

import com.linwei.electronicdream.toasttracker.utils.NotificationUtil;

import java.util.concurrent.atomic.AtomicInteger;


public class ToastTrackerService extends AccessibilityService {
    private final static AtomicInteger c = new AtomicInteger(0);

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() != AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            return;
        }
        //消息来源
        String source = (String) accessibilityEvent.getPackageName();
        //消息来源不属于本程序
        if (!source.equals(getPackageName())) {
            Parcelable parcelable = accessibilityEvent.getParcelableData();
            if (!(parcelable instanceof Notification)) {
                if (accessibilityEvent.getText().isEmpty()) {
                    return;
                }
                String toastmsg = accessibilityEvent.getText().get(0).toString();
                //Toast.makeText(getApplicationContext(), source+toastmsg, Toast.LENGTH_LONG).show();
                NotificationUtil notificationUtil = new NotificationUtil(getApplicationContext());
                try {
                    PackageManager packageManager = getPackageManager();
                    String name = packageManager.getApplicationLabel(getPackageManager().getApplicationInfo(source, PackageManager.GET_META_DATA)).toString();
                    notificationUtil.showNotification(drawable2Bitmap(packageManager.getApplicationIcon(source)), name, toastmsg, getNotiID());
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void onInterrupt() {

    }

    public static int getNotiID() {
        return c.incrementAndGet();
    }
}
