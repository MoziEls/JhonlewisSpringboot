package com.mozi.application.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mozi.application.model.Color;
import com.mozi.application.model.Price;
import com.mozi.application.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    public static final String BR_POUND= "\u00A3";

    private RestTemplate restTemplate;

    private String url="https://jl-nonprod-syst.apigee.net/v1/categories/600001506/products?key=2ALHCAAs6ikGRBoy6eTHA58RaG097Fma";

    @Autowired
    public ProductService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public ArrayList<Product> getProductsList(Optional<String> labelType)
        {
            String response = restTemplate.getForObject(url,String.class);

            ArrayList<Product> result = JsonParser(response,labelType);

            return result;
        }

    private ArrayList<Product> JsonParser(String response, Optional<String> labelType)
    {
        JsonFactory jsonFactory = new JsonFactory();

        ObjectMapper objectMapper = new ObjectMapper(jsonFactory);

        String productId = null;
        String title = null;
        ArrayList<Color> colorSwatches = new ArrayList<Color>();
        String skuid = null;
        String nowPrice = null;
        String label = null;

        ArrayList<Product> productList = new ArrayList<Product>();

        try {

            JsonNode jsonNode = objectMapper.readTree(response).get("products");//getting the products from json

            if (jsonNode.isArray()) {
                for (final JsonNode objNode : jsonNode) {//going through array of json objects in products
                    if(objNode.isObject())
                    {
                        Iterator<Map.Entry<String,JsonNode>> fieldsIterator = objNode.fields();
                        boolean isPriceChanged = true;
                        while (fieldsIterator.hasNext()) {//going through all the field of json object

                            Map.Entry<String,JsonNode> field = fieldsIterator.next();
                          

                            if(field.getKey().equals("productId"))
                                productId = field.getValue().textValue();//assigining it to productid
                            if(field.getKey().equals("title"))
                                title = field.getValue().textValue();
                            if(field.getKey().equals("price"))
                            {
                                isPriceChanged= didPriceChangeOrDecrease(field.getValue());
                                if(didPriceChangeOrDecrease(field.getValue()))
                                {
                                    Price price = getPricing(field.getValue());
                                    nowPrice = BR_POUND+price.getNow();
                                    if(labelType.isPresent())
                                    {
                                        if(labelType.get().equals("ShowWasNow"))
                                            label = "Was "+BR_POUND+price.getWas()+", "+"Now "+BR_POUND+price.getNow();
                                        if(labelType.get().equals("ShowWasThenNow"))
                                            label = "Was "+BR_POUND+price.getWas()+", "+"Then "+BR_POUND+price.getThen2()+
                                                    ", "+"Now "+BR_POUND+price.getNow();
                                        if(labelType.get().equals("ShowPercDscount"))
                                        {

           

                                            float was = Float.valueOf(price.getWas());
                                            String nowValue = (price.getNow()==null)?"0":price.getNow();
                                            float now = Float.valueOf(nowValue);
                                            float diffPercent = ((was-now)/now)*100;
                                            label = diffPercent+"% off - Now "+BR_POUND+price.getNow();
                                        }

                                    }
                                  
                                }
                                else
                                {
                                    continue;
                                }
                            }
                            if(field.getKey().equals("colorSwatches") && field.getValue().size()>0)
                            {
                                colorSwatches = getColorSwatches(field.getValue());

                            }
                            if(field.getKey().equals("defaultSkuId"))
                            {
                                skuid = field.getValue().textValue();

                            }

                        }

                        if(isPriceChanged) {

                            productList.add(new Product(productId, title, colorSwatches, skuid, nowPrice,label));
                        }

                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return productList;
    }

    private static boolean didPriceChangeOrDecrease(JsonNode priceNode)
    {
        //System.out.println("Inside pricing change");
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = priceNode.fields();

        float was=0;
        float now=0;

        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            //System.out.println("value of was : "+field.getValue().toString() + " field : " + field.getKey());
            if(field.getKey().equals("was") &&
                    field.getValue().textValue().equals(""))
            {
                return false;
            }
            else if(field.getKey().equals("was"))
            {
                was = Float.valueOf(field.getValue().textValue());
            }

            if(field.getKey().equals("now") && !field.getValue().isObject() &&
                    !field.getValue().textValue().equals("") )
            {
                now = Float.valueOf(field.getValue().textValue());
            }

        }
        if(now-was>=0)
        {
            float diff= now-was;
            //System.out.println("inside the false clause" + diff);
            return false;
        }

        return true;
    }

    private static Price getPricing(JsonNode priceNode)
    {

         String was = null;
         String then1  = null;
         String then2  = null;
         String now  = null;
         String uom  = null;
         String currency  = null;

        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = priceNode.fields();
        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            if(field.getKey().equals("was"))
            {
                was = field.getValue().textValue();
            }
            if(field.getKey().equals("then1"))
            {
                then1 = field.getValue().textValue();
            }
            if(field.getKey().equals("then2"))
            {
                then2 = field.getValue().textValue();
            }
            if(field.getKey().equals("now"))
            {
                now = field.getValue().textValue();
            }
            if(field.getKey().equals("uom"))
            {
                uom = field.getValue().textValue();
            }
            if(field.getKey().equals("currency"))
            {
                currency = field.getValue().textValue();
            }

        }
        Price price =new Price(was,then1,then2,now,uom,currency);
        
        return price;
        
    }



    private static ArrayList<Color> getColorSwatches(JsonNode colorNode)
    {
         String color=null;
         String basicColor=null;
         String colorSwatchUrl=null;
         String skuId=null;

         ArrayList<Color> colors = new ArrayList<Color>();

        if (colorNode.isArray()) {
            for (final JsonNode objNode : colorNode) {
                if (objNode.isObject()) {
                    Iterator<Map.Entry<String, JsonNode>> fieldsIterator = objNode.fields();
                    while (fieldsIterator.hasNext()) {

                        Map.Entry<String, JsonNode> field = fieldsIterator.next();
                        if(field.getKey().equals("color"))
                            color=field.getValue().textValue();
                        if(field.getKey().equals("basicColor"))
                            basicColor=field.getValue().textValue();
                        if(field.getKey().equals("colorSwatchUrl"))
                            colorSwatchUrl=field.getValue().textValue();
                     
                        if(field.getKey().equals("skuId"))
                            skuId=field.getValue().textValue();

                    }

                    colors.add(new Color(color,basicColor,colorSwatchUrl,skuId));
//returning an array of objects in contrast to get pricing where we return objects
                }
            }
        }

        return colors;

    }

}
