package com.man293.food_ordering_spoon.views.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListViewComponent extends ListView {

    public ListViewComponent(Context context) {
        super(context);
    }

    public ListViewComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ListViewComponent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setFullHeight() {
        int numOfItems = this.getAdapter().getCount();
        if (numOfItems > 0) {
            View firstItem = this.getAdapter().getView(0, null, this);
            if (firstItem != null) {
                firstItem.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                final int GAP = this.getDividerHeight();
                this.getLayoutParams().height =
                        (firstItem.getMeasuredHeight() + GAP) * numOfItems;
            }
        } else {
            this.getLayoutParams().height = 0;
        }
        this.requestLayout();
    }

    public void setFullHeight2() {
        int numOfItems = this.getAdapter().getCount();
        if (numOfItems > 0) {
            View firstItem = this.getAdapter().getView(0, null, this);
            if (firstItem != null) {
                firstItem.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                final int GAP = this.getDividerHeight();
                int totalHeight = (firstItem.getMeasuredHeight() + GAP) * numOfItems;
                this.getLayoutParams().height = totalHeight;
            }
        } else {
            this.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        this.requestLayout();
    }
}
