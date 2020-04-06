/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson10.run;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.workfusion.lab.lesson10.model.Assignment1Model;
import com.workfusion.vds.sdk.api.nlp.configuration.FieldInfo;
import com.workfusion.vds.sdk.api.nlp.configuration.FieldType;
import com.workfusion.vds.sdk.run.ModelRunner;
import com.workfusion.vds.sdk.run.config.LocalTrainingConfiguration;

public class Assignment1ModelTrainingRunner {

    /**
     * Input directory path to use.
     */
    private final static String INPUT_DIR_PATH = "C:\\Users\\Daniel\\workfusion-workspace\\ml_sdk_lessons\\lesson-10\\data\\test";

    /**
     * Output directory path to use.
     */
    public final static String OUTPUT_DIR_PATH = "C:\\Users\\Daniel\\workfusion-workspace\\ml_sdk_lessons\\lesson-10\\results_assignment1";

    /**
     * Field name to use.
     */
    public final static String FIELD_INVOICE_NUMBER = "invoice_number";

    public static void main(String[] args) throws Exception {
        System.setProperty("WORKFLOW_LOG_FOLDER", "./logs/");

        //TODO Configure input/output
        Path inputDirPath = Paths.get(INPUT_DIR_PATH);
        Path outputDirPath = Paths.get(OUTPUT_DIR_PATH);

        //TODO Configure fields according to your use-case
        List<FieldInfo> fields = new ArrayList<>();

        fields.add(new FieldInfo.Builder("invoice_number")
                .type(FieldType.INVOICE_TYPE)
                .required(true)
                .multiValue(false)
                .build());


        fields.add(new FieldInfo.Builder("product")
                .type(FieldType.FREE_TEXT)
                .required(true)
                .multiValue(true)
                .build());

        //TODO add parameters if needed.
        Map<String, Object> parameters = new HashMap<>();


        LocalTrainingConfiguration configuration = LocalTrainingConfiguration.builder()
                .inputDir(inputDirPath)
                .outputDir(outputDirPath)
                .fields(fields)
                .parameters(parameters)
                .build();

        ModelRunner.run(Assignment1Model.class, configuration);
    }

}