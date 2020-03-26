package dto.websocket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import dto.Message;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WSMessage<T> {

    private String _token;
    private MessageType _messageType;
    private final static MessageType t = MessageType.PING;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "_messageType", include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
    @JsonSubTypes(value = {
            @JsonSubTypes.Type(value = Object.class, name = "PING"),
            @JsonSubTypes.Type(value = Message.class, name = Message.type),
//            @JsonSubTypes.Type(value = BarData.class, name = "Bar")
    })
    private T _data;

    public WSMessage() {
    }

    public String get_token() {
        return _token;
    }

    public void set_token(String _token) {
        this._token = _token;
    }

    public MessageType get_messageType() {
        return _messageType;
    }

    public void set_messageType(MessageType _messageType) {
        this._messageType = _messageType;
    }

    public T get_data() {
        return _data;
    }

    public void set_data(T _data) {
        this._data = _data;
    }

    public static enum MessageType {
        PING("PING"),
        CHAT_MESSAGE(Message.type),
        FEED_MESSAGE("FEED_MESSAGE");

        public static Set<MessageType> allowedTypes = new HashSet<MessageType>();

        static {
            allowedTypes.add(PING);
            allowedTypes.add(CHAT_MESSAGE);
            allowedTypes.add(FEED_MESSAGE);
        }

        MessageType(String type) {
            this.type = type;
        }

        private final String type;

        public String getType() {
            return type;
        }

        @JsonCreator
        public static MessageType forValue(String value) {
            for(MessageType allowed :  allowedTypes){
                if(allowed.getType().equals(value)) return allowed;
            }
            throw new RuntimeException(String.format("type [$s] not allowed", value));
        }

        @JsonValue
        public String toValue() {
            for (MessageType allowed :  allowedTypes) {
                if (allowed.getType().equals(this.getType()))
                    return allowed.getType();
            }

            throw new RuntimeException(String.format("not allowed type sent [$s]", this.getType()));
        }
    }
}
