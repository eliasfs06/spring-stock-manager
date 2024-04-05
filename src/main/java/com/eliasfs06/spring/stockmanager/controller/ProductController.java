package com.eliasfs06.spring.stockmanager.controller;

import com.eliasfs06.spring.stockmanager.model.Product;
import com.eliasfs06.spring.stockmanager.model.enums.Brand;
import com.eliasfs06.spring.stockmanager.model.enums.ProductType;
import com.eliasfs06.spring.stockmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("product")
public class ProductController extends GenericController<Product> {

    @Autowired
    private ProductService productService;

    private final String FORM_PATH = "/product/form";

    @Override
    @GetMapping("/form")
    public ModelAndView getForm() {
        ModelAndView mv = new ModelAndView(FORM_PATH);

        mv.addObject("product", new Product());
        mv.addObject("types", ProductType.values());
        mv.addObject("brands", Brand.values());

        return mv;
    }
}
