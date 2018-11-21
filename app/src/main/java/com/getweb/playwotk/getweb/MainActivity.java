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
   // private String url = "https://mobile.facebook.com/bnk48official.cherprang/";
    private String url = "https://m.facebook.com/pg/bnk48official.cherprang/posts/?ref=page_internal&mt_nav=0";
    private String community_url ="https://m.facebook.com/pg/bnk48official.cherprang/community/";
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
//            mProgressDialog = new ProgressDialog(MainActivity.this);
//            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
//            mProgressDialog.setMessage("Loading...");
//            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.show();
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

                //Todo  get total like and follow
                Document commun_page = Jsoup.connect(community_url).timeout(120000).cookies(facebook.cookies()).get();
                Element reactionEle = commun_page.selectFirst("div[data-sigil='reaction-unit-logging']").selectFirst("div").selectFirst("div");
                String TotalLike  = reactionEle.select("div[class='_3_9m']").first().text();
                String TotalFollow  = reactionEle.select("div[class='_3_9m']").last().text();

                //Todo get static of lasted post

                Document doc = Jsoup.connect(url).timeout(120000).cookies(facebook.cookies()).get();


                // Todo: get title or first post
                Element storyContianer = doc.select("div[data-ad-preview='message']").get(0);
                String text = storyContianer.text();

                // Todo:  Get like of first post
                Element storyFooter =  doc.selectFirst("footer");
                String storyLikeCount =  storyFooter.select("div[data-sigil='reactions-sentence-container']")
                                     .first().select("div").last().text();

//                // Todo : Get commnet count of first post
                  Element commentEle =  storyFooter.select("span[data-sigil='comments-token']").first();
                  String storyCommentCount = commentEle.text();
                  String storyShareCount = commentEle.nextElementSibling().text();


                Log.i("get  web" , "get web");
                //get story

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


