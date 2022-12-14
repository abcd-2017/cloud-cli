package com.core.Handler;

import com.alibaba.fastjson.JSON;
import com.core.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lk
 * @date 2022-12-10
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * @param exception 捕获异常
     * @return 返回值
     * 非法参数验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<String> resultList = new ArrayList<>();

        //所有参数校验结果
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(item -> resultList.add(item.getDefaultMessage()));

        Collections.sort(resultList);
        String msg = "参数不能为空";
        if (resultList.size() > 0) {
            msg = resultList.get(0);
        }
        log.error("参数校验失败，msg：{}", JSON.toJSONString(resultList));
        return Result.fail(msg);
    }
}
