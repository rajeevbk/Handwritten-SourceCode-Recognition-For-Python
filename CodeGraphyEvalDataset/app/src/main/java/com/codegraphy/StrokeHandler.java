package com.codegraphy;

import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.VisibleForTesting;

import com.google.mlkit.vision.digitalink.DigitalInkRecognizer;
import com.google.mlkit.vision.digitalink.Ink;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class StrokeHandler {


    public String writer = "";
    //refference to the text view
    private EditTextView editTextView;

    @VisibleForTesting
    private static final String TAG = "parameters";

    // Managing ink currently drawn.
    private Ink.Stroke.Builder strokeBuilder = Ink.Stroke.builder();
    private Ink.Builder inkBuilder = Ink.builder();

    ModelManager modelManager = new ModelManager();

    public void setEditTextView(EditTextView editTextView) {
        this.editTextView = editTextView;
    }

    public Ink getInkObject() {
        return inkBuilder.build();
    }

    public void recognize() {

        //get strokes
        LinkedHashMap allStrokesForEachAuthor = StrokeDataHandler.allStrokesForEachAuthor; // the set of strokes for each user
        //get recognizer
        DigitalInkRecognizer recognizer = modelManager.getRecognizer();
        LinkedHashMap<String, List<Stroke>> eachUserData;
        eachUserData = (LinkedHashMap<String, List<Stroke>>) allStrokesForEachAuthor.get(writer);

        for(LinkedHashMap.Entry<String,List<Stroke>> entry : eachUserData.entrySet()) {
           String key = entry.getKey();
           List<Stroke> strokeList = entry.getValue();
           //Log.i(TAG, "key = " + key +  "values = " + strokeList);
           for(int i = 0; i < strokeList.size(); i++){
               Stroke currentStroke = strokeList.get(i);
               List<Integer> xValues = currentStroke.getxValues();
               List<Integer> yValues = currentStroke.getyValues();

               strokeBuilder = Ink.Stroke.builder();
            for (int j = 0; j < xValues.size(); j++) {
               strokeBuilder.addPoint(Ink.Point.create(xValues.get(j), yValues.get(j)));
            }
            inkBuilder.addStroke(strokeBuilder.build()); //build and add the stroke
           }
            Ink ink = inkBuilder.build();
            //Log.i(TAG, ink.toString());
            recognizer.recognize(ink)
                    .addOnSuccessListener(
                            // `result` contains the recognizer's answers as a RecognitionResult.
                            result -> {
                                String recognitionResult = result.getCandidates().get(0).getText();
                                //Log.i(TAG, "Recognition result  = " + recognitionResult);
                                editTextView.append(recognitionResult +" " ); //add "\n" if need new line

                            })
                    .addOnFailureListener(e -> Log.e(TAG, "Error during recognition: " + e));

            reset();
       }

    }

    public void reset() {
        inkBuilder = Ink.builder();
        strokeBuilder = Ink.Stroke.builder();
    }

}
