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
    public String containerCollection;
    public int batchSize;
    int noOfRecords;

    public List<Document> docs = new LinkedList<>();
    MongoDBConnector db = MongoDBConnector.getInstance();


    public Loader(String database, String collection, int batchSize, int noOfRecords, String containerCollection) {

        super();

        this.database = database;
        this.collection = collection;
        this.containerCollection = containerCollection;
        this.batchSize = batchSize;
        this.noOfRecords = noOfRecords;

        db.connect();
        db.getDatabase(database);

        load();
        loadContainerCollection();

        db.closeConnection();

    }

    public void load() {

        try {

            db.getCollection(collection);

            for (int i = 0; i < noOfRecords; i++) {

                if (i != 0 && i % batchSize == 0) {
                    db.insert(docs);
                    docs = new LinkedList<>();
                }

                docs.add(Transformer.getTransformedRows().get(i));

            }

        } catch (Exception e) {
            logger.error("Exception: " + e.toString());
        }

    }


    public void loadContainerCollection() {

        docs = new LinkedList<>();

        try {

            db.getCollection(containerCollection);


            for (String key : Transformer.getTransformedGroups().keySet()) {


                docs.add(Transformer.getTransformedGroups().get(key));


            }

            db.insert(docs);


        } catch (Exception e) {
            logger.error("Exception: " + e.toString());
        }

    }

}