package com.arle.apireactiva.product.service;

import com.arle.apireactiva.product.dto.ProductRequest;
import com.arle.apireactiva.product.dto.ProductResponse;
import com.arle.apireactiva.product.mapper.ProductMapper;
import com.arle.apireactiva.product.model.Product;
import com.arle.apireactiva.product.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Flux<ProductResponse> getAll() {
        return repository.findAll().map(ProductMapper::toResponse);
    }

    public Mono<ProductResponse> getById(Long id) {
        return findExisting(id).map(ProductMapper::toResponse);
    }

    public Flux<ProductResponse> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name).map(ProductMapper::toResponse);
    }

    public Mono<ProductResponse> create(ProductRequest request) {
        Product product = ProductMapper.toEntity(request);
        return repository.save(product).map(ProductMapper::toResponse);
    }

    public Mono<ProductResponse> update(Long id, ProductRequest request) {
        return findExisting(id)
                .flatMap(existing -> {
                    ProductMapper.updateEntity(existing, request);
                    return repository.save(existing);
                })
                .map(ProductMapper::toResponse);
    }

    public Mono<Void> delete(Long id) {
        return findExisting(id).flatMap(existing -> repository.deleteById(existing.getId()));
    }

    private Mono<Product> findExisting(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Producto no encontrado"
                )));
    }
}

