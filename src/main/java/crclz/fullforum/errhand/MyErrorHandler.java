package crclz.fullforum.errhand;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MyErrorHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ApiError handleBadRequestException(HttpServletRequest req, BadRequestException e) {
        return e.toApiError();
    }
}
