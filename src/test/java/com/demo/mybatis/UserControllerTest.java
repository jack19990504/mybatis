package com.demo.mybatis;

import com.demo.mybatis.entity.User;
import com.demo.mybatis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    public void create() throws Exception {

        var requestBuilder = MockMvcRequestBuilders.post("/user/");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").hasJsonPath())
                .andExpect(jsonPath("$.userName").value("test"))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.age").value(35));
    }

    @Test
    public void retrieve() throws Exception {

        String uuid = UUID.randomUUID().toString();
        var user = User.builder()
                .userId(uuid)
                .userName("retrieve")
                .age(15)
                .gender("male")
                .build();

        userService.create(user);

        var requestBuilder = MockMvcRequestBuilders.get("/user/" + uuid);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").hasJsonPath())
                .andExpect(jsonPath("$.userName").value("retrieve"))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.age").value(15));
    }

    @Test
    public void retrieveAll() throws Exception {

        int size = userService.getAll().size();

        var requestBuilder = MockMvcRequestBuilders.get("/user/");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(size)));
    }

    @Test
    public void update() throws Exception{
        String uuid = UUID.randomUUID().toString();
        var user = User.builder()
                .userId(uuid)
                .userName("update")
                .age(15)
                .gender("male")
                .build();

        userService.create(user);

        var requestBuilder = MockMvcRequestBuilders.put("/user/" + uuid);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").hasJsonPath())
                .andExpect(jsonPath("$.userName").value("test_update"))
                .andExpect(jsonPath("$.gender").value("female"))
                .andExpect(jsonPath("$.age").value(20));

    }

    @Test
    public void delete() throws Exception{
        String uuid = UUID.randomUUID().toString();
        var user = User.builder()
                .userId(uuid)
                .userName("delete")
                .age(15)
                .gender("male")
                .build();

        userService.create(user);

        var requestBuilder = MockMvcRequestBuilders.delete("/user/" + uuid);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").hasJsonPath())
                .andExpect(jsonPath("$.userName").value("delete"))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.age").value(15));

    }

    @After
    public void deleteAll() throws Exception {

        var requestBuilder = MockMvcRequestBuilders.delete("/user/");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }



}
