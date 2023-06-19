package com.example.springreactivemongocrud.controller;

import com.example.springreactivemongocrud.dto.ProductDto;
import com.example.springreactivemongocrud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService service;
    @GetMapping
    public Flux<ProductDto> getproducts(){
        return service.getProducts();
    }
    @GetMapping("/{id}")
    public Mono<ProductDto> getProduct(@PathVariable String id){
        return service.getProductById(id);
    }

    @GetMapping("/product-range")
    public Flux<ProductDto> getProductBetweenRange(@RequestParam("min") double min,@RequestParam("max") double max){
        return service.getProductInRange(min,max);
    }

    @PostMapping
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDtoMono){
        return service.saveProduct(productDtoMono);
    }

    @PutMapping("/update/{id}")
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDtoMono, @PathVariable String Id){
        return service.updateProduct(productDtoMono,Id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteProduct(@PathVariable String Id){
        return service.deleteById(Id);
    }

}
