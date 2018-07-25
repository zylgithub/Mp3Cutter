package com.zyl.mp3cutter.skin;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StyleRes;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.zyl.mp3cutter.R;

import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatBackgroundHelper;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;
import skin.support.widget.SkinCompatThemeUtils;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

/**
 * Description: com.zyl.mp3cutter.skin
 * Created by zouyulong on 2018/5/9.
 * Job number:147490
 * Phone : 15810880928
 * Email : zouyulong@syswin.com
 * Person in charge :  zouyulong
 */

public class CustomSkinMaterialNavigationView extends NavigationView implements SkinCompatSupportable {
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};
    private static final int[] DISABLED_STATE_SET = {-android.R.attr.state_enabled};
    private int mItemBackgroundResId = INVALID_ID;
    private int mTextColorResId = INVALID_ID;
    private int mDefaultTintResId = INVALID_ID;
    private int mIconTintResId = INVALID_ID;
    private SkinCompatBackgroundHelper mBackgroundTintHelper;

    public CustomSkinMaterialNavigationView(Context context) {
        this(context, null);
    }

    public CustomSkinMaterialNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSkinMaterialNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, 0);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavigationView, defStyleAttr,
                R.style.Widget_Design_NavigationView);
        if (a.hasValue(R.styleable.NavigationView_itemIconTint)) {
            mIconTintResId = a.getResourceId(R.styleable.NavigationView_itemIconTint, INVALID_ID);
        } else {
            mDefaultTintResId = SkinCompatThemeUtils.getColorPrimaryResId(context);
        }
        if (a.hasValue(R.styleable.NavigationView_itemTextAppearance)) {
            int textAppearance = a.getResourceId(R.styleable.NavigationView_itemTextAppearance, INVALID_ID);
            if (textAppearance != INVALID_ID) {
                TypedArray ap = context.obtainStyledAttributes(textAppearance, R.styleable.SkinTextAppearance);
                if (ap.hasValue(R.styleable.SkinTextAppearance_android_textColor)) {
                    mTextColorResId = ap.getResourceId(R.styleable.SkinTextAppearance_android_textColor, INVALID_ID);
                }
                ap.recycle();
            }
        }
        if (a.hasValue(R.styleable.NavigationView_itemTextColor)) {
            mTextColorResId = a.getResourceId(R.styleable.NavigationView_itemTextColor, INVALID_ID);
        } else {
            mDefaultTintResId = SkinCompatThemeUtils.getColorPrimaryResId(context);
        }
        if (mTextColorResId == INVALID_ID) {
            mTextColorResId = SkinCompatThemeUtils.getTextColorPrimaryResId(context);
        }
        mItemBackgroundResId = a.getResourceId(R.styleable.NavigationView_itemBackground, INVALID_ID);
        a.recycle();
        applyItemIconTintResource();
        applyItemTextColorResource();
        applyItemBackgroundResource();
    }

    @Override
    public void setItemBackgroundResource(@DrawableRes int resId) {
        super.setItemBackgroundResource(resId);
        mItemBackgroundResId = resId;
        applyItemBackgroundResource();
    }

    private void applyItemBackgroundResource() {
        mItemBackgroundResId = SkinCompatHelper.checkResourceId(mItemBackgroundResId);
        if (mItemBackgroundResId == INVALID_ID) {
            return;
        }
        Drawable drawable = SkinCompatResources.getDrawableCompat(getContext(), mItemBackgroundResId);
        if (drawable != null) {
            setItemBackground(drawable);
        }
    }

    @Override
    public void setItemTextAppearance(@StyleRes int resId) {
        super.setItemTextAppearance(resId);
        if (resId != INVALID_ID) {
            TypedArray a = getContext().obtainStyledAttributes(resId, R.styleable.SkinTextAppearance);
            if (a.hasValue(R.styleable.SkinTextAppearance_android_textColor)) {
                mTextColorResId = a.getResourceId(R.styleable.SkinTextAppearance_android_textColor, INVALID_ID);
            }
            a.recycle();
            applyItemTextColorResource();
        }
    }

    private void applyItemTextColorResource() {
        mTextColorResId = SkinCompatHelper.checkResourceId(mTextColorResId);
        if (mTextColorResId != INVALID_ID) {
            setItemTextColor(SkinCompatResources.getColorStateList(getContext(), mTextColorResId));
        } else {
            mDefaultTintResId = SkinCompatHelper.checkResourceId(mDefaultTintResId);
            if (mDefaultTintResId != INVALID_ID) {
                setItemTextColor(createDefaultColorStateList(android.R.attr.textColorPrimary));
            }
        }
    }

    private void applyItemIconTintResource() {
        mIconTintResId = SkinCompatHelper.checkResourceId(mIconTintResId);
        if (mIconTintResId != INVALID_ID) {
            setItemIconTintList(SkinCompatResources.getColorStateList(getContext(), mIconTintResId));
        } else {
            mDefaultTintResId = SkinCompatHelper.checkResourceId(mDefaultTintResId);
            if (mDefaultTintResId != INVALID_ID) {
                setItemIconTintList(createDefaultColorStateList(android.R.attr.textColorSecondary));
            }
        }
    }

    private ColorStateList createDefaultColorStateList(int baseColorThemeAttr) {
        final TypedValue value = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(baseColorThemeAttr, value, true)) {
            return null;
        }
        ColorStateList baseColor = SkinCompatResources.getColorStateList(getContext(), value.resourceId);

        int colorPrimary = SkinCompatResources.getColor(getContext(), mDefaultTintResId);
        int defaultColor = baseColor.getDefaultColor();
        return new ColorStateList(new int[][]{
                DISABLED_STATE_SET,
                CHECKED_STATE_SET,
                EMPTY_STATE_SET
        }, new int[]{
                baseColor.getColorForState(DISABLED_STATE_SET, defaultColor),
                colorPrimary,
                defaultColor
        });
    }

    @Override
    public void applySkin() {
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.applySkin();
        }
//        applyItemIconTintResource();
        applyItemTextColorResource();
        applyItemBackgroundResource();
    }
}