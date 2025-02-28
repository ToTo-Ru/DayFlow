package cathy.chen.Exception;

import cathy.chen.Pojo.development.Result;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     *Handle Pojo convert exception
     * @param e
     * @return
     */
    @ExceptionHandler({JsonParseException.class, JsonMappingException.class, IllegalArgumentException.class, ConversionFailedException.class})
    public Result handlePojoConvertExc (Exception e) {
        e.printStackTrace();
        return Result.error("Pojo convert exception");
    }

    /**
     *Handle invalidate  format
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException e) {
        e.printStackTrace();
        return Result.error("format incorrect");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleValidationException(MethodArgumentNotValidException e) {
        // 這裏可以從 ex 中提取錯誤信息
        e.printStackTrace();
        return Result.error("format incorrect");
    }

    /**
     * For all exception
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "Busy server, please try again");
    }

}
