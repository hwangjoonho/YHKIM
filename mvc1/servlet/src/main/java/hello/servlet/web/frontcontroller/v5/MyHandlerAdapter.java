package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface MyHandlerAdapter {

    // 오브젝트의 인터페이스 속성 파악
    boolean supports(Object handler);


    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;


}
