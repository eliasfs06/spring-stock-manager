package com.eliasfs06.spring.stockmanager.controller;

import com.eliasfs06.spring.stockmanager.model.Product;
import com.eliasfs06.spring.stockmanager.model.ProductAcquisition;
import com.eliasfs06.spring.stockmanager.model.ProductAcquisitionItem;
import com.eliasfs06.spring.stockmanager.model.dto.ProductAcquisitionItemDTO;
import com.eliasfs06.spring.stockmanager.service.ProductAcquisitionService;
import com.eliasfs06.spring.stockmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("product-acquisition")
public class ProductAcquisitionController extends GenericController<ProductAcquisition> {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductAcquisitionService service;

    private final String FORM_PATH = "/product-acquisition/form";
    private final String LIST_PATH = "/product-acquisition/list";
    private final int DEFAULT_PAGE_SIZE = 5;
    private final int DEFAULT_PAGE_NUMBER = 1;

    @Override
    @GetMapping("/form")
    public ModelAndView getForm() {
        ModelAndView mv = new ModelAndView(FORM_PATH);
        mv.addObject("productAcquisition", new ProductAcquisition());
        mv.addObject("products", productService.findAll());
        return mv;

    }

    @PostMapping("/add-item")
    public String addProduct(Model model, @RequestBody List<ProductAcquisitionItemDTO> productAcquisitionItens) {

        List<ProductAcquisitionItem> itens = new ArrayList<>();
        if(productAcquisitionItens != null && !productAcquisitionItens.isEmpty()){
            productAcquisitionItens.forEach(item -> {
                Product product = productService.get(Long.valueOf(item.getProductId()));
                ProductAcquisitionItem newItem = new ProductAcquisitionItem(product, Integer.valueOf(item.getQuantity()));
                itens.add(newItem);
            });
        }
        model.addAttribute("itens", itens);

        return "/product-acquisition/product-acquisition-itens :: acquisition-itens";
    }
}

