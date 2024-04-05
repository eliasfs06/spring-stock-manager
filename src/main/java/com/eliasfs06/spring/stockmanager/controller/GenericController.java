package com.eliasfs06.spring.stockmanager.controller;

import com.eliasfs06.spring.stockmanager.model.BaseEntity;
import com.eliasfs06.spring.stockmanager.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public abstract class GenericController<T extends BaseEntity> {

    private final String DEFAULT_LIST_PATH = "/list";
    private final String DEFAULT_FORM_PATH = "/form";
    private final String DEFAULT_VIEW_PATH = "/view";

    @Autowired
    private GenericService<T> service;

    @GetMapping("")
    public String findAll(Model model, Pageable pageable){
        String name = this.getEntityName();
        Page<T> page = this.service.getPage(pageable);
        model.addAttribute(name, page);
        return name + DEFAULT_LIST_PATH;
    }

    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id, Model model){
        String name = this.getEntityName();
        model.addAttribute("model", service.get(id));
        return name + DEFAULT_VIEW_PATH;
    }

    @GetMapping("/form")
    public ModelAndView getForm() {
        ModelAndView mv = new ModelAndView(this.getEntityName() + DEFAULT_FORM_PATH);
        try {
            mv.addObject(this.inferGenericType().getDeclaredConstructor().newInstance());
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException |
                 SecurityException | InstantiationException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return mv;
    }

    @PutMapping("")
    public ResponseEntity<T> update(@RequestBody T updated){
        return ResponseEntity.ok(service.update(updated));
    }

    @PostMapping("")
    public ModelAndView save(T entity) {
        ModelAndView mv = new ModelAndView(this.getEntityName() + DEFAULT_LIST_PATH);
        this.service.save(entity);
        return mv;
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        service.delete(id);
        String name = this.getEntityName();
        return name + DEFAULT_LIST_PATH;
    }

    protected String getEntityName() {
        return StringUtils.uncapitalize(this.inferGenericType().getSimpleName());
    }

    protected Class<T> inferGenericType() {
        Class<T> class1 = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return class1;
    }
}
