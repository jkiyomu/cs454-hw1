package com.jikiyomura.hw1;

import java.io.Serializable;

/**
 * Created by Jeffrey on 5/3/2015.
 */
public class Equations implements Serializable {
    private double first;
    private String operands = "empty";
    private double second;

    public Equations(){
        this.first = 0.0;
        this.second = 0.0;
        this.operands = "empty";
    }

    public Equations(double first, String operands){
        this.first = first;
        this.operands = operands;
    }

    public String getOperands(){
        return this.operands;
    }

    public double getFirst() {
        return first;
    }

    public void setFirst(double first) {
        this.first = first;
    }

    public void setOperands(String operands) {
        this.operands = operands;
    }

    public double getSecond() {
        return second;
    }

    public void setSecond(double second) {
        this.second = second;
    }

}
