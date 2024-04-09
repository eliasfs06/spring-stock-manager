package com.eliasfs06.spring.stockmanager.service;

import com.eliasfs06.spring.stockmanager.model.Product;
import com.eliasfs06.spring.stockmanager.model.ProductAcquisition;
import com.eliasfs06.spring.stockmanager.model.ProductAcquisitionItem;
import com.eliasfs06.spring.stockmanager.model.dto.ProductAcquisitionItemDTO;
import com.eliasfs06.spring.stockmanager.repository.ProductAcquisitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ProductAcquisitionService extends GenericService<ProductAcquisition>{

    @Autowired
    private ProductAcquisitionRepository repository;

    @Autowired
    private ProductAcquisitionItemService productAcquisitionItemService;

    @Autowired
    private ProductService productService;

    public Page<ProductAcquisition> getPage(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ProductAcquisition> list;
        List<ProductAcquisition> productAcquisitions = this.findAll();

        productAcquisitions.forEach(productAcquisition -> {
            productAcquisition.setItens(productAcquisitionItemService.findByProductAcquisition(productAcquisition.getId()));
        });

        if (productAcquisitions.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, productAcquisitions.size());
            list = productAcquisitions.subList(startItem, toIndex);
        }

        return new PageImpl<ProductAcquisition>(list, PageRequest.of(currentPage, pageSize), productAcquisitions.size());
    }

    public List<ProductAcquisition> findAll(){
        return repository.findAll();
    }

    public void save(ProductAcquisition productAcquisition, List<ProductAcquisitionItemDTO> productAcquisitionItens) {
        productAcquisition.setAquisitionDate(new Date());
        productAcquisition = repository.save(productAcquisition);
        ProductAcquisition finalProductAcquisition = productAcquisition;

        List<ProductAcquisitionItem> itens = new ArrayList<>();
        productAcquisitionItens.forEach(item -> {
            Product product = productService.get(Long.valueOf(item.getProductId()));
            ProductAcquisitionItem newItem = new ProductAcquisitionItem(product, Integer.valueOf(item.getQuantity()), finalProductAcquisition);
            productAcquisitionItemService.save(newItem);
            itens.add(newItem);
        });
    }
}
