package com.movienchill.mediaservice;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MediaserviceApplication.class)
@AutoConfigureMockMvc
public class MediaserviceApplicationTests {
    @Test
    void getAll() {

    }
}
