package com.viveknarang.nora.model;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.List;

public class MongoDBConnector {

    final static Logger logger = Logger.getLogger(MongoDBConnector.class);

    private static MongoDBConnector single_instance = null;

    public String s;

    public String address = "localhost";
    public int port = 27017;
    public MongoClient mongo;
    public MongoDatabase database;
    public MongoCollection dbcollection;

    // private constructor for singleton class
    private MongoDBConnector() {
    }

    public static MongoDBConnector getInstance() {
        if (single_instance == null)
            single_instance = new MongoDBConnector();

        return single_instance;
    }

    public void connect() {

        logger.info("MongoDBConnector::connect() >> Attempting to connect to MongoDB at: " + address + " port: " + port);

        // Creating a Mongo client
        mongo = new MongoClient(address, port);

        // Creating Credentials
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());

        logger.info("Connected to the database successfully");

        logger.info("Credentials ::" + credential);

    }

    public MongoClient getClient() {
        return mongo;
    }

    public MongoDatabase getDatabase(String db) {
        logger.info("MongoDBConnector::getDatabase() Start");
        database = mongo.getDatabase(db);
        logger.info("MongoDBConnector::getDatabase() End");

        return database;
    }

    public void createCollection(String collection) {
        logger.info("MongoDBConnector::createCollection() Start");
        database.createCollection(collection);
        System.out.println("Collection created successfully");
        logger.info("MongoDBConnector::createCollection() End");
    }

    public void getCollection(String collection) {
        logger.info("MongoDBConnector::getCollection() Start");
        dbcollection = database.getCollection(collection);
        logger.info("MongoDBConnector::getCollection() End");
    }

    public void insert(List<Document> docs) throws Exception {
        logger.info("Committing + " + docs.size() + " documents into MongoDB");
        dbcollection.insertMany(docs);
        docs.clear();
    }

    public void closeConnection() {
        logger.info("MongoDBConnector::closeConnection() Start");
        mongo.close();
        logger.info("MongoDBConnector::closeConnection() End");
    }


}
