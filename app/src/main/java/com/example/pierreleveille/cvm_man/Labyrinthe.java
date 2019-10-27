package com.example.pierreleveille.cvm_man;

public class Labyrinthe {
    private char[][] maquette;

    public Labyrinthe(){
    }

    public Labyrinthe(char [][] a){
    maquette = a;
    }

    public char[][] getTab(){
        return maquette;
    }

    public char getCellule(int x, int y)
    {
        char a;
        a=maquette[x][y];
        return a;
    }
}
