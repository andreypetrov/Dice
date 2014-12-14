package com.petrovdevelopment.dice.activities;

import android.app.Activity;
import android.os.Bundle;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.petrovdevelopment.common.util.U;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Test couchbase lite
 * Created by Andrey Petrov on 2014-11-23.
 */
public class CouchbaseLiteActivity extends Activity {
    private Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            manager = new Manager(new AndroidContext(this), Manager.DEFAULT_OPTIONS);
            U.log(this, "manager created");
        } catch (IOException e) {
            U.log(this, "cannot create manager object");
        }
        createDb();

    }

    private void createDb() {
        String dbname = "hello";
        if (!Manager.isValidDatabaseName(dbname)) {
            U.log(this, "bad database name");
            return;
        }
        //create new database
        Database database;
        try {
            database = manager.getDatabase(dbname);
            U.log(this, "database created");
        } catch (CouchbaseLiteException e) {
            U.log(this, "cannot get database");
            return;
        }
// get the current date and time
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Calendar calendar = GregorianCalendar.getInstance();
        String currentTimeString = dateFormatter.format(calendar.getTime());
// create an object that contains data for a document
        Map<String, Object> docContent = new HashMap<String, Object>();
        docContent.put("message", "Hello Couchbase Lite");
        docContent.put("creationDate", currentTimeString);
// display the data for the new document
        U.log(this, "docContent=" + String.valueOf(docContent));
// create an empty document
        Document document = database.createDocument();
// add content to document and write the document to the database
        try {
            document.putProperties(docContent);
            U.log(this, "Document written to database named " + dbname + " with ID = " + document.getId());
        } catch (CouchbaseLiteException e) {
            U.log(this, "Cannot write document to database " + e);
        }
// save the ID of the new document
        String docID = document.getId();

        // retrieve the document from the database
        Document retrievedDocument = database.getDocument(docID);
        // display the retrieved document
        U.log(this, "retrievedDocument=" + String.valueOf(retrievedDocument.getProperties()));

        // update the document
        Map<String, Object> updatedProperties = new HashMap<String, Object>();
        updatedProperties.putAll(retrievedDocument.getProperties());
        updatedProperties.put ("message", "We're having a heat wave!");
        updatedProperties.put ("temperature", "95");
        try {
            retrievedDocument.putProperties(updatedProperties);
            U.log(this, "updated retrievedDocument=" + String.valueOf(retrievedDocument.getProperties()));
        } catch (CouchbaseLiteException e) {
            U.log(this, "Cannot update document " + e);
        }

        // delete the document
        try {
            retrievedDocument.delete();
           U.log(this, "Deleted document, deletion status = " + retrievedDocument.isDeleted());
        } catch (CouchbaseLiteException e) {
            U.log(this, "Cannot delete document " + e);
        }
    }
}