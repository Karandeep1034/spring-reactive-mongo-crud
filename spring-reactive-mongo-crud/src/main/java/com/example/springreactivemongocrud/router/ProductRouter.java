package com.example.springreactivemongocrud.router;

import com.example.springreactivemongocrud.handler.ProductHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class ProductRouter {
    @Autowired
    private ProductHandler handler;
    @Bean
    public RouterFunction<ServerResponse> productListing() {
        return RouterFunctions.route(GET("/products"), req -> handler.getProducts(req));
    }
    @Bean
    public RouterFunction<ServerResponse> productById() {
        return RouterFunctions.route(GET("/{id}"), req -> handler.getProductById(req));
    }

//    public RouterFunction<ServerResponse> productInRange() {
//        return RouterFunctions.route(GET("{/id}"), req -> handler.getProductInRange(req));
//    }
    @Bean
    public RouterFunction<ServerResponse> saveProduct() {
        return RouterFunctions.route(POST("/add"), req -> handler.saveProduct(req));
    }
    @Bean
    public RouterFunction<ServerResponse> updateProduct() {
        return RouterFunctions.route(PUT("/update"), req -> handler.updateProduct(req));
    }
    @Bean
    public RouterFunction<ServerResponse> deleteProduct() {
        return RouterFunctions.route(DELETE("/delete"), req -> handler.deleteById(req));
    }
}
