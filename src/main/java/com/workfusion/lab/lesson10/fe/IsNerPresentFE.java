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

public class IsNerPresentFE<T extends Element> implements FeatureExtractor<T> {

    private final String nerType;
    public IsNerPresentFE(String type) {
        this.nerType = type;
    }

    @Override
    public Collection<Feature> extract(Document document, T element) {
        List<Feature> result = new ArrayList<>();
        List<NamedEntity>namedEntityList=new ArrayList<>();
        namedEntityList=document.findCovering(NamedEntity.class,element);
        if(namedEntityList.size()>0){
            if(namedEntityList.get(0).getType().toString().equals(this.nerType)){
                Feature feature=new Feature(this.nerType,1.0);
                result.add(feature);
            }
        }
        return result;
    }

}