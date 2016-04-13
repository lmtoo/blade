package cn.accessbright.blade.web.controller;

import cn.accessbright.blade.web.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/4/12.
 */
@RestController
@RequestMapping("/error")
public class ErrorController {


    @RequestMapping("/index")
    public Object index() {
        throw new RuntimeException("asdfasdf");
    }

//    @ExceptionHandler(RuntimeException.class)
//    public Object error(RuntimeException ex) {
//        return new JsonResponse(HttpStatus.OK.value(), ex.getMessage());
//    }
}
