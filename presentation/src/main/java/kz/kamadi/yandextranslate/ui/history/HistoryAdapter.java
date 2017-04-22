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
    private OnHistoryActionListener onHistoryActionListener;
    private List<History> histories = new ArrayList<>();

    public HistoryAdapter(Context context, OnHistoryActionListener onHistoryActionListener) {
        this.context = context;
        this.onHistoryActionListener = onHistoryActionListener;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_history, parent, false), onHistoryActionListener);
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
        int oldSize = this.histories.size();
        this.histories.addAll(histories);
        notifyItemRangeInserted(oldSize,this.histories.size());
    }

    public void deleteItem(int position) {
        this.histories.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnHistoryActionListener {

        void onHistoryItemClick(History history);

        void onHistoryUpdate(History history);

        void onHistoryDelete(History history, int position);
    }
}
