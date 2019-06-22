package com.viveknarang.nora.main;

import com.viveknarang.nora.model.MongoDBConnector;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

/**
 * @author Vivek Narang
 */
public class Loader {

    final static Logger logger = Logger.getLogger(Loader.class);

    MongoDBConnector db = MongoDBConnector.getInstance();

    public int batchSize = 100000;

    public Loader() {

        super();
        System.out.println(db.getDatabase("Test"));
        db.getCollection("Collection");

        try {

            Gson gson = new Gson();

            for (int i = 1; i <= Transformer.transformedRows.size(); i++) {

                if (i % batchSize == 0) {
                    logger.info("Dumping " + batchSize + " documents into list");
                    db.commit();
                }

                db.insertIntoList(Extractor.headers, Transformer.transformedRows.get(i - 1));

            }


            db.closeConnection();

        } catch (Exception e) {
            logger.error("Exception: " + e.toString());
        }
    }

}
