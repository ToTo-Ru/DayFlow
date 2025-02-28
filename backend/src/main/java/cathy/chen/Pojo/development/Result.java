package cathy.chen.Pojo.development;

//this is a Result that return to frontend
//Contain: code, message, data(code/ message -> Code.java(ENUM))
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public Result(Code code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public Result(Code code, T data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

//    for error, as no data will be return
    public Result(Code code, String message) {
        this.code = code.getCode();
        this.message = message;
    }


    public static <E> Result<E> success(){return new Result<>(Code.SUCCESS, null);}
    public static <E> Result<E> success(E data){
        return new Result<>(Code.SUCCESS, data);
    }

    public static <E> Result<E> success(String message, E data){return new Result<>(Code.SUCCESS, data);}

    public static <E> Result<E> error(String message){
        return new Result<>(Code.ERROR, message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}