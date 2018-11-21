package com.getweb.playwotk.getweb;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.*;


public class MainActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private String url = "https://mobile.facebook.com/bnk48official.cherprang/";
    //private String url = "https://m.facebook.com/pg/bnk48official.cherprang/posts/?ref=page_internal&mt_nav=0";
    private ArrayList<String> mAuthorNameList = new ArrayList<>();
    private ArrayList<String> mBlogUploadDateList = new ArrayList<>();
    private ArrayList<String> mBlogTitleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("hello" , "hello");
        new Description().execute();
    }


    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {


                String URL_LOGIN = "https://m.facebook.com/login.php";
                String EMAIL = "CosmoCruz01@gmail.com";
                String PASSWORD = "1234Cosmo";

                Connection.Response facebook = Jsoup.connect(URL_LOGIN)
                        .data("email", EMAIL, "pass", PASSWORD)
                        .method(Connection.Method.POST)
                        .execute();

                Document doc = Jsoup.connect(url).timeout(120000).cookies(facebook.cookies()).get();
                Element descTag = doc.select("meta[name='description']").get(0);
                String content = descTag.attr("content");

                Log.i("get  web" , content.toString());


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView

            mProgressDialog.dismiss();
        }
    }

} //  end class


