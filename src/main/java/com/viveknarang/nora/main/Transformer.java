package com.viveknarang.nora.main;

import com.viveknarang.nora.model.ETLJob;
import com.viveknarang.nora.model.Rule;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Vivek Narang
 */
public class Transformer {

    private final static Logger logger = Logger.getLogger(Transformer.class);
    private static HashMap<String, TreeMap<Integer, List<String>>> rulesMap = new HashMap<>();
    private static HashMap<Integer, Rule> metaMap = new HashMap<>();
    private static HashMap<Integer, String> fieldIndexMap = new HashMap<>();
    private static HashMap<String, Integer> reverseFieldIndexMap = new HashMap<>();
    private static List<Document> transformedRows = new LinkedList<>();
    private static HashMap<String, Document> transformedGroups = new HashMap<>();
    private static String groupFieldName = "";

    public Transformer() {
        super();
    }

    public static List<Document> getTransformedRows() {
        return transformedRows;
    }

    public static String getGroupFieldName() {
        return groupFieldName;
    }

    public static void setGroupFieldName(String groupFieldName) {
        Transformer.groupFieldName = groupFieldName;
    }

    public static HashMap<String, Document> getTransformedGroups() {
        return transformedGroups;
    }

    public static void setTransformedGroups(HashMap<String, Document> transformedGroups) {
        Transformer.transformedGroups = transformedGroups;
    }

    public static void transform(ETLJob job, List<Rule> rules, List<String[]> rows, String fileName, String[] headers, int noOfRecords) {

        logger.info("Transformer:transform()::Start");
        long s = System.currentTimeMillis();
        rulesMap = new HashMap<>();

        int i;

        for (i = 0; i < headers.length; i++) {
            fieldIndexMap.put(i, headers[i]);
            reverseFieldIndexMap.put(headers[i], i);
        }

        for (Rule rule : rules) {

            if (rule.getMarkFieldForGrouping().equalsIgnoreCase("true")) {
                setGroupFieldName(rule.getMapToField());
            }

            metaMap.put(reverseFieldIndexMap.get(rule.getMapFromField()), rule);

            TreeMap<Integer, List<String>> rules$ = new TreeMap<>();

            if (rule.getFileName().equals(fileName)) {

                for (int ti = 0; ti < rule.getTransform().size(); ti++) {
                    rules$.put(Integer.parseInt(rule.getTransform().get(ti).get(0)), rule.getTransform().get(ti));
                }

                rulesMap.put(rule.getMapFromField(), rules$);
            }

        }

        int p = 0;

        for (String[] row : rows) {

            p++;

            if (p == noOfRecords) {
                break;
            }

            Document doc = new Document();

            for (i = 0; i < row.length; i++) {

                if (rulesMap.containsKey(fieldIndexMap.get(i))) {

                    if (metaMap.get(i).getIgnoreField().equalsIgnoreCase("false")) {

                        if (metaMap.get(i).getOverwrite().equalsIgnoreCase("true")) {

                            if (metaMap.get(i) != null && metaMap.get(i).getMapToField() != null) {

                                doc.put(metaMap.get(i).getMapToField(), transform(row[i], rulesMap.get(fieldIndexMap.get(i))));

                            } else {

                                doc.put(headers[i], transform(row[i], rulesMap.get(fieldIndexMap.get(i))));

                            }

                        } else {


                            doc.put(headers[i], row[i]);
                            doc.put(metaMap.get(i).getMapToField(), transform(row[i], rulesMap.get(fieldIndexMap.get(i))));


                        }

                    }

                } else {


                    doc.put(headers[i], row[i]);

                }
            }

            if (!transformedGroups.containsKey(doc.getString(groupFieldName))) {

                Map<String, Object> map = new LinkedHashMap<>();

                for (String key : doc.keySet()) {

                    map.put(key, doc.get(key));

                }

                for (String key : doc.keySet()) {

                    List<Object> list = new LinkedList<>();

                    if (doc.get(key) instanceof Collection) {

                        List<Object> lst = (List<Object>) doc.get(key);

                        for (Object obj : lst) {
                            list.add(obj);
                        }

                    } else {
                        list.add(doc.get(key));
                    }

                    map.put(key + "_list", list);

                }

                Document dc = new Document();
                dc.putAll(map);

                transformedGroups.put(doc.getString(groupFieldName), dc);

            } else {

                Document docm = transformedGroups.get(doc.getString(groupFieldName));

                for (String key : docm.keySet()) {

                    if (key.contains("_list")) {

                        List<Object> lst = (List<Object>) docm.get(key);

                        String sub_key = key.substring(0, key.length() - 5);

                        if (doc.get(sub_key) instanceof Collection) {

                            List<Object> lsr = (List<Object>) doc.get(sub_key);

                            for (Object obj : lsr) {
                                lst.add(obj);
                            }

                        } else {

                            lst.add(doc.get(sub_key));

                        }

                        Set<Object> st = new HashSet<>();
                        st.addAll(lst);
                        lst.clear();
                        lst.addAll(st);
                        docm.put(key, lst);

                    }

                }

                transformedGroups.put(doc.getString(groupFieldName), docm);

            }

            transformedRows.add(doc);


        }

        System.out.println(transformedGroups.get("Ukraine").toJson());

        System.out.println(transformedRows.get(0).toJson());

        long e = System.currentTimeMillis();
        logger.info("Transformer:transform()::Complete >> Transformation completed in: " + ((e - s) / 1000)
                + " seconds on a total of: " + rows.size() + " records & " + rulesMap.size() + " fields...");

    }

    private static Object transform(String field, TreeMap<Integer, List<String>> rule_) {


        for (Integer rule : rule_.keySet()) {

            if (rule_.get(rule).get(1).equals("replaceNullWith") && field.length() == 0
                    && rule_.get(rule).get(2).equals("true")) {

                field = rule_.get(rule).get(3);

            } else {

                if (rule_.get(rule).get(1).equals("prepareList")) {

                    List<String> transformed = new LinkedList<>(Arrays.asList(field.trim().split(rule_.get(rule).get(2))));
                    return transformed;

                } else if (rule_.get(rule).get(1).equals("truncateAfter") && rule_.get(rule).get(2).equals("true")) {


                    if (field.length() > Integer.parseInt(rule_.get(rule).get(3))) {
                        field = field.substring(0, Integer.parseInt(rule_.get(rule).get(3))) + rule_.get(rule).get(4);
                    }

                } else if (rule_.get(rule).get(1).equals("replace") && rule_.get(rule).get(2).equals("true")) {


                    field = field.replaceAll(rule_.get(rule).get(3), rule_.get(rule).get(4));

                } else if (rule_.get(rule).get(1).equals("remove") && rule_.get(rule).get(2).equals("true")) {


                    field = field.replace(rule_.get(rule).get(3), "");

                } else if (rule_.get(rule).get(1).equals("extractFromEndTo") && rule_.get(rule).get(2).equals("true")
                        && field.contains(rule_.get(rule).get(3))) {
                    String[] px = field.split(Pattern.quote(rule_.get(rule).get(3)));

                    field = px[px.length - 1];

                } else if (rule_.get(rule).get(1).equals("extractFromFrontTo") && rule_.get(rule).get(2).equals("true")
                        && field.contains(rule_.get(rule).get(3))) {
                    String[] px = field.split(Pattern.quote(rule_.get(rule).get(3)));

                    field = px[0];

                } else if (rule_.get(rule).get(1).equals("trim") && rule_.get(rule).get(2).equals("true")) {

                    field = field.trim();

                } else if (rule_.get(rule).get(1).equals("toUpperCase") && rule_.get(rule).get(2).equals("true")) {

                    field = field.toUpperCase();

                } else if (rule_.get(rule).get(1).equals("toLowerCase") && rule_.get(rule).get(2).equals("true")) {

                    field = field.toLowerCase();

                } else if (rule_.get(rule).get(1).equals("extractFromTo") && rule_.get(rule).get(2).equals("true")
                        && field.length() > Integer.parseInt(rule_.get(rule).get(4))) {

                    field = field.substring(Integer.parseInt(rule_.get(rule).get(3)),
                            Integer.parseInt(rule_.get(rule).get(4)));

                } else if (rule_.get(rule).get(1).equals("castInt")) {

                    return Integer.parseInt(field);

                } else if (rule_.get(rule).get(1).equals("castFloat")) {

                    return Float.parseFloat(field);

                } else if (rule_.get(rule).get(1).equals("castDouble")) {

                    return Double.parseDouble(field);

                }

            }

        }

        return field;
    }

}