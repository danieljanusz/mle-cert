/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson10.config;

import com.workfusion.lab.lesson10.fe.IsNerPresentFE;
import com.workfusion.lab.lesson10.processing.Assignment1DatePostProcessor;
import com.workfusion.lab.lesson10.processing.ExpandPostProcessor;
import com.workfusion.vds.nlp.hypermodel.GenericHpoConfiguration;
import com.workfusion.vds.nlp.hypermodel.ie.generic.GenericIeHypermodel;
import com.workfusion.vds.nlp.hypermodel.ie.generic.config.GenericIeHypermodelConfiguration;
import com.workfusion.vds.sdk.api.hpo.Dimensions;
import com.workfusion.vds.sdk.api.hpo.HpoConfiguration;
import com.workfusion.vds.sdk.api.hpo.ParameterSpace;
import com.workfusion.vds.sdk.api.hypermodel.ConfigurationContext;
import com.workfusion.vds.sdk.api.hypermodel.annotation.Filter;
import com.workfusion.vds.sdk.api.hypermodel.annotation.Import;
import com.workfusion.vds.sdk.api.hypermodel.annotation.ModelConfiguration;
import com.workfusion.vds.sdk.api.hypermodel.annotation.Named;
import com.workfusion.vds.sdk.api.nlp.annotator.Annotator;
import com.workfusion.vds.sdk.api.nlp.configuration.IeConfigurationContext;
import com.workfusion.vds.sdk.api.nlp.fe.FeatureExtractor;
import com.workfusion.vds.sdk.api.nlp.model.Document;
import com.workfusion.vds.sdk.api.nlp.model.Element;
import com.workfusion.vds.sdk.api.nlp.model.Field;
import com.workfusion.vds.sdk.api.nlp.model.IeDocument;
import com.workfusion.vds.sdk.api.nlp.processing.Processor;
import com.workfusion.vds.sdk.nlp.component.annotator.EntityBoundaryAnnotator;
import com.workfusion.vds.sdk.nlp.component.annotator.ner.BaseRegexNerAnnotator;
import com.workfusion.vds.sdk.nlp.component.annotator.tokenizer.MatcherTokenAnnotator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The model configuration class.
 */
@ModelConfiguration
@Import(
        configurations = {
            @Import.Configuration(GenericIeHypermodelConfiguration.class)
}
        ,
        resources={
                @Import.Resource(value="C:\\Users\\Daniel\\workfusion-workspace\\ml_sdk_lessons\\lesson-10\\src\\main\\resources\\certParameters\\email\\parameters.json",
                        condition =@Filter(expression = "field.code == 'email'")),
                @Import.Resource(value="C:\\Users\\Daniel\\workfusion-workspace\\ml_sdk_lessons\\lesson-10\\src\\main\\resources\\certParameters\\invoice_date\\parameters.json",
                        condition =@Filter(expression = "field.code == 'invoice_date'")),
                @Import.Resource(value="C:\\Users\\Daniel\\workfusion-workspace\\ml_sdk_lessons\\lesson-10\\src\\main\\resources\\certParameters\\invoice_number\\parameters.json",
                        condition =@Filter(expression = "field.code == 'invoice_number'")),
                @Import.Resource(value="C:\\Users\\Daniel\\workfusion-workspace\\ml_sdk_lessons\\lesson-10\\src\\main\\resources\\certParameters\\price\\parameters.json",
                        condition =@Filter(expression = "field.code == 'price'")),
                @Import.Resource(value="C:\\Users\\Daniel\\workfusion-workspace\\ml_sdk_lessons\\lesson-10\\src\\main\\resources\\certParameters\\product\\parameters.json",
                        condition =@Filter(expression = "field.code == 'product'")),
                @Import.Resource(value="C:\\Users\\Daniel\\workfusion-workspace\\ml_sdk_lessons\\lesson-10\\src\\main\\resources\\certParameters\\quantity\\parameters.json",
                        condition =@Filter(expression = "field.code == 'quantity'")),
                @Import.Resource(value="C:\\Users\\Daniel\\workfusion-workspace\\ml_sdk_lessons\\lesson-10\\src\\main\\resources\\certParameters\\supplier_name\\parameters.json",
                        condition =@Filter(expression = "field.code == 'supplier_name'")),
                @Import.Resource(value="C:\\Users\\Daniel\\workfusion-workspace\\ml_sdk_lessons\\lesson-10\\src\\main\\resources\\certParameters\\total_amount\\parameters.json",
                        condition =@Filter(expression = "field.code == 'total_amount'"))
        }
)

public class Assignment3ModelConfiguration extends GenericIeHypermodel {


    public final static String FIELD_DATE = "date";


    public final static String FIELD_INVOICE_NUMBER = "invoice_number";

    /**
     * Regex pattern to match an invoice number.
     */
    private final static String INVOICE_NUMBER_REGEX = "\\d{10}";

    /**
     * Name of {@link Field} representing a date.
     */

    public final static String FIELD_PRODUCT = "product";

    private final static String TOKEN_REGEX = "[\\w@.,$%’-]+";

    /**
     * Name of {@link Field} representing an invoice number.
     */
    //public final static String FIELD_INVOICE_NUMBER = "invoice_number";

    /**
     * Regex pattern to match an invoice number.
     */
     //private final static String INVOICE_NUMBER_REGEX = "\\d{10}";


    /**
     * Regex pattern to match a date.
     */


        @Named("annotators")
    public List<Annotator<Document>> getAnnotators(IeConfigurationContext context) {
        List<Annotator<Document>> annotators = new ArrayList<>();

        annotators.add(new MatcherTokenAnnotator(TOKEN_REGEX));
        annotators.add(new EntityBoundaryAnnotator());
        System.out.println("field get code for annotators "+ context.getField().getCode());
        return annotators;
    }


    @Named("featureExtractors")
    public List<FeatureExtractor<Element>> getFeatureExtractors(IeConfigurationContext context) {
        List<FeatureExtractor<Element>> featuresExtractors = new ArrayList<>();
        featuresExtractors.add(new IsNerPresentFE(FIELD_DATE));
        return featuresExtractors;
    }



    @Named("processors")
    public List<Processor<IeDocument>> basePostProcessor() {
        List<Processor<IeDocument>> processorList = new ArrayList<>();
       // processorList.add(new ExpandPostProcessor());
        processorList.add(new Assignment1DatePostProcessor());
        return processorList;
    }


    @Named("hpoConfiguration")
    public HpoConfiguration hpoConfiguration(ConfigurationContext context){
        return new HpoConfiguration.Builder()
                .timeLimit(35, TimeUnit.MINUTES)
                .maxExperimentsWithSameScore(5)
                .build();
    }


}