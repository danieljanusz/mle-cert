/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson10.run;

import com.workfusion.lab.lesson10.model.Assignment3Model;
import com.workfusion.vds.sdk.run.ModelRunner;
import com.workfusion.vds.sdk.run.config.LocalExecutionConfiguration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Assignment3ModelExecutionRunner {

    /**
     * Model directory path to use.
     */
    private final static String MODEL_DIR_PATH = "lesson-10/results_assignment3/training/output/model";

    /**
     * Input directory path to use.
     */
    private final static String INPUT_DIR_PATH = "lesson-10/data/validation-cert/validation";

    /**
     * Output directory path to use.
     */
    public final static String OUTPUT_DIR_PATH = "lesson-10/results_assignment3/extract";

    public static void main(String[] args) throws Exception {
        System.setProperty("WORKFLOW_LOG_FOLDER", "./logs/");

        Path trainedModelPath = Paths.get(MODEL_DIR_PATH);
        Path inputDirPath = Paths.get(INPUT_DIR_PATH);
        Path outputDirPath = Paths.get(OUTPUT_DIR_PATH);

        Map<String, Object> parameters = new HashMap<>();

        LocalExecutionConfiguration configuration = LocalExecutionConfiguration.builder()
                .inputDir(inputDirPath)
                .outputDir(outputDirPath)
                .trainedModelDir(trainedModelPath)
                .parameters(parameters)
                .build();
        ModelRunner.run(Assignment3Model.class, configuration);
    }
}