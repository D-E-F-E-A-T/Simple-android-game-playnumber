package android.support.p000v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.p000v4.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import android.support.p000v4.content.res.FontResourcesParserCompat.FontFileResourceEntry;
import android.support.p000v4.provider.FontsContractCompat.FontInfo;
import android.support.p000v4.util.SimpleArrayMap;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;

/* renamed from: android.support.v4.graphics.TypefaceCompatApi24Impl */
class TypefaceCompatApi24Impl extends TypefaceCompatBaseImpl {
    private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String TAG = "TypefaceCompatApi24Impl";
    private static final Method sAddFontWeightStyle;
    private static final Method sCreateFromFamiliesWithDefault;
    private static final Class sFontFamily;
    private static final Constructor sFontFamilyCtor;

    TypefaceCompatApi24Impl() {
    }

    static {
        Method addFontMethod;
        Constructor fontFamilyCtor;
        Class fontFamilyClass;
        Method createFromFamiliesWithDefaultMethod;
        try {
            fontFamilyClass = Class.forName(FONT_FAMILY_CLASS);
            try {
                fontFamilyCtor = fontFamilyClass.getConstructor(new Class[0]);
                try {
                    addFontMethod = fontFamilyClass.getMethod(ADD_FONT_WEIGHT_STYLE_METHOD, new Class[]{ByteBuffer.class, Integer.TYPE, List.class, Integer.TYPE, Boolean.TYPE});
                    try {
                        Object familyArray = Array.newInstance(fontFamilyClass, 1);
                        createFromFamiliesWithDefaultMethod = Typeface.class.getMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, new Class[]{familyArray.getClass()});
                    } catch (ClassNotFoundException | NoSuchMethodException e) {
                        e = e;
                        Log.e(TAG, e.getClass().getName(), e);
                        fontFamilyClass = null;
                        fontFamilyCtor = null;
                        addFontMethod = null;
                        createFromFamiliesWithDefaultMethod = null;
                        sFontFamilyCtor = fontFamilyCtor;
                        sFontFamily = fontFamilyClass;
                        sAddFontWeightStyle = addFontMethod;
                        sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
                    }
                } catch (ClassNotFoundException | NoSuchMethodException e2) {
                    e = e2;
                    Log.e(TAG, e.getClass().getName(), e);
                    fontFamilyClass = null;
                    fontFamilyCtor = null;
                    addFontMethod = null;
                    createFromFamiliesWithDefaultMethod = null;
                    sFontFamilyCtor = fontFamilyCtor;
                    sFontFamily = fontFamilyClass;
                    sAddFontWeightStyle = addFontMethod;
                    sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
                }
            } catch (ClassNotFoundException | NoSuchMethodException e3) {
                e = e3;
                Log.e(TAG, e.getClass().getName(), e);
                fontFamilyClass = null;
                fontFamilyCtor = null;
                addFontMethod = null;
                createFromFamiliesWithDefaultMethod = null;
                sFontFamilyCtor = fontFamilyCtor;
                sFontFamily = fontFamilyClass;
                sAddFontWeightStyle = addFontMethod;
                sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
            }
        } catch (ClassNotFoundException | NoSuchMethodException e4) {
            e = e4;
            Log.e(TAG, e.getClass().getName(), e);
            fontFamilyClass = null;
            fontFamilyCtor = null;
            addFontMethod = null;
            createFromFamiliesWithDefaultMethod = null;
            sFontFamilyCtor = fontFamilyCtor;
            sFontFamily = fontFamilyClass;
            sAddFontWeightStyle = addFontMethod;
            sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
        }
        sFontFamilyCtor = fontFamilyCtor;
        sFontFamily = fontFamilyClass;
        sAddFontWeightStyle = addFontMethod;
        sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
    }

    public static boolean isUsable() {
        if (sAddFontWeightStyle == null) {
            Log.w(TAG, "Unable to collect necessary private methods.Fallback to legacy implementation.");
        }
        return sAddFontWeightStyle != null;
    }

    private static Object newFamily() {
        try {
            return sFontFamilyCtor.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean addFontWeightStyle(Object family, ByteBuffer buffer, int ttcIndex, int weight, boolean style) {
        try {
            return ((Boolean) sAddFontWeightStyle.invoke(family, new Object[]{buffer, Integer.valueOf(ttcIndex), null, Integer.valueOf(weight), Boolean.valueOf(style)})).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Typeface createFromFamiliesWithDefault(Object family) {
        try {
            Object familyArray = Array.newInstance(sFontFamily, 1);
            Array.set(familyArray, 0, family);
            return (Typeface) sCreateFromFamiliesWithDefault.invoke(null, new Object[]{familyArray});
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontInfo[] fonts, int style) {
        Object family = newFamily();
        SimpleArrayMap<Uri, ByteBuffer> bufferCache = new SimpleArrayMap<>();
        for (FontInfo font : fonts) {
            Uri uri = font.getUri();
            ByteBuffer buffer = (ByteBuffer) bufferCache.get(uri);
            if (buffer == null) {
                buffer = TypefaceCompatUtil.mmap(context, cancellationSignal, uri);
                bufferCache.put(uri, buffer);
            }
            if (!addFontWeightStyle(family, buffer, font.getTtcIndex(), font.getWeight(), font.isItalic())) {
                return null;
            }
        }
        return Typeface.create(createFromFamiliesWithDefault(family), style);
    }

    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontFamilyFilesResourceEntry entry, Resources resources, int style) {
        FontFileResourceEntry[] entries;
        Object family = newFamily();
        for (FontFileResourceEntry e : entry.getEntries()) {
            ByteBuffer buffer = TypefaceCompatUtil.copyToDirectBuffer(context, resources, e.getResourceId());
            if (buffer == null || !addFontWeightStyle(family, buffer, e.getTtcIndex(), e.getWeight(), e.isItalic())) {
                return null;
            }
        }
        return createFromFamiliesWithDefault(family);
    }
}
