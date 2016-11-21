package com.example.lambo.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/13.
 */
public class MyListView extends ListView {
    private boolean hasH,hasF = false;
    private final static String TAG = "lambo";
    public final static String REFRESH = "REFRESH";
    public final static String RESET_H = "RESET_H";
    public final static String LOAD_MORE = "LOAD_MORE";
    public final static String RESET_F = "RESET_F";
    private final static int SCROLL_DURATION = 400;
    private final static int OFFSET_RADIO  = 2;
    private Scroller scroller;
    private String state = "";
    private TextView tvH,tvF;
    private float lastY = 0;
    private int headerH,footerH = 0;
    private LinearLayout footerView;
    private String action = "";//当前响应触摸事件view
    public MyListView(Context context) {
        super(context);
        initView(context);
    }
    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }
    private void initView(Context context) {
        scroller = new Scroller(context,new DecelerateInterpolator());
        LinearLayout headerView = new LinearLayout(context);
        tvH = new TextView(context);
        tvH.setText("HEADER");
        tvH.setPadding(10, 10, 10, 10);
        headerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setState(RESET_H);
            }
        });
        headerView.addView(tvH);
        addHeaderView(headerView);
        LinearLayout footer = new LinearLayout(context);
        footerView = new LinearLayout(context);
        tvF = new TextView(context);
        tvF.setText("FOOTER");
        if (!hasH) tvH.setVisibility(INVISIBLE);
        if (!hasF) tvF.setVisibility(GONE);
        footerView.addView(tvF);
        footer.addView(footerView);
        footer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setState(RESET_F);
            }
        });
        addFooterView(footer);
        //初始化获得头部容器高度后设置为0
        tvH.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        headerH = tvH.getHeight();
                        updateHeaderView(0);
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
        footerView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        footerH = footerView.getHeight();
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });

    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int offset = (int) (e.getRawY() - lastY);
                lastY = e.getRawY();
//                Log.d(TAG, tvH.getHeight() +"onTouchEvent: "+offset);
                if (getFirstVisiblePosition() == 0 && (tvH.getHeight() > 0 || offset > 0) && getBottomMargin(footerView) <= 0) {
                    action = REFRESH;
                    updateHeaderView(tvH.getHeight() + offset / OFFSET_RADIO);
                } else if (getLastVisiblePosition() == getCount() - 1 && (getBottomMargin(footerView) > 0 || offset < 0) && tvH.getHeight() <= 0) {
                    action = LOAD_MORE;
                    updateFooterView(getBottomMargin(footerView) - offset / OFFSET_RADIO);
                }
                break;
            default:
                lastY = -1;
                if (getFirstVisiblePosition() == 0 && action== REFRESH){
                    if (tvH.getHeight() >= headerH) {
                        setState(REFRESH);
                    } else {
                        setState(RESET_H);
                    }
                }
                if (getLastVisiblePosition() == getCount() - 1 && action == LOAD_MORE){
                    if (getBottomMargin(footerView) > footerH) {
                        setState(LOAD_MORE);
                    } else {
                        setState(RESET_F);
                    }
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    private void updateHeaderView(int height) {
        if (height<0) height = 0;//出现负数会有弹跳（回弹）
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tvH.getLayoutParams();
        lp.height = height;
        tvH.setLayoutParams(lp);
        setSelection(0);//scroll to top
        invalidate();
    }
    public void resetHeader(int height) {
        scroller.startScroll(0, tvH.getHeight(), 0, height, SCROLL_DURATION);
        invalidate();
    }
    private void updateFooterView(int height) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) footerView.getLayoutParams();
        lp.bottomMargin = height;
        footerView.setLayoutParams(lp);
//		setSelection(getCount() - 1); // scroll to bottom
        invalidate();
    }
    public void resetFooter() {
        scroller.startScroll(0, getBottomMargin(footerView), 0, -getBottomMargin(footerView), SCROLL_DURATION);
        invalidate();
    }

    public void setState(String state) {
        this.state = state;
        switch (state){
            case REFRESH:
                if(hasH) resetHeader(headerH - tvH.getHeight());
                else resetHeader(-tvH.getHeight());
                break;
            case RESET_H:
                resetHeader(-tvH.getHeight());
                break;
            case LOAD_MORE:
                resetFooter();
                break;
            case RESET_F:
                resetFooter();
                break;
            default:
                break;
        }
    }

    @Override
    public void computeScroll() {//回弹效果 中间scroller
        if(scroller.computeScrollOffset()){
            if (action == REFRESH){
                updateHeaderView(scroller.getCurrY());
            }else{
                updateFooterView(scroller.getCurrY());
            }
        }
        super.computeScroll();
    }
    public int getBottomMargin(LinearLayout ll){
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ll.getLayoutParams();
        return lp.bottomMargin;
    }

    public void setHasHF(boolean hasH,boolean hasF){
        this.hasH = hasH;
        this.hasF = hasF;
    }
}
