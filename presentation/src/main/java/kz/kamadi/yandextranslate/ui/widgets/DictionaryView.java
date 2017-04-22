package kz.kamadi.yandextranslate.ui.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
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
        fullScroll(ScrollView.FOCUS_UP);
        CharSequence charSequence = null;
        if (dictionary.getDef() != null) {
            for (Dictionary.Def def : dictionary.getDef()) {
                if (def.getTr() != null) {
                    if (charSequence == null || !def.getText().equalsIgnoreCase(charSequence.toString())) {
                        charSequence = def.getText();
                        linearLayout.addView(createTitle(def.getText(), def.getTs()));
                    }

                    int i = 1;
                    if (def.getPos() != null) {
                        linearLayout.addView(createTextView(def.getPos(), R.color.dictionary_tr_pos, R.dimen.dictionary_tr_pos, getDimen(R.dimen.medium_padding), 0, 0, false));
                    }
                    for (Dictionary.Tr tr : def.getTr()) {
                        LinearLayout horizontalLayout = new LinearLayout(getContext());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        horizontalLayout.setLayoutParams(layoutParams);
                        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                        horizontalLayout.addView(createTextView(i + " ", R.color.dictionary_tr_syn_number, R.dimen.dictionary_tr_syn_number, 0, 0, 0, false));

                        CharSequence synCharSequence;
                        synCharSequence = getSynText(tr.getText(), tr.getGen(), R.color.dictionary_tr_syn, R.color.dictionary_tr_syn_gen, tr.getSyn() == null);

                        if (tr.getSyn() != null) {
                            for (int j = 0; j < tr.getSyn().size(); j++) {
                                synCharSequence = TextUtils.concat(synCharSequence, getSynText(tr.getSyn().get(j).getText(), tr.getSyn().get(j).getGen(),
                                        R.color.dictionary_tr_syn, R.color.dictionary_tr_syn_gen, j == tr.getSyn().size() - 1));
                            }
                        }

                        if (tr.getMean() != null) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("\n");

                            for (int j = 0; j < tr.getMean().size(); j++) {
                                if (j == 0) {
                                    stringBuilder.append("(");
                                }
                                stringBuilder.append(tr.getMean().get(j).getText());
                                if (j == tr.getMean().size() - 1) {
                                    stringBuilder.append(")");
                                } else {
                                    stringBuilder.append(", ");
                                }
                            }
                            synCharSequence = TextUtils.concat(synCharSequence, getMeanText(stringBuilder.toString(), R.color.dictionary_tr_mean));
                        }
                        horizontalLayout.addView(createTextView(synCharSequence, R.dimen.dictionary_tr_mean));
                        linearLayout.addView(horizontalLayout);

                        if (tr.getEx() != null) {
                            StringBuilder stringBuilder;
                            for (Dictionary.Def ex : tr.getEx()) {
                                stringBuilder = new StringBuilder(ex.getText());
                                stringBuilder.append(" - ");
                                for (Dictionary.Tr exTr1 : ex.getTr()) {
                                    stringBuilder.append(exTr1.getText());
                                    stringBuilder.append(" ");
                                }
                                linearLayout.addView(createTextView(stringBuilder.toString(), R.color.dictionary_ex, R.dimen.dictionary_tr_ex, 0, 0, getDimen(R.dimen.dictionary_tr_ex_padding_left), true));
                            }
                        }
                        i++;
                    }
                }
            }
        }

    }

    private SpannableString getMeanText(String text, int textColor) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(getColor(textColor)), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private SpannableString getSynText(String text, String gen, int textColor, int genColor, boolean isLast) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(text);
        if (gen != null) {
            stringBuilder.append(" ");
            stringBuilder.append(gen);
        }
        if (!isLast) {
            stringBuilder.append(", ");
        }
        SpannableString spannableString = new SpannableString(stringBuilder);
        spannableString.setSpan(new ForegroundColorSpan(getColor(textColor)), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (gen != null) {
            spannableString.setSpan(new ForegroundColorSpan(getColor(genColor)), text.length(), text.length() + gen.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableString;
    }

    private TextView createTextView(CharSequence charSequence, int dimen) {
        TextView textView = new TextView(getContext());
        textView.setTextIsSelectable(true);
        textView.setText(charSequence);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getDimen(dimen));
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    private TextView createTextView(String text, int color, int dimen, float topMargin, float bottomMargin, float leftMargin, boolean isItalic) {
        TextView textView = new TextView(getContext());
        textView.setTextIsSelectable(true);
        if (isItalic) {
            textView.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
        }
        textView.setText(text);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) topMargin;
        layoutParams.bottomMargin = (int) bottomMargin;
        layoutParams.leftMargin = (int) leftMargin;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getDimen(dimen));
        textView.setTextColor(getColor(color));
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    private TextView createTitle(String text, String transcription) {
        TextView textView = new TextView(getContext());
        SpannableString spannableString = null;
        if (transcription != null) {
            transcription = "[" + transcription + "]";
            spannableString = new SpannableString(text + " " + transcription);
            spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.dictionary_title)), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.dictionary_transcription)), text.length(), spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spannableString = new SpannableString(text);
            spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.dictionary_title)), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        textView.setText(spannableString);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getDimen(R.dimen.dictionary_title));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        textView.setTextIsSelectable(true);
        return textView;
    }

    private int getColor(int resourceId) {
        return ContextCompat.getColor(getContext(), resourceId);
    }

    private float getDimen(int resourceId) {
        return getContext().getResources().getDimension(resourceId);
    }

    public void clearView() {
        linearLayout.removeAllViews();
    }
}