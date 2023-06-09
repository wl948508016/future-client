package com.future.components.ui.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
  *
  * @Description:
  * @Author:         future
  * @CreateDate:     2017/6/29 16:18
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int verticalSpacing;
    private final int horizontalSpacing;
    private final boolean includeEdge;

    public GridSpacingItemDecoration(int spacing, boolean includeEdge) {
        this.verticalSpacing = spacing;
        this.horizontalSpacing = spacing;
        this.includeEdge = includeEdge;
    }

    public GridSpacingItemDecoration(int horizontalSpacing, int verticalSpacing, boolean includeEdge) {
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        GridLayoutManager gridLayoutManager= (GridLayoutManager) parent.getLayoutManager();
        int spanCount=gridLayoutManager.getSpanCount();
        int column = position % spanCount; // item column
        if (includeEdge) {
            outRect.left = horizontalSpacing - column * horizontalSpacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * horizontalSpacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
            if (position < spanCount) { // top edge
                outRect.top = verticalSpacing;
            }
            outRect.bottom = verticalSpacing; // item bottom
        } else {
            outRect.left = column * horizontalSpacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = horizontalSpacing - (column + 1) * horizontalSpacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = verticalSpacing; // item top
            }
        }
    }
}