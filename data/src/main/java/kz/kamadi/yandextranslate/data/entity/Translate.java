package kz.kamadi.yandextranslate.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collections;
import java.util.List;

public class Translate implements Parcelable{

    private List<String> text;
    private String lang;

    public Translate() {
    }

    public Translate(String text) {
        this.text = Collections.singletonList(text);
    }

    protected Translate(Parcel in) {
        text = in.createStringArrayList();
        lang = in.readString();
    }

    public static final Creator<Translate> CREATOR = new Creator<Translate>() {
        @Override
        public Translate createFromParcel(Parcel in) {
            return new Translate(in);
        }

        @Override
        public Translate[] newArray(int size) {
            return new Translate[size];
        }
    };

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTranslation() {
        if (text != null && text.size() > 0) {
            return text.get(0);
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(text);
        dest.writeString(lang);
    }
}
