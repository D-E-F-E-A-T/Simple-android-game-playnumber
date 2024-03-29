package android.support.p000v4.view;

import android.os.Build.VERSION;
import android.view.View;
import android.view.Window;

/* renamed from: android.support.v4.view.WindowCompat */
public final class WindowCompat {
    public static final int FEATURE_ACTION_BAR = 8;
    public static final int FEATURE_ACTION_BAR_OVERLAY = 9;
    public static final int FEATURE_ACTION_MODE_OVERLAY = 10;

    private WindowCompat() {
    }

    public static <T extends View> T requireViewById(Window window, int id) {
        if (VERSION.SDK_INT >= 28) {
            return window.requireViewById(id);
        }
        T view = window.findViewById(id);
        if (view != null) {
            return view;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Window");
    }
}
