package kz.kamadi.yandextranslate.ui.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.data.entity.History;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
    private Context context;
    private OnHistoryUpdateListener onHistoryUpdateListener;
    private List<History> histories = new ArrayList<>();

    public HistoryAdapter(Context context, OnHistoryUpdateListener onHistoryUpdateListener) {
        this.context = context;
        this.onHistoryUpdateListener = onHistoryUpdateListener;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_history, parent, false), onHistoryUpdateListener);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.bind(histories.get(position));
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }


    public void setHistories(List<History> histories) {
        this.histories = histories;
        notifyDataSetChanged();
    }

    public void add(List<History> histories) {
        int oldSize = this.histories.size() - 1;
        this.histories.addAll(histories);
        notifyItemRangeInserted(oldSize, histories.size());
    }

    public interface OnHistoryUpdateListener {
        void onHistoryUpdate(History history);
    }
}
