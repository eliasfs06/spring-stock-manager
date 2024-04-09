package com.eliasfs06.spring.stockmanager.service;

import com.eliasfs06.spring.stockmanager.model.ProductAcquisitionItem;
import com.eliasfs06.spring.stockmanager.repository.ProductAcquisitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAcquisitionItemService extends GenericService<ProductAcquisitionItem> {

    @Autowired
    private ProductAcquisitionRepository repository;

    public List<ProductAcquisitionItem> findByProductAcquisition(Long id){
        return repository.findByProductAcquisition(id);
    }

}
