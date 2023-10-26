package com.man293.food_ordering_spoon.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
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
        if(numOfItems > 0) {
            View firstItem = this.getAdapter().getView(0, null, this);
            if(firstItem != null) {
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
}
