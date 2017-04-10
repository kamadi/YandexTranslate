package kz.kamadi.yandextranslate.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.data.entity.Dictionary;

public class DictionaryView extends ScrollView {

    private LinearLayout linearLayout;
    private Dictionary dictionary;

    public DictionaryView(Context context) {
        super(context);
        initLayout();
    }


    public DictionaryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public DictionaryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_dictionary, this);
        linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout);
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
        linearLayout.removeAllViews();
        CharSequence charSequence = null;
        if (dictionary.getDef() != null) {
            for (Dictionary.Def def : dictionary.getDef()) {
                if (def.getTr() != null) {
                    if (charSequence == null || !def.getText().equalsIgnoreCase(charSequence.toString())) {
                        charSequence = def.getText();
                        linearLayout.addView(createTextView(def.getText() + def.getTs()));
                    }

                    int i = 0;
                    for (Dictionary.Tr tr : def.getTr()) {

                    }
                }
            }
        }

    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        return textView;
    }
}
