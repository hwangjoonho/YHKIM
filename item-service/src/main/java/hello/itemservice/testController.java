package hello.itemservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class testController {


    @Value("${test.key}")
    private String testValue;

    @ResponseBody
    @GetMapping("/test")
    public String test(){
        System.out.println(testValue);

        return testValue;
    }
}
