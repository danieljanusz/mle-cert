/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson10.config;

import com.workfusion.lab.lesson10.annotator.EmailNerAnnotator;
import com.workfusion.lab.lesson10.fe.IsNerPresentFE;
import com.workfusion.lab.lesson10.processing.*;
import com.workfusion.vds.nlp.hypermodel.ie.generic.GenericIeHypermodel;
import com.workfusion.vds.nlp.hypermodel.ie.generic.config.GenericIeHypermodelConfiguration;
import com.workfusion.vds.sdk.api.hpo.HpoConfiguration;
import com.workfusion.vds.sdk.api.hypermodel.ConfigurationContext;
import com.workfusion.vds.sdk.api.hypermodel.annotation.Import;
import com.workfusion.vds.sdk.api.hypermodel.annotation.ModelConfiguration;
import com.workfusion.vds.sdk.api.hypermodel.annotation.Named;
import com.workfusion.vds.sdk.api.nlp.annotator.Annotator;
import com.workfusion.vds.sdk.api.nlp.configuration.IeConfigurationContext;
import com.workfusion.vds.sdk.api.nlp.fe.FeatureExtractor;
import com.workfusion.vds.sdk.api.nlp.model.Document;
import com.workfusion.vds.sdk.api.nlp.model.Element;
import com.workfusion.vds.sdk.api.nlp.model.IeDocument;
import com.workfusion.vds.sdk.api.nlp.processing.Processor;
import com.workfusion.vds.sdk.nlp.component.annotator.EntityBoundaryAnnotator;
import com.workfusion.vds.sdk.nlp.component.annotator.tokenizer.MatcherTokenAnnotator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The model configuration class.
 */
@ModelConfiguration


@Import(configurations={
        @Import.Configuration(GenericIeHypermodelConfiguration.class)
})
//        ,
//        resources={
//                @Import.Resource(value="src/main/resources/certParameters/email/parameters.json",
//                        condition =@Filter(expression = "field.code == 'email'")),
//                @Import.Resource(value="src/main/resources/certParameters/invoice_date/parameters.json",
//                        condition =@Filter(expression = "field.code == 'invoice_date'")),
//                @Import.Resource(value="src/main/resources/certParameters/invoice_number/parameters.json",
//                        condition =@Filter(expression = "field.code == 'invoice_number'")),
//                @Import.Resource(value="src/main/resources/certParameters/price/parameters.json",
//                        condition =@Filter(expression = "field.code == 'price'")),
//                @Import.Resource(value="src/main/resources/certParameters/product/parameters.json",
//                        condition =@Filter(expression = "field.code == 'product'")),
//                @Import.Resource(value="src/main/resources/certParameters/quantity/parameters.json",
//                        condition =@Filter(expression = "field.code == 'quantity'")),
//                @Import.Resource(value="src/main/resources/certParameters/supplier_name/parameters.json",
//                        condition =@Filter(expression = "field.code == 'supplier_name'")),
//                @Import.Resource(value="src/main/resources/certParameters/total_amount/parameters.json",
//                        condition =@Filter(expression = "field.code == 'total_amount'"))
//        }
//)

public class Assignment3ModelConfiguration extends GenericIeHypermodel {


  private final static String TOKEN_REGEX = "[\\w@.,$%’-]+";

    @Named("annotators")
    public List<Annotator<Document>> getAnnotators(IeConfigurationContext context) {
        List<Annotator<Document>> annotators = new ArrayList<>();
        annotators.add(new MatcherTokenAnnotator(TOKEN_REGEX));
        annotators.add(new EntityBoundaryAnnotator());
        annotators.add(new EmailNerAnnotator());
        return annotators;
    }

    @Named("featureExtractors")
    public List<FeatureExtractor<Element>> getFeatureExtractors(IeConfigurationContext context) {
        List<FeatureExtractor<Element>> featuresExtractors = new ArrayList<>();
        featuresExtractors.add(new IsNerPresentFE<>("email"));
        featuresExtractors.add(new IsNerPresentFE<>("invoice_date"));
        featuresExtractors.add(new IsNerPresentFE<>("invoice_number"));
        featuresExtractors.add(new IsNerPresentFE<>("price"));
        featuresExtractors.add(new IsNerPresentFE<>("product"));
        featuresExtractors.add(new IsNerPresentFE<>("quantity"));
        featuresExtractors.add(new IsNerPresentFE<>("supplier_name"));
        featuresExtractors.add(new IsNerPresentFE<>("total_amount"));
        return featuresExtractors;
    }

    @Named("processors")
    public List<Processor<IeDocument>> basePostProcessor() {
        List<Processor<IeDocument>> processorList = new ArrayList<>();
        processorList.add(new InvoiceDatePostProcessor());
        processorList.add(new PricePostProcessor());
        processorList.add(new ProductPostProcessor());
        processorList.add(new QuantityPostProcessor());
        processorList.add(new TotalAmountPostProcessor());
        return processorList;
    }

    @Named("hpoConfiguration")
    public HpoConfiguration hpoConfiguration(ConfigurationContext context){
        return new HpoConfiguration.Builder()
                .timeLimit(5, TimeUnit.HOURS)
                .maxExperimentsWithSameScore(5)
                .build();
    }
}