/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson10;

import com.workfusion.vds.sdk.api.nlp.annotator.Annotator;
import com.workfusion.vds.sdk.api.nlp.model.Document;
import com.workfusion.vds.sdk.api.nlp.model.NamedEntity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Assignment 2
 */


/**
 * <p><b>Assignment 2</b></p>
 * <p>
 *     Provide a custom Annotator that adds Named Entity elements for Tokens containing an email. As an template, use
 *     {@link Assignment2EmailNerAnnotator}.
 * </p>
 * <p>Tips:</p>
 * <ul>
 *     <li>Use the following regular expression for emails: \b[\w.%-]+@[-.\w]+\.[A-Za-z]{2,4}\b</li>
 * </ul>
 */
public class Assignment2EmailNerAnnotator implements Annotator<Document> {

    /**
     * Regex to match an email.
     */
    private static final String EMAIL_REGEXP = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";

    /**
     * Type for {@link NamedEntity} to use.
     */
    private static final String NER_TYPE = "email";

    @Override
    public void process(Document document) {

        //TODO: PUT YOUR CODE HERE
        Pattern myPattern = Pattern.compile(EMAIL_REGEXP);
        Matcher myMatcher = myPattern.matcher(document.getText());
        while (myMatcher.find()) {

            document.add(NamedEntity.descriptor()
                    .setBegin(myMatcher.start())
                    .setEnd(myMatcher.end())
                    .setType("email"));
        }

    }




    }
