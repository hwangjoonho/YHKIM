package hello.servlet.web.frontcontroller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class MyView {
    private String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }
    // jsp 를 렌더링한다는 의미.
    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        // RequestDispacher 를 사용해야 jsp에서 ${} 축약형 패턴을 사용할 수 있다.
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);

    }
    // model 객체 활용하도록 오버로딩
    public void render(Map<String, Object> model, HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        modelToRequestAttribute(model, request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    // 작업한 model 객체를 request 객체로 변환후 response
    private void modelToRequestAttribute(Map<String, Object> model,
                                         HttpServletRequest request) {
        model.forEach((key, value) -> request.setAttribute(key, value));
    }
}
