package com.example.pierreleveille.cvm_man;
import android.util.Log;
import android.view.ViewDebug.IntToString;

import java.util.Random;
import java.util.Vector;
public class Blinky extends Fantome {






    public Blinky( Labyrinthe jeu)
    {

        super ( 16, 12, 'e', jeu );

    }

    public void avance(CVMMAN cvmman) {
        Log.i("posi","("+getPosX()+", "+getPosY()+")");
        Log.i("nextposi", String.valueOf(getLaby().getCellule(getPosX()-1, getPosY())));
        Log.i("nextPo","("+String.valueOf(getPosX()-1)+", "+getPosY()+")");

        if ( getDirection() == 's')
            sud(cvmman);
        else if ( getDirection() == 'n')
            nord(cvmman);
        else if ( getDirection() =='o')
            ouest(cvmman);
        else
            est(cvmman);
    }

    public void sud(CVMMAN cvmman)
    {
        //direction sud
        Log.i("move","s");

        if ( getLaby().getCellule(getPosX(), getPosY()+1)=='x' || getLaby().getCellule(getPosX(), getPosY()+1)=='p'){ // il entre dans un mur, on doit absolument changer de direction
            deciderDirectionObligatoireOrienteCVMMan(cvmman);
        }

        else if ((getLaby().getCellule(getPosX(),getPosY()+1)!='x') && ( getLaby().getCellule(getPosX()-1,getPosY()+1) !='x' || getLaby().getCellule(getPosX()+1, getPosY()+1) != 'x' ) ) // il est à une intersection, tente de suivre le CVM-Man
        {
            deciderDirectionObligatoireOrienteCVMMan(cvmman);
            setY (getPosY() +1);
        }
        else  {  // pas d'intersection, il poursuit son chemin dans sa direction
            setY (getPosY()+1);
        }
    }

    public void nord(CVMMAN cvmman)
    {
        Log.i("move","n");
        if ( getLaby().getCellule(getPosX(), getPosY()-1)=='x' || getLaby().getCellule(getPosX(), getPosY()-1)=='p') { // il entre dans un mur, on doit absolument changer de direction
            deciderDirectionObligatoireOrienteCVMMan(cvmman);
        }

        else if ((getLaby().getCellule(getPosX(),getPosY()-1)!='x') && ( getLaby().getCellule(getPosX()-1,getPosY()-1) !='x' || getLaby().getCellule(getPosX()+1, getPosY()-1) != 'x' ) ) // il est à une intersection, tente de suivre le CVM-Man
        {
            deciderDirectionObligatoireOrienteCVMMan(cvmman);
            setY (getPosY() -1);
        }
        else  {  // pas d'intersection, il poursuit son chemin dans sa direction
            setY (getPosY()-1);
        }
    }

    public void est(CVMMAN cvmman)
    {
        Log.i("move","e");
        //direction est
        if ( getLaby().getCellule(getPosX()-1, getPosY())=='x'|| getLaby().getCellule(getPosX()-1, getPosY())=='p') { // il entre dans un mur, on doit absolument changer de direction
            deciderDirectionObligatoireOrienteCVMMan(cvmman);
            Log.i("condition","1");

        }

        else if ((getLaby().getCellule(getPosX()-1,getPosY())!='x') && ( getLaby().getCellule(getPosX()-1,getPosY()-1) !='x' || getLaby().getCellule(getPosX()+1, getPosY()-1) != 'x' ) ) // il est à une intersection, tente de suivre le CVM-Man
        {
            deciderDirectionObligatoireOrienteCVMMan(cvmman);
            setX(getPosX() -1);
            Log.i("condition","2");

        }
        else  {
            Log.i("condition","3");
            // pas d'intersection, il poursuit son chemin dans sa direction
            if ( getPosX() == 26 && getPosX() == 14 )
                setX( 0);
            else
                setX (getPosX()-1);
        }
    }

    public void ouest(CVMMAN cvmman)
    {
        Log.i("move","o");
        if ( getLaby().getCellule(getPosX()+1, getPosY())=='x'|| getLaby().getCellule(getPosX()+1, getPosY())=='p') { // il entre dans un mur, on doit absolument changer de direction
            deciderDirectionObligatoireOrienteCVMMan(cvmman);
        }

        else if ((getLaby().getCellule(getPosX()+1,getPosY())!='x') && ( getLaby().getCellule(getPosX()+1,getPosY()-1) !='x' || getLaby().getCellule(getPosX()+1, getPosY()+1) != 'x' ) ) // il est à une intersection, tente de suivre le CVM-Man
        {

            deciderDirectionObligatoireOrienteCVMMan(cvmman);
            setX (getPosX() -1);
        }
        else  {  // pas d'intersection, il poursuit son chemin dans sa direction
            if ( getPosY() == 1 && getPosX() == 14 ) // passage secret
                setX( 26);
            else

                setX (getPosY()-1);
        }
    }

    public void deciderDirectionObligatoireOrienteCVMMan(CVMMAN cvmman) {
        // 4 directions possibles

        Vector<Character> dirDispo;

        if ( getDirection() == 's')
        {
            dirDispo = directionsDispo(getPosX()+1, getPosY());
            // est ou ouest ou nord dépend du cvmman, il ne peut pas rebrousser chemin / reculer
            dirDispo.remove(new Character('n'));


        }
        else if ( getDirection() =='n')
        {
            dirDispo = directionsDispo(getPosX()-1, getPosY());
            dirDispo.remove(new Character('s'));
        }
        else if ( getDirection() =='e')
        {

            dirDispo = directionsDispo(getPosX(), getPosY()+1);

            dirDispo.remove(new Character('o'));


        }
        else //si c'est ouest
        {

            dirDispo = directionsDispo(getPosX(), getPosY()-1);
            dirDispo.remove(new Character('e')); //ne pas pouvoir reculer et entrer en transe

        }

        Vector<Character> cibles = determinerDirectionCible( cvmman.getLigne(), cvmman.getCol());
        Vector<Character> intersect = (Vector<Character>) dirDispo.clone(); //une copie du Vecteur
        intersect.retainAll(cibles); //on fait une intersection pour garder les meilleures positions
        //intersection
        if ( intersect.size()== 0)
        {
            int indiceChoisi = (int)(Math.random()*(dirDispo.size()-1));

            setDirection( dirDispo.get(indiceChoisi));
        }
        else
        {
            int indiceChoisi = (int)(Math.random()*(intersect.size()-1));

            setDirection( intersect.get(indiceChoisi));
        }
    }





}





