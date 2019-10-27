package com.example.pierreleveille.cvm_man;

public class CVMMAN {

    private int posCol, posLigne;
    private char direction;
    /**
     * Create the panel.
     */
    public CVMMAN() {
        posCol=14;
        posLigne=18;
        direction='e';
    }

    public void setPosCol(int c)
    {
        posCol=c;
    }
    public void setPosLigne(int l)
    {
        posLigne=l;
    }
    public void setDirection(char d)
    {
        direction=d;
    }
    public int getCol()
    {
        return posCol;
    }
    public int getLigne()
    {
        return posLigne;
    }
    public char getDir()
    {
        return direction;
    }



}
