package com.eliasfs06.spring.stockmanager.controller;

import com.eliasfs06.spring.stockmanager.model.Product;
import com.eliasfs06.spring.stockmanager.model.ProductAcquisition;
import com.eliasfs06.spring.stockmanager.model.ProductAcquisitionItem;
import com.eliasfs06.spring.stockmanager.model.dto.ProductAcquisitionItemDTO;
import com.eliasfs06.spring.stockmanager.service.ProductAcquisitionService;
import com.eliasfs06.spring.stockmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @GetMapping("/form")
    public ModelAndView getForm() {
        ModelAndView mv = new ModelAndView(FORM_PATH);
        mv.addObject("productAcquisition", new ProductAcquisition());
        mv.addObject("products", productService.findAll());
        return mv;

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model){
        service.delete(id);
        return findAll(model, Optional.of(DEFAULT_PAGE_NUMBER), Optional.of(DEFAULT_PAGE_SIZE));
    }

    @GetMapping("/list")
    public String findAll(Model model, @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(DEFAULT_PAGE_NUMBER);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<ProductAcquisition> productAcquisitionPage = service.getPage(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("productAcquisitionList", productAcquisitionPage);
        model.addAttribute("currentPage", currentPage);

        int totalPages = productAcquisitionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return LIST_PATH;
    }

    @PostMapping("/save")
    public String save(Model model, ProductAcquisition productAcquisition, @RequestBody List<ProductAcquisitionItemDTO> productAcquisitionItens) {
        service.save(productAcquisition, productAcquisitionItens);
        return findAll(model, Optional.of(DEFAULT_PAGE_NUMBER), Optional.of(DEFAULT_PAGE_SIZE));
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

