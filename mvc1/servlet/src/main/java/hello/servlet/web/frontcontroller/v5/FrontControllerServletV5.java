package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

//    지금까지 우리가 개발한 프론트 컨트롤러는 한가지 방식의 컨트롤러 인터페이스만 사용할 수 있다.
//    ControllerV3 , ControllerV4 는 완전히 다른 인터페이스이다. 따라서 호환이 불가능하다. 마치 v3는
//      110v이고, v4는 220v 전기 콘센트 같은 것이다. 이럴 때 사용하는 것이 바로 어댑터이다


    // 모든 속성 처리 가능하도록 Object로 받는다. - 여기는 정확한 위치 포함
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    // 서로 다른 속성은 List에 넣고 관리한다.  -  여기는 구별할 기준 속성 리스트들
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }
//  핸들러어댑터 목록에 들어갈 dispatch 위치 세팅
    private void initHandlerMappingMap() {
        System.out.println( "이게 먼저 실행되는건가봐?");
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        // V4   추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

//  핸들러 어댑터 목록 정의
    private void initHandlerAdapters() {

        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }


// --=------------------------------------세팅값 설정 완료----------------------------------------------


    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("세팅완료후 로직 실행");

        // initHandlerMappingMap의 request URI에 맞는 handler 속성 '객체' 반환
        // handler = MemberFormControllerV3
        Object handler = getHandler(request);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // 여기까지 handler를 통해 자식 속성 객체 파악 완료
        // 이후 로직은 handler로 정해진 자식객체에 해당하는 부모 객체 파악

        // handler에 맞는 handlerAdapter 찾기(for문 돌며 속성 해당 확인)
        // adapter = ControllerV3HandlerAdapter
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        ModelView mv = adapter.handle(request, response, handler);

        MyView view = viewResolver(mv.getViewName());
        view.render(mv.getModel(), request, response);
    }

//  요청이 가지고 있는 목적지 확인
        // 자식 인터페이스 속성 확인 로직
    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        // FrontControllerServletV5()이 먼저 실행되어 handlerMappingMap에 adapt할 값이 넣어진 상태
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        // 부모 인터페이스 속성 확인 로직
        // 속성 해당 확인 - 그 객체에 해당하는 supports 메소드실행
        // 여기서 adapter = 해당 속성 인스턴스
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler = " + handler);
    }

    //    논리적 주소값만 사용할 수 있도록 공통 주소값 객체화
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

}

