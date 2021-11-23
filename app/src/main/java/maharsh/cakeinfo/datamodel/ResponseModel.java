package maharsh.cakeinfo.datamodel;

import okhttp3.ResponseBody;

public class ResponseModel<T> {
    private final T responseClass;
    private final int responseCode;
    private final ResponseBody errorBody;

    public ResponseModel(T responseClass, int responseCode, ResponseBody errorBody) {
        this.responseClass = responseClass;
        this.responseCode = responseCode;
        this.errorBody = errorBody;
    }

    public T getResponseClass() {
        return responseClass;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public ResponseBody getErrorBody() {
        return errorBody;
    }
}
