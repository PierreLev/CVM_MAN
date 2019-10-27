package com.example.pierreleveille.cvm_man;

import java.util.Vector;

public class Pinky extends Fantome{

    public Pinky(Labyrinthe laby)
        {

            super ( 15, 14, 'e', laby );
        }

        public void avance (CVMMAN c) {

            if ( getDirection() == 's')
                sud(c);
            else if ( getDirection() == 'n')
                nord(c);
            else if ( getDirection() =='o')
                ouest(c);
            else
                est(c);
        }
        public void sud(CVMMAN cvmman)
        {
            //direction sud

            if ( getLaby().getCellule(getPosY()+1, getPosX())=='x') { // il entre dans un mur, on doit absolument changer de direction
                deciderDirectionObligatoireOrienteCVMMan(cvmman);
            }

            else if ((getLaby().getCellule(getPosY()+1,getPosX())!='x') && ( getLaby().getCellule(getPosY()+1,getPosX()-1) !='x' || getLaby().getCellule(getPosY()+1, getPosX()+1) != 'x' ) ) // il est à une intersection, tente de suivre le CVM-Man
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

            if ( getLaby().getCellule(getPosY()-1, getPosX())=='x') { // il entre dans un mur, on doit absolument changer de direction
                deciderDirectionObligatoireOrienteCVMMan(cvmman);
            }

            else if ((getLaby().getCellule(getPosY()-1,getPosX())!='x') && ( getLaby().getCellule(getPosY()-1,getPosX()-1) !='x' || getLaby().getCellule(getPosY()-1, getPosX()+1) != 'x' ) ) // il est à une intersection, tente de suivre le CVM-Man
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
            //direction est
            if ( getLaby().getCellule(getPosY(), getPosX()+1)=='x') { // il entre dans un mur, on doit absolument changer de direction
                deciderDirectionObligatoireOrienteCVMMan(cvmman);
            }

            else if ((getLaby().getCellule(getPosY(),getPosX()+1)!='x') && ( getLaby().getCellule(getPosY()-1,getPosX()+1) !='x' || getLaby().getCellule(getPosY()+1, getPosX()+1) != 'x' ) ) // il est à une intersection, tente de suivre le CVM-Man
            {
                deciderDirectionObligatoireOrienteCVMMan(cvmman);
                setX (getPosX() +1);
            }
            else  {  // pas d'intersection, il poursuit son chemin dans sa direction
                if ( getPosX() == 26 && getPosY() == 14 )
                    setX( 0);
                else
                    setX (getPosX()+1);
            }
        }

        public void ouest(CVMMAN cvmman)
        {
            if ( getLaby().getCellule(getPosY(), getPosX()-1)=='x') { // il entre dans un mur, on doit absolument changer de direction
                deciderDirectionObligatoireOrienteCVMMan(cvmman);
            }

            else if ((getLaby().getCellule(getPosY(),getPosX()-1)!='x') && ( getLaby().getCellule(getPosY()+1,getPosX()-1) !='x' || getLaby().getCellule(getPosY()-1, getPosX()-1) != 'x' ) ) // il est à une intersection, tente de suivre le CVM-Man
            {

                deciderDirectionObligatoireOrienteCVMMan(cvmman);
                setX (getPosX() -1);
            }
            else  {  // pas d'intersection, il poursuit son chemin dans sa direction
                if ( getPosX() == 1 && getPosY() == 14 ) // passage secret
                    setX( 26);
                else

                    setX (getPosX()-1);
            }
        }

        public void deciderDirectionObligatoireOrienteCVMMan(CVMMAN cvmman) { // n'a pas le choix de changer de direction, entre dans un mur
            // 4 directions possibles

            Vector<Character> dirDispo;
            Vector<Character> cibles;
            if ( getDirection() == 's')
            {

                dirDispo = directionsDispo(getPosY()+1, getPosX());
                // est ou ouest ou nord dépend du cvmman, il ne peut plus aller vers le sud
                dirDispo.remove(new Character('n'));
                cibles = determinerDirectionCible( cvmman.getLigne()+4, cvmman.getCol());



            }
            else if ( getDirection() =='n')
            {
                dirDispo = directionsDispo(getPosY()-1, getPosX());
                dirDispo.remove(new Character('s'));
                cibles = determinerDirectionCible( cvmman.getLigne()-4, cvmman.getCol()-4);
            }
            else if ( getDirection() =='e')
            {

                dirDispo = directionsDispo(getPosY(), getPosX()+1);
                // est ou ouest ou nord dépend du cvmman, il ne peut plus aller vers le sud
                dirDispo.remove(new Character('o'));
                cibles = determinerDirectionCible( cvmman.getLigne(), cvmman.getCol()+4);
                //if ( cibles.size() == 0)  // le cvmman est face à nous
                //je ne peux pas revenir sur mon chemin, ca serait reculer et m eloigne de cvmman

            }
            else //si c'est ouest
            {

                dirDispo = directionsDispo(getPosY(), getPosX()-1);
                dirDispo.remove(new Character('e')); //ne pas pouvoir reculer et entrer en transe
                cibles = determinerDirectionCible( cvmman.getLigne(), cvmman.getCol()-4);


            }


            Vector<Character> intersect = (Vector<Character>) dirDispo.clone();
            intersect.retainAll(cibles);
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

