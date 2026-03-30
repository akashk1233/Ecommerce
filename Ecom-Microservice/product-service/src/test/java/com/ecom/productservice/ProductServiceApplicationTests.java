package com.ecom.productservice;

import com.ecom.productservice.dto.ProductRequest;
import com.ecom.productservice.dto.ProductResponse;
import com.ecom.productservice.repository.ProductRepo;
import com.ecom.productservice.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;
import org.testcontainers.shaded.com.google.common.base.Verify;
import tools.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.verify;
import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
//@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepo productRepo;

    @MockitoBean
    private ProductService productService;

//    @Container
//    static MySQLContainer mySQLContainer =
//            new MySQLContainer("mysql:8.4")
//                    .withDatabaseName("product-service-testdb")
//                    .withUsername("root")
//                    .withPassword("root");

//    @DynamicPropertySource
//    static void setProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", mySQLContainer::getUsername);
//        registry.add("spring.datasource.password", mySQLContainer::getPassword);
//    }
    private int uuid = UUID.randomUUID().hashCode();

    ProductResponse productResponse = ProductResponse.builder()
            .productName("samsung")
            .description("new-one")
            .price(BigDecimal.valueOf(1200))
            .build();

	@Test
	void createProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        String productReqString = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productReqString))
                .andExpect(MockMvcResultMatchers.status().isCreated());

	}

    private ProductRequest getProductRequest()
    {
        return ProductRequest.builder()
                .productName("samsung")
                .description("new-one")
                .price(BigDecimal.valueOf(1200))
                .build();
    }
    @Test
    public void should_give_product_byId() throws Exception {
        Mockito.when(productService.getProductById(uuid)).thenReturn(productResponse);
        String productResponseString = objectMapper.writeValueAsString(productResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/getProduct/{pid}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productResponseString))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertEquals("samsung",productResponse.getProductName());
        verify(productService).getProductById(uuid);

    }

}
