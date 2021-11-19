package com.example.Backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootConfiguration 
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void addition() {
        assertEquals(2, 1 + 1);
    }



}
