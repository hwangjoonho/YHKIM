package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("FrontControllerServletv3.service");
        // 요청이 들어온 URI 따놓기
        String requestURI = request.getRequestURI();
        // 해당 컨트롤러와 매핑
        ControllerV3 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

//        ----------------------------------------------
        // 파라미터 형식 전환
        Map<String, String> paramMap = createParamMap(request);

        // 컨트롤러에 맞는 process 로직 처리
        ModelView mv = controller.process(paramMap);
        // 최종 로직 후 반환값이 ModelView 객체

        // 현재 mv 객체에 결과값 및 return 주소값 포함되어 있는 상태
        String viewName = mv.getViewName();

//        ----------------------------------------------

        // 논리 주소값 -> 물리 주소값 변환
        MyView view = viewResolver(viewName);
        // 파라미터 형식 paramMap -> request 전환 및 dispatcher 로 forward 처리
        view.render(mv.getModel(), request, response);
    }
    
    
//    WEB에서 request 넘어온값 paramMap 형식으로 파라미터 프로토콜에 맞게 형식 전환
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName,
                        request.getParameter(paramName)));
        return paramMap;
    }

//    논리적 주소값만 사용할 수 있도록 공통 주소값 객체화
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

}

