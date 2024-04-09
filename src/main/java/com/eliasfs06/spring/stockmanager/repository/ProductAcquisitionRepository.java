package com.eliasfs06.spring.stockmanager.repository;

import com.eliasfs06.spring.stockmanager.model.ProductAcquisition;
import com.eliasfs06.spring.stockmanager.model.ProductAcquisitionItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAcquisitionRepository extends GenericRepository<ProductAcquisition>{

    @Query("FROM ProductAcquisitionItem item WHERE item.productAcquisition.id = ?1 " +
            "AND item.productAcquisition.active = true AND item.active = true")
    public List<ProductAcquisitionItem> findByProductAcquisition(Long id);
}
