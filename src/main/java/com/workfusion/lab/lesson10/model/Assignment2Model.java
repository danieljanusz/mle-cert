/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson10.model;

import com.workfusion.lab.lesson10.config.Assignment2ModelConfiguration;
import com.workfusion.vds.nlp.hypermodel.ie.generic.GenericIeHypermodel;
import com.workfusion.vds.sdk.api.hypermodel.ModelType;
import com.workfusion.vds.sdk.api.hypermodel.annotation.HypermodelConfiguration;
import com.workfusion.vds.sdk.api.hypermodel.annotation.ModelDescription;

@ModelDescription(
        code = "wf-lab-ml-sdk-lesson-10-2-model",
        title = "WF Lab ML-SDK Lesson 10.2 Model (1.0)",
        description = "WF Lab ML-SDK Lesson 10.2 Model (1.0)",
        version = "1.0",
        type = ModelType.IE
)
@HypermodelConfiguration(Assignment2ModelConfiguration.class)
public class Assignment2Model extends GenericIeHypermodel {

    public Assignment2Model() throws Exception {
    }

}