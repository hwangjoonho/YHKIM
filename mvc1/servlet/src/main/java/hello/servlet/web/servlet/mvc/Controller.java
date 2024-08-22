package hello.servlet.web.servlet.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public interface Controller {

    ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse
            response) throws Exception;
}
