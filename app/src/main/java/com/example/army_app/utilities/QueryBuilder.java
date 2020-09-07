package com.example.army_app.utilities;

import com.example.army_app.MainActivity;
import com.example.army_app.SeriesDetailsActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class QueryBuilder {

        private static String query;
        public QueryBuilder(){
            this.query = "";
        }


        public QueryBuilder EncodePermissions(String userName, String password) throws UnsupportedEncodingException {
            query+= URLEncoder.encode(NetworkUtils.USERNAME ,"UTF-8")+"="+ URLEncoder.encode(userName,"UTF-8")+
                    "&&"+ URLEncoder.encode(NetworkUtils.PASSWORD ,"UTF-8")+"="+ URLEncoder.encode(password,"UTF-8");
            return this;
        }
        /*
        public QueryBuilder EncodeNumberOfLines(String lines) throws UnsupportedEncodingException {
            query+="&&"+ URLEncoder.encode(NetworkUtils.NUMBER_OF_LINES ,"UTF-8")+"="+ URLEncoder.encode(lines,"UTF-8");
            return this;
        }
         */

        /*
        public QueryBuilder EncodeTypeForSpecificTable(String type) throws UnsupportedEncodingException {
            query+="&&"+ URLEncoder.encode(NetworkUtils.TYPE ,"UTF-8")+"="+ URLEncoder.encode(type,"UTF-8");
            return this;
        }

         */
        public QueryBuilder EncodeId(String id) throws UnsupportedEncodingException {
            query +="&&" + URLEncoder.encode(NetworkUtils.ID , "UTF-8")+"="+ URLEncoder.encode(String.valueOf(id) , "UTF-8");
            return this;
        }
        public QueryBuilder EncodeName(String name) throws UnsupportedEncodingException {
            query +="&&" + URLEncoder.encode(MainActivity.NAME , "UTF-8")+"="+ URLEncoder.encode(String.valueOf(name) , "UTF-8");
            return this;
        }
        public QueryBuilder EncodeIdTwo(String id) throws UnsupportedEncodingException {
            query +="&&" + URLEncoder.encode(SeriesDetailsActivity.IDTWO, "UTF-8")+"="+ URLEncoder.encode(String.valueOf(id) , "UTF-8");
            return this;
        }
        public QueryBuilder EncodeIdThree(String id) throws UnsupportedEncodingException {
            query +="&&" + URLEncoder.encode(SeriesDetailsActivity.IDTHREE, "UTF-8")+"="+ URLEncoder.encode(String.valueOf(id) , "UTF-8");
             return this;
        }
        public QueryBuilder EncodeIdFour(String id) throws UnsupportedEncodingException {
            query +="&&" + URLEncoder.encode(SeriesDetailsActivity.IDFOUR, "UTF-8")+"="+ URLEncoder.encode(String.valueOf(id) , "UTF-8");
            return this;
         }
        public QueryBuilder EncodeCat(String cat) throws UnsupportedEncodingException {
            query +="&&" + URLEncoder.encode(MainActivity.CAT , "UTF-8")+"="+ URLEncoder.encode(cat , "UTF-8");
            return this;
        }
         public QueryBuilder EncodeNameTow(String name) throws UnsupportedEncodingException {
            query +="&&" + URLEncoder.encode(MainActivity.NAMETOW , "UTF-8")+"="+ URLEncoder.encode(name , "UTF-8");
            return this;
         }
         public QueryBuilder EncodeUrl(String url) throws UnsupportedEncodingException {
             query +="&&" + URLEncoder.encode(MainActivity.URL , "UTF-8")+"="+ URLEncoder.encode(url , "UTF-8");
             return this;
         }
        public QueryBuilder EncodeValue(String value) throws UnsupportedEncodingException {
            query +="&&" + URLEncoder.encode(MainActivity.VALUE , "UTF-8")+"="+ URLEncoder.encode(value , "UTF-8");
             return this;
        }
        public String getQueryString(){
            return query;
        }

}
