package com.viveknarang.nora.main;

import com.google.gson.Gson;
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
    MongoDBConnector db = MongoDBConnector.getInstance();


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

            for (int i = 1; i <= Transformer.getTransformedRows().size(); i++) {

                if (i % batchSize == 0) {
                    logger.info("Dumping " + batchSize + " documents into the list");
                    db.insert(docs);
                    docs = new LinkedList<>();
                }

                this.insertIntoList(Transformer.getTransformedRowsHeader(), Transformer.getTransformedRows().get(i - 1));

            }

            db.closeConnection();

        } catch (Exception e) {
            logger.error("Exception: " + e.toString());
        }

    }

    public void insertIntoList(List<String> header, List<String> record) {

        Document document = new Document();

        for (int i = 0; i < header.size(); i++) {
            document.put(header.get(i), record.get(i));
        }

        docs.add(document);
    }


}