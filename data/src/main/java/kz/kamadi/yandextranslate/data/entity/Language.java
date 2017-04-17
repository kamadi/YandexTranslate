package kz.kamadi.yandextranslate.data.entity;


import android.os.Parcel;
import android.os.Parcelable;

public class Language implements Parcelable{

    private String code;
    private String name;

    public Language() {
    }

    public Language(String name, String code) {
        this.code = code;
        this.name = name;
    }

    public Language(Language language) {
        this.name = language.getName();
        this.code = language.getCode();
    }

    protected Language(Parcel in) {
        code = in.readString();
        name = in.readString();
    }

    public static final Creator<Language> CREATOR = new Creator<Language>() {
        @Override
        public Language createFromParcel(Parcel in) {
            return new Language(in);
        }

        @Override
        public Language[] newArray(int size) {
            return new Language[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Language language = (Language) o;

        return code != null ? code.equals(language.code) : language.code == null;

    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }
}
