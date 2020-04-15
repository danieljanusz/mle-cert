/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson10.processing;

//import com.sun.org.apache.xml.internal.security.Init;

import com.workfusion.vds.sdk.api.nlp.annotation.OnInit;
import com.workfusion.vds.sdk.api.nlp.model.Cell;
import com.workfusion.vds.sdk.api.nlp.model.Field;
import com.workfusion.vds.sdk.api.nlp.model.IeDocument;
import com.workfusion.vds.sdk.api.nlp.processing.Processor;
import com.workfusion.vds.sdk.nlp.component.processing.normalization.OcrAmountNormalizer;

import java.util.ArrayList;
import java.util.List;


public class ProductPostProcessor implements Processor<IeDocument> {

    public static final String FIELD_NAME = "product";

    @OnInit
    public void init() {
        // initialize formatter inside onInit method to prevent serialization issues
    }

    @Override
    public void process(IeDocument document) {

        String prevFieldValue="";
        List<Field> fieldList = new ArrayList<>();
        fieldList.addAll(document.findFields(FIELD_NAME));
        if (fieldList.size() > 0) {
            for (Field field : fieldList) {
                List<Cell>cellList=new ArrayList<>();
                cellList.addAll(document.findCovering(Cell.class,field));
                if(cellList.size()>0){
                    String fixedProduct=cellList.get(0).getText();
                    if(prevFieldValue==fixedProduct){
                        document.remove(field);
                    }else{
                        field.setValue(fixedProduct);
                        prevFieldValue=fixedProduct;
                    }

                }

            }
        }

    }
}