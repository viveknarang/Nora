package com.viveknarang.nora.model;

import java.util.List;

/**
 * @author Vivek Narang
 */
public class Rule {

    private String fileName = null;
    private String mapFromField = null;
    private String overWrite = "false";
    private String mapToField = null;
    private String ignoreField = "false";
    private String markFieldForGrouping = "false";

    private List<List<String>> transform = null;

    public String getFileName() {
        return fileName;
    }

    public String getMapFromField() {
        return mapFromField;
    }

    public String getMapToField() {
        return mapToField;
    }

    public String getOverwrite() {
        return overWrite;
    }

    public String getIgnoreField() {
        return ignoreField;
    }

    public List<List<String>> getTransform() {
        return transform;
    }

    public String getMarkFieldForGrouping() {
        return markFieldForGrouping;
    }

}