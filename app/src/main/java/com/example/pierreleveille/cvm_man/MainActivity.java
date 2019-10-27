package com.example.pierreleveille.cvm_man;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class MainActivity extends AppCompatActivity {
    Button estB, ouestB, nordB, sudB, startB;
    TextView nbpoints, nbvies;
    SurfaceDessin surface;
    String[] tabGrille;
    char[][] laby;
    Labyrinthe labyrinthe;
    CVMMAN c;
    Blinky b;
    Pinky p;
    Clyde cl;
    Inky i;
    int points=0,vies=5, mode=0, running=0;
    char dir='e';
    Handler handler;
    Thread thread;
    boolean collision;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        estB = (Button) findViewById(R.id.estB);
        ouestB = (Button) findViewById(R.id.ouestB);
        nordB = (Button) findViewById(R.id.nordB);
        sudB = (Button) findViewById(R.id.sudB);
        startB = (Button) findViewById(R.id.startB);

        nbpoints = (TextView) findViewById(R.id.nbpoints);
        nbvies = (TextView) findViewById(R.id.nbvies);
        surface = (SurfaceDessin) findViewById(R.id.surface);
        surface.setWillNotDraw(false);

        Ecouteur ec = new Ecouteur();
        estB.setOnClickListener(ec);
        ouestB.setOnClickListener(ec);
        nordB.setOnClickListener(ec);
        sudB.setOnClickListener(ec);
        startB.setOnClickListener(ec);

        collision=false;

        tabGrille = new String[35];
        laby = new char[31][28];

        lecture();
        afficherConsole();

        labyrinthe = new Labyrinthe(laby);
        c=new CVMMAN();
        b=new Blinky(labyrinthe);
        cl=new Clyde(labyrinthe);
        i=new Inky(labyrinthe);
        p=new Pinky(labyrinthe);

        surface.setLaby(labyrinthe);
        surface.setCVMMAN(c);
        surface.setBlinky(b);
        surface.setPinky(p);
        surface.setInky(i);
        surface.setClyde(cl);

        thread = new Thread(){
                public void run()
                {
                    boucleJeu();
                }
            };

        handler= new Handler();
        thread.run();
        handler.post(thread);
        handler.removeCallbacks(thread);


        Log.d("largeur surface: ",String.valueOf(surface.getWidth()));
        Log.d("hauteur surface: ",String.valueOf(surface.getHeight()));


    }
    public void boucleJeu()
    {
        // À chaque intervalle de 350 milliseconde on met à jour la surface de dessin et le jeu
        bouge(dir);
        b.avance(c);
        cl.avance(c);
        i.avance(c);
        p.avance(c);
        surface.setBlinky(b);
        surface.setClyde(cl);
        surface.setInky(i);
        surface.setPinky(p);
        surface.setCVMMAN(c);

        collision();
        if(collision==true)
            cvmmanMort();

        nbpoints.setText(String.valueOf(points));
        nbvies.setText(String.valueOf(vies));

        surface.invalidate();

        handler.postDelayed(thread,350);
    }
    public void collision(){

        if (c.getLigne()==b.getPosY() &&c.getCol()==b.getPosX()){
            collision=true;
        }
        else if (c.getLigne()==cl.getPosY() &&c.getCol()==cl.getPosX()){
            collision=true;
        }
        else if (c.getLigne()==i.getPosY() &&c.getCol()==i.getPosX()){
            collision=true;
        }
        else if (c.getLigne()==p.getPosY() &&c.getCol()==p.getPosX()){
            collision=true;
        }

    }

    public void cvmmanMort(){
        collision=false;
        c.setPosLigne(14);
        c.setPosCol(18);
        c.setDirection('e');
        vies--;
    }

    public void bouge(char dir){
        int ligne=c.getLigne()-1;
        int col=c.getCol()-1;
        if(dir=='n' && laby[ligne-1][col]!='x'){
            c.setPosLigne(c.getLigne()-1);
            gomme(c.getLigne()-1, c.getCol()-1);

        }
        else if(dir=='e' && laby[ligne][col-1]!='x'){
            c.setPosCol(c.getCol()-1);
            gomme(c.getLigne()-1, c.getCol()-1);

        }
        else if(dir=='s' && laby[ligne+1][col]!='x'){
            c.setPosLigne(c.getLigne()+1);
            gomme(c.getLigne()-1, c.getCol()-1);

        }
        else if(dir=='o' && laby[ligne][col+1]!='x'){
            c.setPosCol(c.getCol()+1);
            gomme(c.getLigne()-1, c.getCol()-1);
        }
    }

    public void gomme(int ligne, int col)
    {
        if(laby[ligne][col]=='o'){
            points++;
            laby[ligne][col]=' ';
            surface.setMaquette(laby);
        }
        else if(laby[ligne][col]=='0'){
            mode=1;
            laby[ligne][col]=' ';
            surface.setMaquette(laby);
        }

    }

    public void afficherConsole()
    {
        //System.out.println();
        for(int i=0;i<31;i++){
            Log.d("tableau char : ",tabGrille[i]);
        }
        Log.d("test d'affichage : ", "Salut!");
        for(int i=0;i<31;i++){
            for(int j=0;j<28;j++) {
                //Log.d("laby : ", String.valueOf(laby[i][j]));
            }
        }


    }
    private class Ecouteur implements View.OnClickListener {

        public void onClick(View v) {
            if(v==estB)
            {
                c.setDirection('e');
                dir='e';
                bouge(dir);
                surface.setCVMMAN(c);
                nbpoints.setText(String.valueOf(points));
                //surface.postInvalidate();
            }
            else if(v==ouestB)
            {
                c.setDirection('o');
                dir='o';
                bouge(dir);
                surface.setCVMMAN(c);
                nbpoints.setText(String.valueOf(points));
                //surface.postInvalidate();

            }
            else if(v==nordB)
            {
                c.setDirection('n');
                dir='n';
                bouge(dir);
                surface.setCVMMAN(c);
                nbpoints.setText(String.valueOf(points));
                //surface.postInvalidate();

            }
            else if(v==sudB)
            {
                c.setDirection('s');
                dir='s';
                bouge(dir);
                surface.setCVMMAN(c);
                nbpoints.setText(String.valueOf(points));
                //surface.postInvalidate();


            }
            else if(v==startB){
                if(running==0)
                {
                    handler.post(thread);

                    //mettreAJour();
                    running=1;
                }
                else if(running==1)
                {
                    handler.removeCallbacks(thread);
                    running=0;
                }
            }

        }
    }

    public static void fermerFlux(Reader r) {
        try {
            r.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void mettreAJour(){
    //vies--;
    //nbvies.setText(String.valueOf(vies));
    }

    public void lecture() {


        //FileInputStream inputStream;
        ////
        InputStream i_s_fichierExistant;
        InputStreamReader readerFichExistant;
        BufferedReader readerCharExistant;

        ////
        //InputStreamReader reader;

        //BufferedReader readerChar;

        //j’ai omis les try / catch

        try {
            i_s_fichierExistant = getResources().openRawResource(R.raw.fichier_grille);
            readerFichExistant = new InputStreamReader ( i_s_fichierExistant);
            readerCharExistant = new BufferedReader ( readerFichExistant);
            int ligne=0, col=0;
            String nlleligne = readerCharExistant.readLine();
            tabGrille[ligne]=nlleligne;

            char[] a= new char[31];
            a=nlleligne.toCharArray();
            for(col=0;col<28;col++)
            {
                laby[ligne][col]=a[col];
            }
            ligne++;

            nlleligne = readerCharExistant.readLine();


            while ( nlleligne != null ) {
                //tableau de strings
                tabGrille[ligne]=nlleligne;
                a=nlleligne.toCharArray();
                for(col=0;col<28;col++)
                {
                    laby[ligne][col]=a[col];
                }
                ligne++;
                nlleligne = readerCharExistant.readLine();


            }
            readerCharExistant.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





