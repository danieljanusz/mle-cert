/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson10.config;

import com.workfusion.lab.lesson10.fe.ColumnIndexFE;
import com.workfusion.lab.lesson10.fe.RowIndexFE;
import com.workfusion.lab.lesson10.fe.TableNumberFE;
import com.workfusion.lab.lesson10.processing.ExpandPostProcessor;
import com.workfusion.vds.nlp.hypermodel.ie.generic.config.GenericIeHypermodelConfiguration;
import com.workfusion.vds.sdk.api.hpo.Dimensions;
import com.workfusion.vds.sdk.api.hpo.HpoConfiguration;
import com.workfusion.vds.sdk.api.hpo.ParameterSpace;
import com.workfusion.vds.sdk.api.hypermodel.ConfigurationContext;
import com.workfusion.vds.sdk.api.hypermodel.annotation.Filter;
import com.workfusion.vds.sdk.api.hypermodel.annotation.Import;
import com.workfusion.vds.sdk.api.hypermodel.annotation.ModelConfiguration;
import com.workfusion.vds.sdk.api.hypermodel.annotation.Named;
import com.workfusion.vds.sdk.api.nlp.configuration.IeConfigurationContext;
import com.workfusion.vds.sdk.api.nlp.fe.FeatureExtractor;
import com.workfusion.vds.sdk.api.nlp.model.Element;
import com.workfusion.vds.sdk.api.nlp.model.Field;
import com.workfusion.vds.sdk.api.nlp.model.IeDocument;
import com.workfusion.vds.sdk.api.nlp.processing.Processor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The model configuration class.
 */
@ModelConfiguration
@Import(
        configurations = {
        @Import.Configuration(value = GenericIeHypermodelConfiguration.class)
        },
        resources = {
        //@Import.Resource(value="C:\\Users\\Daniel\\workfusion-workspace\\ml_sdk_lessons\\lesson-10\\src\\main\\resources\\parameters\\invoice_number\\parameters.json",

         @Import.Resource(value="C:\\Users\\Daniel\\workfusion-workspace\\ml_sdk_lessons\\lesson-10\\src\\main\\resources\\parameters.json",
        condition = @Filter(expression = "field.code eq 'invoice_number'"))
}
//parameter/invoice_number/parameters.json
)


public class Assignment2ModelConfiguration {
    /**
     * Name of {@link Field} representing an invoice number.
     */
    public final static String FIELD_INVOICE_NUMBER = "invoice_number";

    /**
     * Name of {@link Field} representing a product.
     */
    public final static String FIELD_PRODUCT = "product";



    @Named("featureExtractors1")
    public FeatureExtractor getFeatureExtractor1 (){
      FeatureExtractor featureExtractor=new RowIndexFE();
      return featureExtractor;
    };

    @Named("featureExtractors2")
    public FeatureExtractor getFeatureExtractor2 (){
        FeatureExtractor featureExtractor=new TableNumberFE();
        return featureExtractor;
    };

    @Named("featureExtractors3")
    public FeatureExtractor getFeatureExtractor3 (){
        FeatureExtractor featureExtractor=new ColumnIndexFE();
        return featureExtractor;
    };


    @Named("parameterSpace")
    public ParameterSpace configure(IeConfigurationContext context) {
        ParameterSpace.Builder builder = new ParameterSpace.Builder();
        switch (context.getField().getCode()){

            case FIELD_PRODUCT: {
                builder.add(Dimensions.selectOne("featureExtractors1","featureExtractors2"));
                break;
            }
            case FIELD_INVOICE_NUMBER:{
                builder.add(Dimensions.required("featureExtractors3"));
                break;
            }

        }

        // TODO:  PUT YOU CODE HERE

        return builder.build();
    }
    @Named("processors")
    public List<Processor<IeDocument>> basePostProcessor() {
        // TODO:  PUT YOU CODE HERE (IF NEEDED)
        List<Processor<IeDocument>>processorList=new ArrayList<>();
        processorList.add(new ExpandPostProcessor());
        return processorList;
    }

    @Named("hpoConfiguration")
    public HpoConfiguration hpoConfiguration(ConfigurationContext context){
        return new HpoConfiguration.Builder()
                .timeLimit(600, TimeUnit.SECONDS)
                .maxExperimentsWithSameScore(5)
                .build();
    }

}