package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {

    //  v5에서 속성 검증하기 위한 로직
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }

    // ----------------------------------------------------------

    @Override   // V 버전에 맞는 ModelView 반환하도록
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        ControllerV4 controller = (ControllerV4) handler;
        
        // 파라미터 타입 paramMap으로 변환 필요
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        // 원래는 viewName만 넣어주면 되는 V4 방식이지만 현재는 Adaptor를 사용하는 ModelView 방식이므로 그에 따른 로직 필요
        String viewName = controller.process(paramMap, model);

        // ModelView타입으로 반환해주어야하므로 변환 필요
        ModelView mv = new ModelView(viewName);
        // 
        mv.setModel(model);
        
        // 
        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
