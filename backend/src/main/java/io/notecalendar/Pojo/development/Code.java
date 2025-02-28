package io.notecalendar.Pojo.development;

public enum Code {
    SUCCESS(200, "success"),
    ERROR(404, "error");

    private final int code;
    private final String message;


    Code(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
