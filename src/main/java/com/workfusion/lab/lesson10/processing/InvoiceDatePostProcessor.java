/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson10.processing;

//import com.sun.org.apache.xml.internal.security.Init;

import com.workfusion.vds.sdk.api.nlp.annotation.OnInit;
import com.workfusion.vds.sdk.api.nlp.model.Field;
import com.workfusion.vds.sdk.api.nlp.model.IeDocument;
import com.workfusion.vds.sdk.api.nlp.processing.Processor;
import com.workfusion.vds.sdk.nlp.component.processing.normalization.OcrDateNormalizer;

import java.util.ArrayList;
import java.util.List;


public class InvoiceDatePostProcessor implements Processor<IeDocument> {

    public static final String FIELD_NAME = "invoice_date";
    private static final String OUTPUT_DATE_FORMAT = "MM/dd/yyyy";

    @OnInit
    public void init() {
        // initialize formatter inside onInit method to prevent serialization issues
    }


    @Override
    public void process(IeDocument document) {
        //correct date to format dd/mm/yyyy
        List<Field> fieldList = new ArrayList<>();

        fieldList.addAll(document.findFields(FIELD_NAME));
        if (fieldList.size() > 0) {
            for (Field field : fieldList) {
                String dateBefore = field.getValue();
                OcrDateNormalizer normalizer = new OcrDateNormalizer(OUTPUT_DATE_FORMAT);
                String dateAfterFormatting = normalizer.normalize(dateBefore);
                field.setValue(dateAfterFormatting);
            }
        }
    }

}