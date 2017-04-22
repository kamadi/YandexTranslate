package kz.kamadi.yandextranslate.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

public class Translation implements Parcelable{
    private String text;
    private String language;
    private Dictionary dictionary;
    private Translate translate;
    private History history;

    public Translation() {
    }

    public Translation(History history) {
        this.history = history;
        this.translate = history.getTranslate();
        this.dictionary = history.getDictionary();
        this.text = history.getText();
        this.language = history.getLanguage();
    }

    protected Translation(Parcel in) {
        text = in.readString();
        language = in.readString();
        translate = in.readParcelable(Translate.class.getClassLoader());
        history = in.readParcelable(History.class.getClassLoader());
        String content = in.readString();
        if (content!=null) {
            dictionary = new Gson().fromJson(content, Dictionary.class);
        }
    }

    public static final Creator<Translation> CREATOR = new Creator<Translation>() {
        @Override
        public Translation createFromParcel(Parcel in) {
            return new Translation(in);
        }

        @Override
        public Translation[] newArray(int size) {
            return new Translation[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Translate getTranslate() {
        return translate;
    }

    public void setTranslate(Translate translate) {
        this.translate = translate;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(language);
        dest.writeParcelable(translate, flags);
        dest.writeParcelable(history, flags);
        dest.writeString(dictionary.getContent());
    }
}
