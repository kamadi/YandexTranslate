package kz.kamadi.yandextranslate.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class History implements Parcelable {

    private Integer id;
    private String text;
    private boolean isFavourite;
    private int status;
    private String language;
    private Translate translate;
    private Dictionary dictionary;

    public History() {
    }

    protected History(Parcel in) {
        text = in.readString();
        isFavourite = in.readByte() != 0;
        status = in.readInt();
        language = in.readString();
        translate = in.readParcelable(Translate.class.getClassLoader());
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Translate getTranslate() {
        return translate;
    }

    public void setTranslate(Translate translate) {
        this.translate = translate;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeByte((byte) (isFavourite ? 1 : 0));
        dest.writeInt(status);
        dest.writeString(language);
        dest.writeParcelable(translate, flags);
    }
}
