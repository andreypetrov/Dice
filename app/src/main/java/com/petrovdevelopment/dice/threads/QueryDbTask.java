package com.petrovdevelopment.dice.threads;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;

import com.petrovdevelopment.common.util.U;
import com.petrovdevelopment.dice.MainApplication;
import com.petrovdevelopment.dice.utils.DatabaseHelper;

/**
 * Created by Andrey Petrov on 2014-12-14.
 */
public class QueryDbTask extends AsyncTask<Object, Void, Integer> {
    private MainApplication mainApplication;
    private Cursor mWordsCursor;
    private DatabaseHelper mDb;

    private ProgressDialog progressDialog;

    public QueryDbTask(MainApplication mainApplication) {
        super();

        this.mainApplication = mainApplication;
        progressDialog = new ProgressDialog(mainApplication);
    }

    // can use UI thread here
    protected void onPreExecute() {
//        ProgressBar progressBar = new ProgressBar(mainApplication);
//        progressBar.setIndeterminateDrawable(mainApplication.getResources().getDrawable(R.drawable.progress_spinner));
//
//        progressDialog.show();
//        // this call need to be after show!
//        progressDialog.setContentView(progressBar);
    }

    /**
     * Open database, get the random words and load then into a list of word models.
     * This list is kept by the main Game model.
     * Automatically done on worker thread (separate from UI thread)
     */
    protected Integer doInBackground(final Object... args) {
        //Game game = ((MainApplication) mainApplication.getApplicationContext()).getGame();
        //int wordsCount = game.getWordsCountPerGame();

        Cursor diceCursor = mainApplication.getDbHelper().getDice();
        U.log(this, diceCursor);
        return 0;
//        game.initFromCursor(mWordsCursor);
//        return game.getWordsCountPerGame();
    }

    // can use UI thread here
    protected void onPostExecute(Integer result) {
//     if(progressDialog!=null)   progressDialog.dismiss();
//        U.log(this, "result is: " + result);

        //mainApplication.onPostLoadModelExecute();
    }
}