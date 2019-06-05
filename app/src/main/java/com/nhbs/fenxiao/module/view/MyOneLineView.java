package com.nhbs.fenxiao.module.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.utils.DensityUtils;

public class MyOneLineView extends LinearLayout {
    /**
     * 上下分割线，默认隐藏上面分割线
     */
    private View dividerTop, dividerBottom;

    /**
     * 最外层容器
     */
    private LinearLayout llRoot;
    /**
     * 最左边的Icon
     */
    private ImageView ivLeftIcon;
    /**
     * 中间的文字内容
     */
    private TextView tvTextContent;

    /**
     * 右边的icon 通常是箭头
     */
    private ImageView ivRightIcon;


    /**
     * 整个一行被点击
     */
    public static interface OnRootClickListener {
        void onRootClick(View view);
    }


    /**
     * 右边箭头的点击事件
     */
    public static interface OnArrowClickListener {
        void onArrowClick(View view);
    }

    public MyOneLineView(Context context) {
        super(context);
    }

    public MyOneLineView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    /**
     * 初始化各个控件
     */
    public MyOneLineView init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_my_one_line, this, true);
        llRoot = (LinearLayout) findViewById(R.id.ll_root);
        dividerTop = findViewById(R.id.divider_top);
        dividerBottom = findViewById(R.id.divider_bottom);
        ivLeftIcon = (ImageView) findViewById(R.id.iv_left_icon);
        tvTextContent = (TextView) findViewById(R.id.tv_text_content);
        ivRightIcon = (ImageView) findViewById(R.id.iv_right_icon);
        return this;
    }

    /**
     * 文字 + 箭头
     *
     * @param textContent
     * @return
     */
    public MyOneLineView init(String textContent) {
        init();
        setTextContent(textContent);
        showLeftIcon(false);
        return this;
    }

    /**
     * 默认情况下的样子  icon + 文字 + 右箭头 + 下分割线
     *
     * @param iconRes     icon图片
     * @param textContent 文字内容
     */
    public MyOneLineView init(int iconRes, String textContent) {
        init();
        showDivider(false, true);
        setLeftIcon(iconRes);
        setTextContent(textContent);
        showArrow(true);
        return this;
    }

    /**
     * 我的页面每一行  icon + 文字 + 右箭头（显示/不显示） + 右箭头左边的文字（显示/不显示）+ 下分割线
     *
     * @param iconRes     icon图片
     * @param textContent 文字内容
     */
    public MyOneLineView initMine(int iconRes, String textContent, boolean showArrow,boolean showBottomLine) {
        init(iconRes, textContent);
        showDivider(false, showBottomLine);
        showArrow(showArrow);
        return this;
    }

    //---------------------下面是对每个部分的一些设置     上面是应用中常用场景封装-----------------------//

    /**
     * 设置root的paddingTop 与 PaddingBottom 从而控制整体的行高
     * paddingLeft 与 paddingRight 保持默认 20dp
     */
    public MyOneLineView setRootPaddingTopBottom(int paddintTop, int paddintBottom) {
        llRoot.setPadding(DensityUtils.dp2px(getContext(), 20),
                DensityUtils.dp2px(getContext(), paddintTop),
                DensityUtils.dp2px(getContext(), 20),
                DensityUtils.dp2px(getContext(), paddintBottom));
        return this;
    }

    /**
     * 设置root的paddingLeft 与 PaddingRight 从而控制整体的行高
     * <p>
     * paddingTop 与 paddingBottom 保持默认 15dp
     */
    public MyOneLineView setRootPaddingLeftRight(int paddintTop, int paddintRight) {
        llRoot.setPadding(DensityUtils.dp2px(getContext(), paddintTop),
                DensityUtils.dp2px(getContext(), 15),
                DensityUtils.dp2px(getContext(), paddintRight),
                DensityUtils.dp2px(getContext(), 15));
        return this;
    }

    /**
     * 设置上下分割线的显示情况
     *
     * @return
     */
    public MyOneLineView showDivider(Boolean showDividerTop, Boolean showDivderBottom) {
        if (showDividerTop) {
            dividerTop.setVisibility(VISIBLE);
        } else {
            dividerTop.setVisibility(GONE);
        }
        if (showDivderBottom) {
            dividerBottom.setVisibility(VISIBLE);
        } else {
            dividerBottom.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 设置上分割线的颜色
     *
     * @return
     */
    public MyOneLineView setDividerTopColor(int dividerTopColorRes) {
        dividerTop.setBackgroundColor(getResources().getColor(dividerTopColorRes));
        return this;
    }

    /**
     * 设置上分割线的高度
     *
     * @return
     */
    public MyOneLineView setDividerTopHigiht(int dividerTopHigihtDp) {
        ViewGroup.LayoutParams layoutParams = dividerTop.getLayoutParams();
        layoutParams.height = DensityUtils.dp2px(getContext(), dividerTopHigihtDp);
        dividerTop.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置下分割线的颜色
     *
     * @return
     */
    public MyOneLineView setDividerBottomColor(int dividerBottomColorRes) {
        dividerBottom.setBackgroundColor(getResources().getColor(dividerBottomColorRes));
        return this;
    }

    /**
     * 设置下分割线的左右边距
     *
     * @return
     */
    public MyOneLineView setDividerBottomMargin(int dividerBottomLeft, int dividerBottomRight) {
        LinearLayout.MarginLayoutParams params = (LinearLayout.MarginLayoutParams) dividerBottom.getLayoutParams();
        params.setMargins(DensityUtils.dp2px(getContext(), dividerBottomLeft), 0, DensityUtils.dp2px(getContext(), dividerBottomRight), 0);
        dividerBottom.setLayoutParams(params);
        return this;
    }

    /**
     * 设置上分割线的高度
     *
     * @return
     */
    public MyOneLineView setDividerBottomHigiht(int dividerBottomHigihtDp) {
        ViewGroup.LayoutParams layoutParams = dividerBottom.getLayoutParams();
        layoutParams.height = DensityUtils.dp2px(getContext(), dividerBottomHigihtDp);
        dividerBottom.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置左边Icon
     *
     * @param iconRes
     */
    public MyOneLineView setLeftIcon(int iconRes) {
        ivLeftIcon.setImageResource(iconRes);
        return this;
    }

    /**
     * 设置左边Icon显示与否
     *
     * @param showLeftIcon
     */
    public MyOneLineView showLeftIcon(boolean showLeftIcon) {
        if (showLeftIcon) {
            ivLeftIcon.setVisibility(VISIBLE);
        } else {
            ivLeftIcon.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 设置右边Icon 以及Icon的宽高
     */
    public MyOneLineView setLeftIconSize(int widthDp, int heightDp) {
        ViewGroup.LayoutParams layoutParams = ivLeftIcon.getLayoutParams();
        layoutParams.width = DensityUtils.dp2px(getContext(), widthDp);
        layoutParams.height = DensityUtils.dp2px(getContext(), heightDp);
        ivLeftIcon.setLayoutParams(layoutParams);
        return this;
    }


    /**
     * 设置中间的文字内容
     *
     * @param textContent
     * @return
     */
    public MyOneLineView setTextContent(String textContent) {
        tvTextContent.setText(textContent);
        return this;
    }

    /**
     * 设置中间的文字颜色
     *
     * @return
     */
    public MyOneLineView setTextContentColor(int colorRes) {
        tvTextContent.setTextColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置中间的文字大小
     *
     * @return
     */
    public MyOneLineView setTextContentSize(int textSizeSp) {
        tvTextContent.setTextSize(textSizeSp);
        return this;
    }

    /**
     * 设置右箭头的显示与不显示
     *
     * @param showArrow
     */
    public MyOneLineView showArrow(boolean showArrow) {
        if (showArrow) {
            ivRightIcon.setVisibility(VISIBLE);
        } else {
            ivRightIcon.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 获取右边icon
     */
    public MyOneLineView setIvRightIcon(int iconRes) {

        ivRightIcon.setImageResource(iconRes);

        return this;
    }

    /**
     * 获取右边icon
     */
    public ImageView getIvRightIcon() {

        return ivRightIcon;
    }

    //----------------------下面是一些点击事件

    public MyOneLineView setOnRootClickListener(final OnRootClickListener onRootClickListener, final int tag) {
        llRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                llRoot.setTag(tag);
                onRootClickListener.onRootClick(llRoot);
            }
        });
        return this;
    }

    public MyOneLineView setOnArrowClickListener(final OnArrowClickListener onArrowClickListener, final int tag) {

        ivRightIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ivRightIcon.setTag(tag);
                onArrowClickListener.onArrowClick(ivRightIcon);
            }
        });
        return this;
    }
}
