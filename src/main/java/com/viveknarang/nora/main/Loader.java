package com.viveknarang.nora.main;

import com.mongodb.client.MongoDatabase;
import com.viveknarang.nora.model.MongoDBConnector;

/**
 * 
 * @author Vivek Narang
 *
 */
public class Loader {

    MongoDBConnector db = MongoDBConnector.getInstance();

    public Loader() {
        super();
        System.out.println(db.getDatabase("Test"));
        db.getCollection("Collection");
        db.closeConnection();
    }

}
