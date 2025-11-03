package org.example.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class UserServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void makeFail(){
        int a = 1;
        assertEquals(3,a);
    }

}
