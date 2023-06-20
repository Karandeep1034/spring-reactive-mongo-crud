package com.example.springreactivemongocrud.service;

import com.example.springreactivemongocrud.dto.ProductDto;
import com.example.springreactivemongocrud.repo.ProductRepository;
import com.example.springreactivemongocrud.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Flux<ProductDto> getProducts(){
        return repository.findAll()
                .map(product -> AppUtils.entityToDto(product));
    }

    public Mono<ProductDto> getProductById(String Id){
        return repository.findById(Id)
                .map(p-> AppUtils.entityToDto(p));

    }

    public Flux<ProductDto> getProductInRange(double min,double max){
        return repository.findByPriceBetween(Range.closed(min,max));
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono){
        return productDtoMono.map(productDto -> AppUtils.dtoToEntity(productDto))
                .flatMap(product -> repository.insert(product))
                .map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> updateProduct(ProductDto productDto,String Id){
        return repository.findById(Id)
                .map(product ->  AppUtils.dtoToEntity(productDto))
                .doOnNext(e->e.setId(Id))
                .flatMap(repository::save)
                .map(entity -> AppUtils.entityToDto(entity));
    }

    public Mono<Void> deleteById(String Id){
        return repository.deleteById(Id);
    }
}
