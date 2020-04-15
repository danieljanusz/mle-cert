/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson10.processing;

//import com.sun.org.apache.xml.internal.security.Init;

import com.workfusion.vds.sdk.api.nlp.annotation.OnInit;
import com.workfusion.vds.sdk.api.nlp.model.Field;
import com.workfusion.vds.sdk.api.nlp.model.IeDocument;
import com.workfusion.vds.sdk.api.nlp.processing.Processor;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class QuantityPostProcessor implements Processor<IeDocument> {

    public static final String FIELD_NAME = "quantity";

    @OnInit
    public void init() {
        // initialize formatter inside onInit method to prevent serialization issues
    }

    @Override
    public void process(IeDocument document) {

        //format price to standard format ex. 12021.83
        // if 0.00 - leave as is.

        List<Field> fieldList = new ArrayList<>();
        fieldList.addAll(document.findFields(FIELD_NAME));
        if (fieldList.size() > 0) {
            for (Field field : fieldList) {
                String amount =field.getValue();
                if (!amount.equals("0.00")){
                    double doubleAmount;
                    NumberFormat formatter=new DecimalFormat("#0.00");
                    try{
                        doubleAmount=Double.parseDouble(amount);
                        amount=formatter.format(doubleAmount);
                        amount=amount.replaceAll("\\,","\\.");
                        field.setValue(amount);
                    }
                    catch (NumberFormatException e){
                        doubleAmount=0;
                    }
                }

            }
        }

    }
}