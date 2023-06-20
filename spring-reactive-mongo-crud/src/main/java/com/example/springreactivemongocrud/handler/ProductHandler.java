package com.example.springreactivemongocrud.handler;

import com.example.springreactivemongocrud.dto.ProductDto;
import com.example.springreactivemongocrud.service.ProductService;
import com.example.springreactivemongocrud.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductHandler {

    @Autowired
    private ProductService service;

    public Mono<ServerResponse> getProducts(ServerRequest req){
        return service.getProducts()
                .collectList()
                .flatMap(list -> ServerResponse.ok().bodyValue(list));
    }

    public Mono<ServerResponse> getProductById(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        return service.getProductById(id)
                .flatMap(dto -> ServerResponse.ok().bodyValue(dto));
    }

//    public Mono<ServerResponse> getProductInRange(double min,double max){
//        return service.getProductInRange(min,max)
//                .collectList()
//                .flatMap(list -> ServerResponse.ok().bodyValue(list));
//    }

    public Mono<ServerResponse> saveProduct(ServerRequest serverRequest){
        Mono<ProductDto> product = serverRequest.bodyToMono(ProductDto.class);
        return service.saveProduct(product)
                .flatMap(dto -> ServerResponse.ok().bodyValue(dto));
    }

    public Mono<ServerResponse> updateProduct(ServerRequest serverRequest){
        Mono<ProductDto> productMono = serverRequest.bodyToMono(ProductDto.class);
        return productMono.flatMap(product -> service.updateProduct(product,product.getId()))
                .flatMap(dto -> ServerResponse.ok().bodyValue(dto));
    }

    public Mono<ServerResponse> deleteById(ServerRequest serverRequest){
        String id =serverRequest.pathVariable("id");
        return service.deleteById(id)
                .flatMap(mono -> ServerResponse.ok().bodyValue(mono));
    }

}
