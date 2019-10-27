package com.example.pierreleveille.cvm_man;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 1689032 on 2018-04-24.
 */

public class SurfaceDessin extends View{
    private Paint p;
    Path path;
    private Labyrinthe labySurface;
    private CVMMAN cvmmanSurface;
    private Blinky blinky;
    private Pinky pinky;
    private Clyde clyde;
    private Inky inky;
    private char[][] maquetteSurface;
    private int posC, posL;
    private char dirC;
    private Drawable d,bl,cl,pi, in;



    public SurfaceDessin(Context context, AttributeSet attr) {
        super(context, attr);
        p = new Paint();

    }

    public void setLaby(Labyrinthe laby)
    {
        labySurface=laby;
        maquetteSurface=laby.getTab();
    }

    public void setMaquette(char[][] cTab){
        maquetteSurface=cTab;
    }

    public void setCVMMAN(CVMMAN c)
    {
        cvmmanSurface=c;
        posC=c.getCol();
        posL=c.getLigne();
        dirC=c.getDir();
    }

    public void setBlinky(Blinky b)
    {
        blinky=b;
    }

    public void setPinky(Pinky b)
    {
        pinky=b;
    }

    public void setClyde(Clyde b)
    {
        clyde=b;
    }

    public void setInky(Inky b)
    {
        inky=b;
    }




    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        int ligne=1, col=1, tampon=6;

        float top=tampon*ligne+canvas.getHeight()*(ligne-1), bottom=(canvas.getHeight()/31+10)*ligne,left=tampon*col+canvas.getWidth()*(col-1), right=canvas.getWidth()/28+10;

        //Déterminer la largeur des cellules
        float larg=canvas.getWidth()/28-tampon;
        float haut=canvas.getHeight()/31-tampon;


        //Déterminer la hauteur des cellules


        p.setColor(Color.BLUE);
        for(int i=0;i<31;i++) {
            col=1;
            top = (ligne - 1) * haut + tampon * ligne;
            bottom = (ligne) * haut + tampon * ligne;

            for (int j = 0; j < 28; j++) {

                left = (col - 1) * larg + tampon * col;
                right = (col) * larg + tampon * col;

                //Murs
                if (maquetteSurface[i][j] == 'x') {
                    p.setColor(Color.BLUE);
                    canvas.drawRect(left, top, right, bottom, p);
                }
                //Portes des fantômes
                else if(maquetteSurface[i][j] == 'p')
                {
                    p.setColor(Color.RED);
                    canvas.drawRect(left, top, right, bottom, p);
                }
                //Gommes petites
                else if(maquetteSurface[i][j]=='o'){
                    p.setColor(Color.YELLOW);
                    canvas.drawCircle(left+(larg/2),top+(haut/2),larg/4,p);
                }
                else if(maquetteSurface[i][j]=='0') {
                    p.setColor(Color.CYAN);
                    canvas.drawCircle(left+(larg/2),top+(haut/2),larg/2,p);
                }
                    //Gommes grosses

                col++;
            }
            ligne++;
        }

        //Fantôme
        bl= getResources().getDrawable(R.drawable.blinky);
        pi= getResources().getDrawable(R.drawable.pinky);
        cl= getResources().getDrawable(R.drawable.clyde);
        in=getResources().getDrawable(R.drawable.inky);

        //////////
        col=blinky.getPosX();
        ligne=blinky.getPosY();
        top = (ligne - 1) * haut + tampon * ligne;
        bottom = (ligne) * haut + tampon * ligne;
        left = (col - 1) * larg + tampon * col;
        right = (col) * larg + tampon * col;

        bl.setBounds((int)left,(int)top,(int)right,(int)bottom);
        bl.draw(canvas);
        //////////
        col=pinky.getPosX();
        ligne=pinky.getPosY();
        top = (ligne - 1) * haut + tampon * ligne;
        bottom = (ligne) * haut + tampon * ligne;
        left = (col - 1) * larg + tampon * col;
        right = (col) * larg + tampon * col;

        pi.setBounds((int)left,(int)top,(int)right,(int)bottom);
        pi.draw(canvas);
        ///////////
        col=inky.getPosX();
        ligne=inky.getPosY();
        top = (ligne - 1) * haut + tampon * ligne;
        bottom = (ligne) * haut + tampon * ligne;
        left = (col - 1) * larg + tampon * col;
        right = (col) * larg + tampon * col;

        in.setBounds((int)left,(int)top,(int)right,(int)bottom);
        in.draw(canvas);
        ///////////
        col=clyde.getPosX();
        ligne=clyde.getPosY();
        top = (ligne - 1) * haut + tampon * ligne;
        bottom = (ligne) * haut + tampon * ligne;
        left = (col - 1) * larg + tampon * col;
        right = (col) * larg + tampon * col;

        cl.setBounds((int)left,(int)top,(int)right,(int)bottom);
        cl.draw(canvas);


        //Prendre l'image du CVMMAN correspondant à sa direction
        if(cvmmanSurface.getDir()=='e')
            d= getResources().getDrawable(R.drawable.est);
        else if(cvmmanSurface.getDir()=='o')
            d= getResources().getDrawable(R.drawable.ouest);
        else if(cvmmanSurface.getDir()=='n')
            d= getResources().getDrawable(R.drawable.nord);
        else if(cvmmanSurface.getDir()=='s')
            d= getResources().getDrawable(R.drawable.sud);

        //Prend la position du CVM
        col=posC;
        ligne=posL;

        top = (ligne - 1) * haut + tampon * ligne;
        bottom = (ligne) * haut + tampon * ligne;
        left = (col - 1) * larg + tampon * col;
        right = (col) * larg + tampon * col;

        d.setBounds((int)left,(int)top,(int)right,(int)bottom);
        p.setColor(Color.RED);
        d.draw(canvas);
    }

    public void getCVMMAN (CVMMAN cm)
    {

    }
}
