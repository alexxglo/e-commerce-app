package com.example.ecommerceservice.services.impl;

import com.example.ecommerceservice.entities.DTOs.FundResponse;
import com.example.ecommerceservice.entities.DTOs.ProductDTO;
import com.example.ecommerceservice.entities.DTOs.TransactionResponse;
import com.example.ecommerceservice.entities.Product;
import com.example.ecommerceservice.feignclient.FundClient;
import com.example.ecommerceservice.repositories.ProductRepository;
import com.example.ecommerceservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    FundClient fundClient;

    @Override
    public ResponseEntity<List<Product>> searchProduct(String keyword) {
        List<Product> foundProducts;
        foundProducts = productRepository.getProductByNameContainingKeyword(keyword);
        if(foundProducts.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(foundProducts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FundResponse> buyProduct(Long accountNumber, Long productId) {
        try {
            Product product = productRepository.getById(productId);
            ProductDTO productParam = setProductDTO(product);
            ResponseEntity<FundResponse> response = fundClient.buyProduct(accountNumber, product.getPrice());
            fundClient.addProductToHistory(accountNumber, productParam);
            return response;
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<TransactionResponse> getHistoryOfAccount(Long accountNumber) {
        return fundClient.getHistoryOfAccount(accountNumber);
    }

    private ProductDTO setProductDTO(Product product) {
        ProductDTO productParam = new ProductDTO();
        productParam.setName(product.getName());
        productParam.setPrice(product.getPrice());
        return productParam;
    }

}
