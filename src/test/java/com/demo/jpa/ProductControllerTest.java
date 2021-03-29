package com.demo.jpa;

import com.demo.jpa.entity.Product;
import com.demo.jpa.service.ProductService;
import com.demo.mybatis.MybatisApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisApplication.class)
@AutoConfigureMockMvc
@Slf4j
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Test
    public void create() throws Exception {

        var requestBuilder = MockMvcRequestBuilders.post("/product/");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").hasJsonPath())
                .andExpect(jsonPath("$.name").value("car"));
    }

    @Test
    public void retrieve() throws Exception {

        var product = Product.builder().name("phone").build();
        product = productService.create(product);

        var requestBuilder = MockMvcRequestBuilders.get("/product/" + product.getId());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").hasJsonPath())
                .andExpect(jsonPath("$.name").value("phone"));
    }

    @Test
    public void retrieveAll() throws Exception {

        int size = productService.getAll().size();

        var requestBuilder = MockMvcRequestBuilders.get("/product/");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(size)));
    }

    @Test
    public void update() throws Exception{

        var product = Product.builder().name("phone").build();
        product = productService.create(product);

        var requestBuilder = MockMvcRequestBuilders.put("/product/" + product.getId());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").hasJsonPath())
                .andExpect(jsonPath("$.name").value("boat"));

    }

    @Test
    public void delete() throws Exception{

        var product = Product.builder().name("phone").build();
        product = productService.create(product);

        var requestBuilder = MockMvcRequestBuilders.delete("/product/" + product.getId());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());

    }

    @After
    public void deleteAll() throws Exception {

        var requestBuilder = MockMvcRequestBuilders.delete("/product/");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }



}
