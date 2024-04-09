package com.eliasfs06.spring.stockmanager.service;

import com.eliasfs06.spring.stockmanager.model.Product;
import com.eliasfs06.spring.stockmanager.model.ProductAcquisitionItem;
import com.eliasfs06.spring.stockmanager.model.exceptionsHandler.BusinessException;
import com.eliasfs06.spring.stockmanager.repository.ProductAcquisitionItemRepository;
import com.eliasfs06.spring.stockmanager.repository.ProductAcquisitionRepository;
import com.eliasfs06.spring.stockmanager.service.helper.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAcquisitionItemService extends GenericService<ProductAcquisitionItem> {

    @Autowired
    private ProductAcquisitionItemRepository repository;

    @Autowired
    private ProductService productService;

    public List<ProductAcquisitionItem> findByProductAcquisition(Long id) {
        return repository.findByProductAcquisition(id);
    }

    public ProductAcquisitionItem save(ProductAcquisitionItem productAcquisitionItem) {
        Product product = productAcquisitionItem.getProduct();
        product.setStockQuantity(product.getStockQuantity() == null ? productAcquisitionItem.getQuantity() : product.getStockQuantity() + productAcquisitionItem.getQuantity());
        productService.save(product);
        return repository.save(productAcquisitionItem);
    }

    public void deleteItem(Long id) {
        ProductAcquisitionItem productAcquisitionItem = get(id);
        Product product = productAcquisitionItem.getProduct();
        product.setStockQuantity(product.getStockQuantity() - productAcquisitionItem.getQuantity());
        productService.save(product);
        repository.save(productAcquisitionItem);
    }

}