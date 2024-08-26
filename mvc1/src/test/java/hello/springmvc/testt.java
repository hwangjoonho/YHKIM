package hello.springmvc;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : jhhwa
 * @filepath : hello.springmvc
 * @fileName : testt
 * @date : 2023-12-28
 * @description :
 * ===========================================================
 * DATE              AUTHOR             VERSION
 * -----------------------------------------------------------
 * 2023-12-28        jhhwa       1.0
 */
class testt {

    @Test
    public void test() {
        var a = 1;
        var b = 1;
        System.out.println(a=b);
        System.out.println("a=b");
        System.out.println(a==b);
        System.out.println("a==b");

        int a1 = 1;
        int b1 = 1;
        System.out.println(a1==b1);
        System.out.println(a1=b1);

        Integer aa = 1;
        Integer bb = 1;
        System.out.println(aa==bb);
        System.out.println(aa = bb);

        String k = "1";
        String j = "1";
        System.out.println(k==j);
        System.out.println(k=j);

        Map q = new HashMap<>();
        Map q1 = new HashMap<>();
        System.out.println(q==q1);
        System.out.println(q = q1);

    }
}
