package net.ichigotake.android.yancha.app.core;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ChatMessage implements Parcelable {

    public static List<ChatMessage> fromSearchApiResponse(String json) {
        List<ChatMessage> messages = new Gson().fromJson(json, new TypeToken<List<ChatMessage>>() {}.getType());
        if (messages == null) {
            messages = new ArrayList<ChatMessage>();
        }
        return messages;
    }

    public String created_at_datetime;
    public long created_at_ms;
    public int id;
    public int plusplus;
    public String nickname;
    public String profile_image_url;
    public String profile_url;
    public ArrayList<String> tags;
    public String text;
    public String full_text;
    public String user_key;

    public ChatMessage() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.created_at_datetime);
        dest.writeLong(this.created_at_ms);
        dest.writeInt(this.id);
        dest.writeInt(this.plusplus);
        dest.writeString(this.profile_image_url);
        dest.writeString(this.profile_url);
        dest.writeSerializable(this.tags);
        dest.writeString(this.text);
        dest.writeString(this.user_key);
    }

    private ChatMessage(Parcel in) {
        this.created_at_datetime = in.readString();
        this.created_at_ms = in.readLong();
        this.id = in.readInt();
        this.plusplus = in.readInt();
        this.profile_image_url = in.readString();
        this.profile_url = in.readString();
        this.tags = (ArrayList<String>) in.readSerializable();
        this.text = in.readString();
        this.user_key = in.readString();
    }

    @Override
    public String toString() {
        return full_text;
    }

    public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
        public ChatMessage createFromParcel(Parcel source) {
            return new ChatMessage(source);
        }

        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };
}
