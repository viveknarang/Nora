package com.viveknarang.nora.main;

import com.viveknarang.nora.model.MongoDBConnector;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Vivek Narang
 */
public class Loader {

    final static Logger logger = Logger.getLogger(Loader.class);

    MongoDBConnector db = MongoDBConnector.getInstance();

    public String database;
    public String collection;
    public int batchSize;
    public List<Document> docs = new LinkedList<>();


    public Loader(String database, String collection, int batchSize) {

        super();

        this.database = database;
        this.collection = collection;
        this.batchSize = batchSize;

        db.connect();
        db.getDatabase(database);
        db.getCollection(collection);

        load();
    }

    public void load() {


        try {

            Gson gson = new Gson();

            for (int i = 1; i <= Transformer.transformedRows.size(); i++) {

                if (i % batchSize == 0) {
                    logger.info("Dumping " + batchSize + " documents into the list");
                    db.insert(docs);
                    docs = new LinkedList<>();
                }

                this.insertIntoList(Extractor.headers, Transformer.transformedRows.get(i - 1));

            }

            db.closeConnection();

        } catch (Exception e) {
            logger.error("Exception: " + e.toString());
        }

    }

    public void insertIntoList(String[] header, List<String> record) {

        Document document = new Document();

        for (int i = 0; i < header.length; i++) {
            document.put(header[i], record.get(i));
        }

        docs.add(document);
    }


}