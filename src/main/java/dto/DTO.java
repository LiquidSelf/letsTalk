package dto;

import java.io.Serializable;

public class DTO<T> implements Serializable {
    private static final long serialVersionUID = -8091879091924046322L;
    private T data;

    public DTO(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static DTO mk(Object obj){return new DTO(obj);}
}
