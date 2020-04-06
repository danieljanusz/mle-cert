/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson10.config;

import com.workfusion.vds.nlp.hypermodel.ie.generic.config.GenericIeHypermodelConfiguration;
import com.workfusion.vds.sdk.api.hypermodel.annotation.Import;
import com.workfusion.vds.sdk.api.hypermodel.annotation.ModelConfiguration;
import com.workfusion.vds.sdk.api.hypermodel.annotation.Named;
import com.workfusion.vds.sdk.api.nlp.annotator.Annotator;
import com.workfusion.vds.sdk.api.nlp.fe.FeatureExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * The model configuration class.
 */
@ModelConfiguration
// TODO:  PUT YOU CODE HERE
@Import(configurations = {
        @Import.Configuration(GenericIeHypermodelConfiguration.class)
})

public class Assignment1ModelConfiguration {




}