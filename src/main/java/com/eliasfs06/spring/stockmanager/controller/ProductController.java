package com.eliasfs06.spring.stockmanager.controller;

import com.eliasfs06.spring.stockmanager.model.Product;
import com.eliasfs06.spring.stockmanager.model.ProductConsumptionRequest;
import com.eliasfs06.spring.stockmanager.model.enums.Brand;
import com.eliasfs06.spring.stockmanager.model.enums.ProductType;
import com.eliasfs06.spring.stockmanager.model.exceptionsHandler.BusinessException;
import com.eliasfs06.spring.stockmanager.service.ProductConsumptionRequestService;
import com.eliasfs06.spring.stockmanager.service.ProductConsumptionService;
import com.eliasfs06.spring.stockmanager.service.ProductService;
import com.eliasfs06.spring.stockmanager.service.helper.MessageCode;
import com.eliasfs06.spring.stockmanager.service.helper.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("product")
public class ProductController extends GenericController<Product> {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductConsumptionService productConsumptionService;

    @Autowired
    private ProductConsumptionRequestService productConsumptionRequestServiceService;

    @Autowired
    private MessageHelper messageHelper;

    private final String FORM_PATH = "/product/form";
    private final String LIST_PATH = "/product/list";
    private final int DEFAULT_PAGE_SIZE = 5;
    private final int DEFAULT_PAGE_NUMBER = 1;

    @Override
    @GetMapping("/form")
    public ModelAndView getForm() {
        ModelAndView mv = new ModelAndView(FORM_PATH);

        mv.addObject("product", new Product());
        mv.addObject("types", ProductType.values());
        mv.addObject("brands", Brand.values());

        return mv;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getForm(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView(FORM_PATH);
        Product product = productService.get(id);
        mv.addObject("product", product);
        mv.addObject("types", ProductType.values());
        mv.addObject("brands", Brand.values());

        return mv;
    }

    @GetMapping("/list")
    public String findAll(Model model, @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(DEFAULT_PAGE_NUMBER);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<Product> productPage = productService.getPage(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("productList", productPage);
        model.addAttribute("currentPage", currentPage);

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return LIST_PATH;
    }

    @PostMapping("/save")
    public String save(Product product, Model model, RedirectAttributes ra) {
        try {
            productService.validateProduct(product);

        } catch (BusinessException e) {
            model.addAttribute("errorMessage", messageHelper.getMessage(MessageCode.DEFAULT_EMPTY_FIELD_MSG));
            model.addAttribute("product", product);
            model.addAttribute("types", ProductType.values());
            model.addAttribute("brands", Brand.values());
            return FORM_PATH;

        }
        productService.save(product);
        model.addAttribute("successMessage", messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG));
        return findAll(model, Optional.of(DEFAULT_PAGE_NUMBER), Optional.of(DEFAULT_PAGE_SIZE));
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model){
        productService.delete(id);
        model.addAttribute("successMessage", messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG));
        return findAll(model, Optional.of(DEFAULT_PAGE_NUMBER), Optional.of(DEFAULT_PAGE_SIZE));
    }

    @PostMapping("/consume/{id}")
    public ResponseEntity<String> consume(@PathVariable Long id, @RequestParam Integer quantity) {
        try {
            productConsumptionService.cosumeProduct(productService.get(id), quantity);
            return ResponseEntity.ok().body(MessageCode.DEFAULT_SUCCESS_MSG);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageHelper.getMessage(MessageCode.CANT_CONSUME_PRODUCT));
        }
    }

    @PostMapping("/request-to-consume/{id}")
    public ResponseEntity<String> requestToConsume(@PathVariable Long id,
                                                   @RequestParam Integer quantity, String description) {
        try {
            productConsumptionRequestServiceService.requestToCosumeProduct(productService.get(id), quantity, description);
            return ResponseEntity.ok().body(MessageCode.DEFAULT_SUCCESS_MSG);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageHelper.getMessage(MessageCode.CANT_CONSUME_PRODUCT));
        }
    }
}
