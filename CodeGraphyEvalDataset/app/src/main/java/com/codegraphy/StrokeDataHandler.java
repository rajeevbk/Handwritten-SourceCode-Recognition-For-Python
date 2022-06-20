package com.codegraphy;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class StrokeDataHandler {

   public static LinkedHashMap allStrokesForEachAuthor = new LinkedHashMap<String, LinkedHashMap<String, List<Stroke>>>();

   public static LinkedHashMap<String, LinkedHashMap<String, List<Stroke>>> getStrokeData(JSONArray jsonArray) throws JSONException {

      LinkedHashMap allStrokesForEachAuthor = new LinkedHashMap<String, LinkedHashMap<String, List<Stroke>>>();
      for(int i = 0; i < jsonArray.length(); i++) {

         JSONObject currentItem =  jsonArray.getJSONObject(i); // this is Aman or any author with all his records
         int size = currentItem.length();
         //Log.i("parameters", " size = ----------" + size);

         LinkedHashMap<String, List<Stroke>> strokeMap = new LinkedHashMap<>();
         for(int k=0;k<size-1;k++){
            JSONArray xyparent =  (currentItem.getJSONArray(String.valueOf(k)));

            List strokeListForEachRecord = new ArrayList<Stroke>();
            for(int j=0;j<xyparent.length();j++){
               Stroke stroke = new Stroke();
               JSONArray xArray = xyparent.getJSONObject(j).getJSONArray("x");
               JSONArray yArray = xyparent.getJSONObject(j).getJSONArray("y");

               for(int l = 0; l < xArray.length(); l++){
                  stroke.getxValues().add(Integer.valueOf(xArray.getInt(l)));
                  stroke.getyValues().add(Integer.valueOf(yArray.getInt(l)));
               }
               strokeListForEachRecord.add(stroke);
            }
            strokeMap.put( String.valueOf(k), strokeListForEachRecord);
         }

         allStrokesForEachAuthor.put(currentItem.get("key"), strokeMap);
      }

      System.out.println(allStrokesForEachAuthor.size());
      return allStrokesForEachAuthor;
   }

}
