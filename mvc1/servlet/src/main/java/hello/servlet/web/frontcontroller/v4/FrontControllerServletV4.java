package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("FrontControllerServletv4.service");
        // 요청이 들어온 URI 따놓기
        String requestURI = request.getRequestURI();
        // 해당 컨트롤러와 매핑 = 요청이 들어온 URI값에 따른 객체 반환
        // ControllerV4는 인터페이스지만 상속으로 인해 자식속성으로 현변환됨
        ControllerV4 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }





        // 파라미터 형식 전환
        Map<String, String> paramMap = createParamMap(request);

//        ------------------------------------
        // 로직에서 사용할 model 객체 깡통 세팅
        Map<String, Object> model = new HashMap<>();
//        ------------------------------------

        // 현재 mv 객체에 결과값 및 return 주소값 포함되어 있는 상태
        // 우측은 해당 객체에 해당하는 process 메서드 실행
        // 요청에 해당하는 자식 속성 객체에서 메서드(process) 뽑아쓰는 것
        String viewName = controller.process(paramMap, model);
        // 최종 로직후 반환값이 String
//        ------------------------------------

        // 논리 주소값 -> 물리 주소값 변환
        MyView view = viewResolver(viewName);
        // 파라미터 형식 paramMap -> request 전환 및 dispatcher 로 forward 처리
        view.render(model, request, response);
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

