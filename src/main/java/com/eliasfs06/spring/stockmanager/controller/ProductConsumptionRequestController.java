package com.eliasfs06.spring.stockmanager.controller;

import com.eliasfs06.spring.stockmanager.model.Product;
import com.eliasfs06.spring.stockmanager.model.ProductConsumptionRequest;
import com.eliasfs06.spring.stockmanager.service.ProductConsumptionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("product-consumption-request")
public class ProductConsumptionRequestController extends GenericController<ProductConsumptionRequest> {

    @Autowired
    private ProductConsumptionRequestService service;

    private final String LIST_PATH = "/product-consumption-request/list";
    private final int DEFAULT_PAGE_SIZE = 5;
    private final int DEFAULT_PAGE_NUMBER = 1;

    @GetMapping("/list")
    public String findAll(Model model, @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(DEFAULT_PAGE_NUMBER);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<ProductConsumptionRequest> requestPage = service.getPage(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("requestList", requestPage);
        model.addAttribute("currentPage", currentPage);

        int totalPages = requestPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return LIST_PATH;
    }
}
