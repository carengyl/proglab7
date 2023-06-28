package common.commands;

import common.entities.HumanBeing;

import java.io.Serializable;
import java.util.ArrayList;

public class CommandArgument implements Serializable {
    private final int numberOfArgs;
    private int currentArgNumber;
    private String arg;
    private String[] args;
    private int enumNumber;
    private double doubleArg;
    private long longArg;
    private boolean booleanArg;
    private String fileName;
    private HumanBeing humanBeingArgument;
    private ArrayList<String> elementArgument;
    public CommandArgument(String[] args) {
        this.numberOfArgs = args.length;
        currentArgNumber = 0;
        if (numberOfArgs > 1) {
            this.args = args;
            arg = args[0];
        } else if (numberOfArgs == 1) {
            arg = args[0];
        }
    }

    public String getArg() {
        if (numberOfArgs > 1 && currentArgNumber < args.length - 1) {
            String prevArg = arg;
            arg = args[currentArgNumber++];
            return prevArg;
        }
        return arg;
    }

    public void setEnumNumber(int enumNumber) {
        this.enumNumber = enumNumber;
    }

    public int getEnumNumber() {
        return enumNumber;
    }

    public long getLongArg() {
        return longArg;
    }

    public void setLongArg(long longArg) {
        this.longArg = longArg;
    }

    public int getNumberOfArgs() {
        return numberOfArgs;
    }

    public double getDoubleArg() {
        return doubleArg;
    }

    public void setDoubleArg(double doubleArg) {
        this.doubleArg = doubleArg;
    }

    public boolean isBooleanArg() {
        return booleanArg;
    }

    public void setBooleanArg(boolean booleanArg) {
        this.booleanArg = booleanArg;
    }

    public HumanBeing getHumanBeingArgument() {
        return humanBeingArgument;
    }

    public void setHumanBeingArgument(HumanBeing humanBeingArgument) {
        this.humanBeingArgument = humanBeingArgument;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<String> getElementArgument() {
        return elementArgument;
    }

    public void setElementArgument(ArrayList<String> elementArgument) {
        this.elementArgument = elementArgument;
    }
}
