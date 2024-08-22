package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {

//  v5에서 속성 검증하기 위한 로직
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
    }
//    ---------------------------------------

    @Override   // V3 버전에 맞는 ModelView 반환하도록
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        ControllerV3 controller = (ControllerV3) handler;
        
        // 파라미터 타입 paramMap으로 변환 필요
        Map<String, String> paramMap = createParamMap(request);
        // V3는 ModelView 방식이므로 그에 따른 로직 필요
        ModelView mv = controller.process(paramMap);
        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
