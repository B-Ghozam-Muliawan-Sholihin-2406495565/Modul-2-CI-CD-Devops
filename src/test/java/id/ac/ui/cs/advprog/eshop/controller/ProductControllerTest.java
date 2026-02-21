package id.ac.ui.cs.advprog.eshop.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(67);
    }

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPostSuccess() throws Exception {
        mockMvc.perform(post("/product/create")
                .flashAttr("product", product)) 
                .andExpect(status().is3xxRedirection()) 
                .andExpect(redirectedUrl("list")); 
        verify(service, times(1)).create(any(Product.class));
    }

    @Test
    void testCreateProductPostFailEmptyName() throws Exception {
        product.setProductName("");
        product.setProductQuantity(67);
        mockMvc.perform(post("/product/create")
                .flashAttr("product", product)) 
                .andExpect(status().isOk()) 
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("errorMessage")); 
        verify(service, times(0)).create(any(Product.class));
    }

    @Test
    void testCreateProductPostFailNegativeQuantity() throws Exception {
        product.setProductQuantity(-1);
        mockMvc.perform(post("/product/create")
                .flashAttr("product", product)) 
                .andExpect(status().isOk()) 
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("errorMessage")); 
        verify(service, times(0)).create(any(Product.class));
    }

    @Test
    void testEditProductPage() throws Exception {
        when(service.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(product);
        
        mockMvc.perform(get("/product/edit/eb558e9f-1c39-460e-8860-71af6af63bd6"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"));
        
        verify(service, times(1)).findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testEditProductPostSuccess() throws Exception {
        mockMvc.perform(post("/product/edit")
                .flashAttr("product", product)) 
                .andExpect(status().is3xxRedirection()) 
                .andExpect(redirectedUrl("list")); 
        verify(service, times(1)).edit(any(Product.class));
    }


    @Test
    void testEditProductPostFailEmptyName() throws Exception {
        product.setProductName("");
        product.setProductQuantity(67);
        mockMvc.perform(post("/product/edit")
                .flashAttr("product", product)) 
                .andExpect(status().isOk()) 
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("errorMessage")); 
        verify(service, times(0)).edit(any(Product.class));
    }

    @Test
    void testEditProductPostFailNegativeQuantity() throws Exception {
        product.setProductQuantity(-1);
        mockMvc.perform(post("/product/edit")
                .flashAttr("product", product)) 
                .andExpect(status().isOk()) 
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("errorMessage")); 
        verify(service, times(0)).edit(any(Product.class));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(get("/product/delete/eb558e9f-1c39-460e-8860-71af6af63bd6"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));
        
        verify(service, times(1)).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testProductListPage() throws Exception {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        
        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productList.add(product2);
        
        when(service.findAll()).thenReturn(productList);
        
        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"));
        
        verify(service, times(1)).findAll();
    }
}
