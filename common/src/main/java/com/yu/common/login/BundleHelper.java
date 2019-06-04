package com.yu.common.login;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author yudneghao
 * @date 2019-06-04
 */
public class BundleHelper implements Serializable {
    private Bundle bundle;

    private BundleHelper(Bundle bundle) {
        this.bundle = bundle == null ? new Bundle() : bundle;
    }

    public static BundleHelper create() {
        return create(new Bundle());
    }

    public static BundleHelper create(@Nullable Bundle bundle) {
        return new BundleHelper(bundle);
    }

    public Bundle get() {
        return this.bundle;
    }

    public BundleHelper putAll(Bundle bundle) {
        this.bundle.putAll(bundle);
        return this;
    }

    public BundleHelper remove(String key) {
        this.bundle.remove(key);
        return this;
    }

    public BundleHelper clear() {
        this.bundle.clear();
        return this;
    }

    public BundleHelper putBundle(@Nullable String key, @Nullable Bundle value) {
        this.bundle.putBundle(key, value);
        return this;
    }

    public BundleHelper putSerializable(@Nullable String key, @Nullable Serializable value) {
        this.bundle.putSerializable(key, value);
        return this;
    }

    public BundleHelper putFloat(@Nullable String key, float value) {
        this.bundle.putFloat(key, value);
        return this;
    }

    public BundleHelper putDouble(@Nullable String key, double value) {
        this.bundle.putDouble(key, value);
        return this;
    }

    public BundleHelper putString(@Nullable String key, @Nullable String value) {
        this.bundle.putString(key, value);
        return this;
    }

    public BundleHelper putBoolean(@Nullable String key, boolean value) {
        this.bundle.putBoolean(key, value);
        return this;
    }

    public BundleHelper putByte(@Nullable String key, byte value) {
        this.bundle.putByte(key, value);
        return this;
    }

    public BundleHelper putChar(@Nullable String key, char value) {
        this.bundle.putChar(key, value);
        return this;
    }

    public BundleHelper putShort(@Nullable String key, short value) {
        this.bundle.putShort(key, value);
        return this;
    }

    public BundleHelper putInt(@Nullable String key, int value) {
        this.bundle.putInt(key, value);
        return this;
    }

    public BundleHelper putLong(@Nullable String key, long value) {
        this.bundle.putLong(key, value);
        return this;
    }

    public BundleHelper putParcelable(@Nullable String key, @Nullable Parcelable value) {
        this.bundle.putParcelable(key, value);
        return this;
    }

    public BundleHelper putParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends Parcelable> value) {
        this.bundle.putParcelableArrayList(key, value);
        return this;
    }

    public BundleHelper putSparseParcelableArray(@Nullable String key, @Nullable SparseArray<? extends Parcelable> value) {
        this.bundle.putSparseParcelableArray(key, value);
        return this;
    }

    public BundleHelper putParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        this.bundle.putParcelableArray(key, value);
        return this;
    }

    public BundleHelper putStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        this.bundle.putStringArrayList(key, value);
        return this;
    }

    public BundleHelper putIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        this.bundle.putIntegerArrayList(key, value);
        return this;
    }

    public String toString() {
        return "BundleHelper{bundle=" + this.bundle + '}';
    }

}
