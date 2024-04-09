package com.eliasfs06.spring.stockmanager.repository;

import com.eliasfs06.spring.stockmanager.model.ProductAcquisition;
import com.eliasfs06.spring.stockmanager.model.ProductAcquisitionItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAcquisitionRepository extends GenericRepository<ProductAcquisition>{
}
