package dto.websocket;

public class WSMessage<T> {

    private T _data;
    private String _token;
    private boolean _isPing;

    public WSMessage() {
    }

    public T get_data() {
        return _data;
    }

    public void set_data(T _data) {
        this._data = _data;
    }

    public String get_token() {
        return _token;
    }

    public void set_token(String _token) {
        this._token = _token;
    }

    public boolean get_isPing() {
        return _isPing;
    }

    public void set_isPing(boolean _isPing) {
        this._isPing = _isPing;
    }
}
