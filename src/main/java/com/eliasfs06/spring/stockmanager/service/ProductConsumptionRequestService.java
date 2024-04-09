package com.eliasfs06.spring.stockmanager.service;

import com.eliasfs06.spring.stockmanager.model.Product;
import com.eliasfs06.spring.stockmanager.model.ProductConsumption;
import com.eliasfs06.spring.stockmanager.model.ProductConsumptionRequest;
import com.eliasfs06.spring.stockmanager.model.exceptionsHandler.BusinessException;
import com.eliasfs06.spring.stockmanager.repository.ProductConsumptionRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductConsumptionRequestService extends GenericService<ProductConsumptionRequest>{

    @Autowired
    private ProductConsumptionService productConsumptionService;

    @Autowired
    private ProductConsumptionRequestRepository repository;

    public void requestToCosumeProduct(Product product, Integer quantity, String description) throws BusinessException {
        productConsumptionService.verifyTotalToConsume(product, quantity);
        ProductConsumptionRequest request = new ProductConsumptionRequest(product, quantity, description);
        repository.save(request);
    }

    public Page<ProductConsumptionRequest> getPage(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ProductConsumptionRequest> list;
        List<ProductConsumptionRequest> requests = repository.findAll();

        if (requests.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, requests.size());
            list = requests.subList(startItem, toIndex);
        }

        return new PageImpl<ProductConsumptionRequest>(list, PageRequest.of(currentPage, pageSize), requests.size());
    }
}
