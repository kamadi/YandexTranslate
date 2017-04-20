package kz.kamadi.yandextranslate.ui.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before isLoading more.
    private int visibleThreshold = 5;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean isLoading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;

    private boolean isEnabled = true;

    LinearLayoutManager layoutManager;
    OnLoadMoreListener onLoadMoreListener;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager, OnLoadMoreListener onLoadMoreListener) {
        this.layoutManager = layoutManager;
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

        if (isEnabled && !isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
            currentPage++;
            onLoadMoreListener.onLoadMore();
            isLoading = true;
        }
    }

    public void setLoading(boolean loading) {
        this.isLoading = loading;
        Log.e("setLoading", String.valueOf(loading));
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
        Log.e("setEnabled", String.valueOf(enabled));
    }

}

