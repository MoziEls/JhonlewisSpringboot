package com.mozi.application.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mozi.application.Exception.ProductException;
import com.mozi.application.model.Product;
import com.mozi.application.service.ProductService;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@ResponseBody
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController( ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products", produces = { "application/json" })
    public ArrayList<Product> getProducts(@RequestParam("labelType") Optional<String> labelType)
    {
    	if(labelType.isPresent()) {
        if(labelType==null && labelType.get().equals("") && (!labelType.equals("ShowWasNow")
                                &&!labelType.equals("ShowWasThenNow")
                                && !labelType.equals("ShowPercDscount")))
            throw new ProductException();
    	}

        System.out.println(labelType);

        return productService.getProductsList(labelType);
    }

}
