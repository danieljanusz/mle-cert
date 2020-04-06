/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson10;

import java.io.File;
import java.util.Map;

import org.junit.Test;

import com.workfusion.lab.lesson10.fe.ColumnIndexFE;
import com.workfusion.lab.lesson10.fe.RowIndexFE;
import com.workfusion.lab.lesson10.fe.TableNumberFE;
import com.workfusion.lab.lesson10.run.Assignment1ModelExecutionRunner;
import com.workfusion.lab.lesson10.run.Assignment1ModelTrainingRunner;
import com.workfusion.lab.lesson10.run.Assignment2ModelExecutionRunner;
import com.workfusion.lab.lesson10.run.Assignment2ModelTrainingRunner;
import com.workfusion.lab.utils.BaseLessonTest;
import com.workfusion.nlp.uima.workflow.task.hpo.HpoConfiguration;

import static org.assertj.core.api.Java6Assertions.assertThat;

import static com.workfusion.lab.lesson10.run.Assignment1ModelExecutionRunner.OUTPUT_DIR_PATH;

public class Lesson10Test extends BaseLessonTest {

    /**
     * Assignment 1
     * <p>
     * Provide a model configuration with HPO.
     * Update the Runners to use the provided training and test files.
     * Add the field "invoice_number" with type FieldType.INVOICE_TYPE for the training configuration.
     * Train the model. Check the training statistics.
     * Execute the model. Check the execution statistics.
     */
    @Test
    public void assignment1() throws Exception {

        executeRunner(Assignment1ModelTrainingRunner.class);

        File hpoConfigFile = getHpoConfigFileForIESubmodel(Assignment1ModelTrainingRunner.OUTPUT_DIR_PATH, Assignment1ModelTrainingRunner.FIELD_INVOICE_NUMBER);
        assertThat(hpoConfigFile).exists();

        HpoConfiguration hpoConfiguration = getHpoConfiguration(hpoConfigFile);
        assertThat(hpoConfiguration).isNotNull();

        // Obtains training statistics
        Map<String, FieldStatistic> trainingStatistics = getTrainingFieldStatistics(Assignment1ModelTrainingRunner.OUTPUT_DIR_PATH);
        // Check the field statistics
        checkFieldStatistics(trainingStatistics, Assignment1ModelTrainingRunner.FIELD_INVOICE_NUMBER, 0.9, 0.6);

        executeRunner(Assignment1ModelExecutionRunner.class);
        Map<String, FieldStatistic> executionStatistics = getExecutionFieldStatistics(Assignment1ModelTrainingRunner.OUTPUT_DIR_PATH + "/extract");

        // Check the field statistics
        checkFieldStatistics(executionStatistics, Assignment1ModelTrainingRunner.FIELD_INVOICE_NUMBER, 0.9, 0.6);
    }

    /**
     * Assignment 2
     * <p>
     * Provide a model configuration with HPO.
     *  - fix the configuration for the field "invoice_number" by the configuration you obtained from assignment 1.
     *  - add feature extractor ColumnIndexFE() for field "invoice_number"
     *  - add one and only one from RowIndexFE() and TableNumberFE() feature extractor for field "product"
     *  - override postprocessor for fields "invoice_number" and "product" on ExpandPostProcessor() (don't need to rewrite it)
     *  Provide the next parameters for HPO:
     *  - HPO_TIME_LIMIT = 600 sec.
     *  - HPO_MAX_ITER_WITH_SAME_SCORE = 5
     * Update the Runners to use the provided training and test files.
     * Add the following fields for the training configuration:
     * - "invoice_number" with the FieldType.INVOICE_TYPE type
     * - "product" with the FieldType.FREE_TEXT type, multi-value
     * Train the model. Check the training statistics.
     * Execute the model. Check the execution statistics.
     */
    @Test
    public void assignment2() throws Exception {

        String REQUIRED_PIPELINE_AE_STRING = "{\"@type\":\"java.util.ArrayList\",\"@items\":[{\"@type\":\"com.workfusion.lab.lesson10.processing.ExpandPostProcessor\"}]}";

        executeRunner(Assignment2ModelTrainingRunner.class);

        File hpoConfigFile = getHpoConfigFileForIESubmodel(Assignment2ModelTrainingRunner.OUTPUT_DIR_PATH, Assignment2ModelTrainingRunner.FIELD_INVOICE_NUMBER);
        assertThat(hpoConfigFile).exists();

        HpoConfiguration hpoConfiguration = getHpoConfiguration(hpoConfigFile);
        assertThat(hpoConfiguration).isNotNull();

        //check HPO constant
        assertHpoConstant(hpoConfiguration);

        //test field "invoice_number"
        assertFieldInvoiceNumberFe(Assignment2ModelTrainingRunner.OUTPUT_DIR_PATH, "invoice_number", ColumnIndexFE.class);

        //test field "product"
        assertFieldProductFe(Assignment2ModelTrainingRunner.OUTPUT_DIR_PATH, "product", ColumnIndexFE.class, RowIndexFE.class, TableNumberFE.class);

        //test postprocessing
        assertPostProcessors(Assignment2ModelTrainingRunner.OUTPUT_DIR_PATH, REQUIRED_PIPELINE_AE_STRING);

        executeRunner(Assignment2ModelExecutionRunner.class);

        // Obtains training statistics
        Map<String, FieldStatistic> trainingStatistics = getTrainingFieldStatistics(Assignment2ModelTrainingRunner.OUTPUT_DIR_PATH);

        // Check the field statistics
        checkFieldStatistics(trainingStatistics, Assignment2ModelTrainingRunner.FIELD_INVOICE_NUMBER, 0.9, 0.5);
        checkFieldStatistics(trainingStatistics, Assignment2ModelTrainingRunner.FIELD_PRODUCT, 0.9, 0.5);

        Map<String, FieldStatistic> executionStatistics = getExecutionFieldStatistics(Assignment2ModelTrainingRunner.OUTPUT_DIR_PATH + "/extract");

        // Check the field statistics
        checkFieldStatistics(executionStatistics, Assignment2ModelTrainingRunner.FIELD_INVOICE_NUMBER, 0.9, 0.5);
        checkFieldStatistics(trainingStatistics, Assignment2ModelTrainingRunner.FIELD_PRODUCT, 0.9, 0.5);
    }

}