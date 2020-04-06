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
import org.apache.xerces.impl.xpath.regex.Match;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Assignment1DatePostProcessor implements Processor<IeDocument> {

    public static final String FIELD_NAME = "invoice_date";
    private static final String OUTPUT_DATE_FORMAT = "MM/dd/yyyy";

    @OnInit
    public void init() {
        // initialize formatter inside onInit method to prevent serialization issues

    }


    @Override
    public void process(IeDocument document) {
        //System.out.println("find fields  "+document.findFields());
//        System.out.println("get text date value "+ document.findFields());
//        Optional<Field> myFieldList2=document.findField(FIELD_NAME);

        //correct date to format dd/mm/yyyy

        List<Field>fieldList=new ArrayList<>();
        fieldList.addAll(document.findFields(FIELD_NAME));


        if (fieldList.size()>0) {
            for(Field field:fieldList){
//                    System.out.println("date before formatting " + field.getValue());
                    String dateBefore=field.getValue();
                    OcrDateNormalizer normalizer = new OcrDateNormalizer(OUTPUT_DATE_FORMAT);
                    String dateAfterFormatting=normalizer.normalize(dateBefore);
//                    System.out.println("formatted value " + dateAfterFormatting);
                    field.setValue(dateAfterFormatting);
            }
        }
    }

}