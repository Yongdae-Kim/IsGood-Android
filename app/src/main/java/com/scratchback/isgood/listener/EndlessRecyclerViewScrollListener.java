package com.scratchback.isgood.listener;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by useruser on 16. 2. 16..
 */
public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int startingPageIndex = 0;
    private LinearLayoutManager layoutManager;

    public EndlessRecyclerViewScrollListener() {
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {

        layoutManager = (LinearLayoutManager) view
                .getLayoutManager();

        int lastVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        int visibleItemCount = view.getChildCount();
        int totalItemCount = layoutManager.getItemCount();

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        if (!loading && (lastVisibleItemPosition + visibleItemCount) >= totalItemCount) {
            loading = onLoadMore(currentPage + 1, totalItemCount);
        }
    }

    public abstract boolean onLoadMore(int page, int totalItemsCount);
}