package hello.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        super.service(req, resp);

        // request를 inpustStream으로 받고
        ServletInputStream inputStream = request.getInputStream();

        //  그걸 json형식으로 String화 시키는 부분
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);

        //                  JSON 파싱 Jackson 라이브러리
        //          readValue : json형식 String을 Key : Value 값에 맞춰 객체에 대응시키는 메서드 ( String => dto )
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        System.out.println("helloData = " + helloData);

        System.out.println("helloData.getUsername() = " + helloData.getUsername());
        System.out.println("helloData.getUsername() = " + helloData.getAge());

        response.getWriter().write("ok");

    }


}
