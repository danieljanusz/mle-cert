/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson10.processing;

import com.workfusion.vds.sdk.api.nlp.model.Field;
import com.workfusion.vds.sdk.api.nlp.model.IeDocument;
import com.workfusion.vds.sdk.api.nlp.processing.Processor;
import com.workfusion.vds.sdk.nlp.component.processing.normalization.OcrAmountNormalizer;
import com.workfusion.vds.sdk.nlp.component.processing.normalization.OcrWhitespaceNormalizer;

public class ExpandPostProcessor implements Processor<IeDocument> {

    /**
     * Name of {@link Field} representing a field to process.
     */
    //private static final String FIELD_NAME = "somefield";

    @Override
    public void process(IeDocument document) {
        System.out.println("in post processors");
        document.findFields()
                .forEach(f -> {
                    if (f.getValue() != null) {
                        String value = f.getValue();
                      //  f.setValue(value.replace("O", "0"));
                      OcrWhitespaceNormalizer ocrWhitespaceNormalizer=new OcrWhitespaceNormalizer();
                        String newValue=ocrWhitespaceNormalizer.normalize(f.getValue());
                        System.out.println("newValue "+newValue);
                    }
                });
    }

}