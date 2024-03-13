package team.broadcast.domain.video_room.enums;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VideoRoomJoinType {
    @SerializedName("publisher")
    PUBLISHER("1"),
    @SerializedName("subscriber")
    SUBSCRIBER("0");

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    private final String value;
}
