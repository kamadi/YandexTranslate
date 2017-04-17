package kz.kamadi.yandextranslate.ui.language;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.data.entity.Language;

public class LanguageAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final int DEFAULT = 0;
    private static final int SELECTED = 1;

    private Context context;
    private List<Language> languages;
    private Language selectedLanguage;
    private LayoutInflater inflater;

    public LanguageAdapter(Context context, List<Language> languages, Language selectedLanguage) {
        this.context = context;
        this.languages = languages;
        inflater = LayoutInflater.from(context);
        this.selectedLanguage = selectedLanguage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SELECTED)
            return new SelectedViewHolder(inflater.inflate(R.layout.list_item_language_selected, parent, false));
        return new ViewHolder(inflater.inflate(R.layout.list_item_language, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        if (languages.get(position).equals(selectedLanguage)){
            return SELECTED;
        }
        return DEFAULT;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(languages.get(position));
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }
}
