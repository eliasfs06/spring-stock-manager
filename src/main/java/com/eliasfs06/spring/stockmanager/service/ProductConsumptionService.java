package com.eliasfs06.spring.stockmanager.service;

import com.eliasfs06.spring.stockmanager.model.Product;
import com.eliasfs06.spring.stockmanager.model.ProductConsumption;
import com.eliasfs06.spring.stockmanager.model.exceptionsHandler.BusinessException;
import com.eliasfs06.spring.stockmanager.service.helper.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumptionService extends GenericService<ProductConsumption>{

    @Autowired
    private ProductService productService;

    public void cosumeProduct(Product product, Integer quantity) throws BusinessException {
        verifyTotalToConsume(product, quantity);
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productService.save(product);
        ProductConsumption productConsumption = new ProductConsumption(product, quantity);
        this.save(productConsumption);
    }

    public void verifyTotalToConsume(Product product, Integer quantity) throws BusinessException {
        if(product.getStockQuantity() == null || product.getStockQuantity() < quantity){
            throw new BusinessException(MessageCode.CANT_CONSUME_PRODUCT);
        }
    }
}
