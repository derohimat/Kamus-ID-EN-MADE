package com.derohimat.kamusiden.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by denirohimat on 27/03/18.
 */

public class WordDao implements Parcelable {
    public static final Creator<WordDao> CREATOR = new Creator<WordDao>() {
        @Override
        public WordDao createFromParcel(Parcel source) {
            return new WordDao(source);
        }

        @Override
        public WordDao[] newArray(int size) {
            return new WordDao[size];
        }
    };
    private int id;
    private String word;
    private String meaning;

    public WordDao(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    public WordDao(int id, String word, String meaning) {
        this.id = id;
        this.word = word;
        this.meaning = meaning;
    }

    public WordDao() {
    }

    protected WordDao(Parcel in) {
        this.id = in.readInt();
        this.word = in.readString();
        this.meaning = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.word);
        dest.writeString(this.meaning);
    }
}
