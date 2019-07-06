package com.viveknarang.nora.main;

import com.viveknarang.nora.model.MongoDBConnector;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Vivek Narang
 */
public class Loader {

    final static Logger logger = Logger.getLogger(Loader.class);
    public String database;
    public String collection;
    public int batchSize;
    public List<Document> docs = new LinkedList<>();
    int noOfRecords;
    MongoDBConnector db = MongoDBConnector.getInstance();


    public Loader(String database, String collection, int batchSize, int noOfRecords) {

        super();

        this.database = database;
        this.collection = collection;
        this.batchSize = batchSize;
        this.noOfRecords = noOfRecords;
        db.connect();
        db.getDatabase(database);
        db.getCollection(collection);

        load();
    }

    public void load() {

        try {

            for (int i = 0; i < noOfRecords; i++) {

                if (i != 0 && i % batchSize == 0) {
                    db.insert(docs);
                    docs = new LinkedList<>();
                }

                docs.add(Transformer.getTransformedRows().get(i));

            }

            db.closeConnection();

        } catch (Exception e) {
            logger.error("Exception: " + e.toString());
        }

    }

}