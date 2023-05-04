package com.fox.factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fox.factory.controllres.OrderController;
import com.fox.factory.entities.Order;
import com.fox.factory.entities.dto.CatrgoryDto;
import com.fox.factory.entities.dto.OrderDto;
import com.fox.factory.entities.dto.OrderItemInListDto;
import com.fox.factory.entities.dto.ProductImageDto1;
import com.fox.factory.service.MyEmailService;
import com.fox.factory.service.OrderService;
import com.fox.factory.service.mappers.OrderMapper;
import com.fox.factory.service.mappers.OrderMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerTests {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void testCreateProduct() throws JsonProcessingException {
        // Customize the request body based on your ProductDetailDto object
        String requestBody = "{ \"price\": 90, \"name\": \"Dark milk chocolate 10kg\", \"description\": \"string\", \"catrgories\": [{\"id\": 2, \"title\": \"syroup\", \"slug\": \"syroup\"}]}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(requestBody);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/products/new", jsonNode, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testfilterProduct() throws JsonProcessingException {
        String requestBody = "";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(requestBody);
        // Customize the request body based on your ProductDetailDto object
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/products/filter/both?title=milk",jsonNode, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void testcommentProduct() throws JsonProcessingException {
        String requestBody = "{\n" +
                "  \"text\": \"aaaaa\",\n" +
                "  \"author\": {\n" +
                "    \"id\": 3,\n" +
                "    \"username\": \"Allaaa\",\n" +
                "    \"role\": \"ADMIN\"\n" +
                "  },\n" +
                "  \"rate\": 4,\n" +
                "  \"published\": true\n" +
                "}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(requestBody);
        // Customize the request body based on your ProductDetailDto object
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/products/3/comments",jsonNode, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    // Add similar tests for other endpoints
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveCategory() throws Exception {
        CatrgoryDto catrgoryDto = new CatrgoryDto(3L, "catrgory", "catrgory");
        String requestBody = objectMapper.writeValueAsString(catrgoryDto);
        mockMvc.perform(post("/api/category/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    void testAllCats() throws Exception {
        mockMvc.perform(get("/api/category/all"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCategory() throws Exception {
        mockMvc.perform(delete("/api/category/1"))
                .andExpect(status().isForbidden());
    }


    @Test
    void testCreateAndAddImageToProduct_returnsHttpStatusOk() throws Exception {
        String filename = "test-image.jpeg";
        byte[] content = "test-image-content".getBytes();
        MockMultipartFile image = new MockMultipartFile("image", filename, MediaType.IMAGE_JPEG_VALUE, content);
        ProductImageDto1 imageDto = new ProductImageDto1(1L, filename, "121212121", "jpg");

        Set<ProductImageDto1> productImageSet = new HashSet<>();
        productImageSet.add(imageDto);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/images/1").file(image))
                .andExpect(status().isOk());
    }


    @Test
    void testViewImage_returnsHttpStatusOk() throws Exception {
        mockMvc.perform(get("/api/images/1"))
                .andExpect(status().isOk());

    }
    @Test
    public void addItem_shouldReturnHttpStatusOk() throws Exception {
        // given
        OrderItemInListDto item = new OrderItemInListDto(1L,1,null,10F);
        // when and then
        mockMvc.perform(post("/api/orders/1/items/add")
                        .content(asJsonString(item))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private MyEmailService emailService;
    @Mock
    private OrderMapper orderMapper = new OrderMapperImpl();
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        orderController = new OrderController(orderService, orderMapper);
    }

    @Test
    public void createOrder() throws Exception {
        mockMvc.perform(post("/api/orders/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteOrder() throws Exception {

        mockMvc.perform(post("/api/orders/{id}/delete", 10000)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getOrder() throws Exception {


        mockMvc.perform(get("/api/orders/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllOrdersForUser() throws Exception {


        mockMvc.perform(get("/api/orders/{uid}/user", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateOrderStatus() throws Exception {

        mockMvc.perform(put("/api/orders/{id}/change", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }


}