/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson10.fe;

import com.workfusion.vds.sdk.api.nlp.fe.Feature;
import com.workfusion.vds.sdk.api.nlp.fe.FeatureExtractor;
import com.workfusion.vds.sdk.api.nlp.model.Document;
import com.workfusion.vds.sdk.api.nlp.model.Element;
import com.workfusion.vds.sdk.api.nlp.model.NamedEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Assignment 1
 */
//@FeatureName(IsNerPresentFE.FEATURE_NAME)
public class IsNerPresentFE<T extends Element> implements FeatureExtractor<T> {

    /**
     * Name of {@link Feature} the feature extractor produces.
     */
//    public final static String FEATURE_NAME = "nerFeature";

    /**
     * The Ner type to look for.
     */
    private final String nerType;

    /**
     * Create an instance of {@link FeatureExtractor} that detects if a token is inside the {@link NamedEntity} of the specified {@code type}.
     * @param type  type of {@link NamedEntity}
     */
    public IsNerPresentFE(String type) {
        this.nerType = type;
    }

    @Override
    public Collection<Feature> extract(Document document, T element) {
        List<Feature> result = new ArrayList<>();

        // TODO:  PUT YOU CODE HERE
        System.out.println("element "+element );
        //System.out.println("document get text "+ document.getText());
        List<NamedEntity>namedEntityList=new ArrayList<>();
        namedEntityList=document.findCovering(NamedEntity.class,element);
        if(namedEntityList.size()>0){
       //     System.out.println("NamedEntity found. size = "+ namedEntityList.size());
            if(namedEntityList.get(0).getType().toString().equals(this.nerType)){
               // System.out.println("IsNer present field added");
                Feature feature=new Feature(this.nerType,1.0);
                result.add(feature);
            }
        }
        return result;
    }

}