/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson10.run;

import com.workfusion.lab.lesson10.model.Assignment3Model;
import com.workfusion.vds.sdk.api.nlp.configuration.FieldInfo;
import com.workfusion.vds.sdk.api.nlp.configuration.FieldType;
import com.workfusion.vds.sdk.run.ModelRunner;
import com.workfusion.vds.sdk.run.config.LocalTrainingConfiguration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assignment3ModelTrainingRunner {

    /**
     * Input directory path to use.
     */
    private final static String INPUT_DIR_PATH = "lesson-10/data/train-cert";

    /**
     * Output directory path to use.
     */
    public final static String OUTPUT_DIR_PATH = "lesson-10/results_assignment3";

    /**
     * Field name to use.
     */
    public final static String FIELD_INVOICE_NUMBER = "invoice_number";

    public static void main(String[] args) throws Exception {
        System.setProperty("WORKFLOW_LOG_FOLDER", "./logs/");

        Path inputDirPath = Paths.get(INPUT_DIR_PATH);
        Path outputDirPath = Paths.get(OUTPUT_DIR_PATH);

        List<FieldInfo> fields = new ArrayList<>();

        fields.add(new FieldInfo.Builder("invoice_number")
                .type(FieldType.INVOICE_NUMBER)
                .required(true)
                .multiValue(false)
                .build());

        fields.add(new FieldInfo.Builder("invoice_date")
                .type(FieldType.INVOICE_DATE)
                .required(true)
                .multiValue(false)
                .build());

        fields.add(new FieldInfo.Builder("total_amount")
                .type(FieldType.TOTAL_AMOUNT)
                .required(true)
                .multiValue(false)
                .build());

        fields.add(new FieldInfo.Builder("supplier_name")
                .type(FieldType.FREE_TEXT)
                .required(true)
                .multiValue(false)
                .build());


        fields.add(new FieldInfo.Builder("email")
                .type(FieldType.EMAIL)
                .required(true)
                .multiValue(false)
                .build());

        fields.add(new FieldInfo.Builder("product")
                .type(FieldType.FREE_TEXT)
                .required(true)
                .multiValue(true)
                .build());

        fields.add(new FieldInfo.Builder("price")
                .type(FieldType.PRICE)
                .required(true)
                .multiValue(true)
                .build());

        fields.add(new FieldInfo.Builder("quantity")
                .type(FieldType.NUMBER)
                .required(true)
                .multiValue(true)
                .build());

        Map<String, Object> parameters = new HashMap<>();


        LocalTrainingConfiguration configuration = LocalTrainingConfiguration.builder()
                .inputDir(inputDirPath)
                .outputDir(outputDirPath)
                .fields(fields)
                .parameters(parameters)
                .build();
        ModelRunner.run(Assignment3Model.class, configuration);
    }
}