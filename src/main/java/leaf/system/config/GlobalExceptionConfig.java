package leaf.system.config;

import leaf.common.Log;
import leaf.common.object.JSONMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常配置
 */
//@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionConfig {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleException(Exception e) {
//        // 进行自定义的异常处理逻辑，例如记录日志、返回特定的错误信息等
//        lyf.common.Log.write("Error",lyf.common.Log.getException(e));
//        // 例如，将异常信息作为响应体返回
//        //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONMap.error("接口执行失败！Error:" + e.getMessage()).toString());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONMap.error("系统繁忙").toString());
//    }
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        // 进行自定义的异常处理逻辑，例如记录日志、返回特定的错误信息等
        Log.write("Error",Log.getException(e));
    }

    //拦截异常  Request method 'POST/GET' not supported
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return JSONMap.error("请求失败，可能是请求方式有误").toString();
    }
    //拦截异常  Content type 'XXX' not supported
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUnknownContentTypeException(HttpMediaTypeNotSupportedException ex) {
        return JSONMap.error("请求失败，可能是Content-Type与参数不符合").toString();
    }
}
