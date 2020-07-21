package crclz.fullforum.controllers.errhand;

public class BadRequestException extends RuntimeException {
    private final String code;

    public BadRequestException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public ApiError toApiError() {
        return new ApiError(getCode(), getMessage());
    }
}
