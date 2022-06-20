package com.codegraphy;
import java.util.ArrayList;
import java.util.List;

//Holds stroke data  (x and y cordinates) for each stroke
public class Stroke {


    private List<Integer> xValues = new ArrayList<>();
    private List<Integer> yValues = new ArrayList<>();

    public List<Integer> getxValues() {
        return xValues;
    }

    public void setxValues(List<Integer> xValues) {
        this.xValues = xValues;
    }

    public List<Integer> getyValues() {
        return yValues;
    }

    public void setyValues(List<Integer> yValues) {
        this.yValues = yValues;
    }

}
