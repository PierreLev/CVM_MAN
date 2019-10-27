package com.example.pierreleveille.cvm_man;

import java.util.Vector;

public class Clyde extends Fantome{


        public Clyde( Labyrinthe jeu)
        {

            super ( 16, 12, 'e', jeu );

        }

        public void avance (CVMMAN cvmman) {

            if ( cvmman.getDir() == 's')
                sud(cvmman);
            else if ( cvmman.getDir() == 'n')
                nord(cvmman);
            else if ( cvmman.getDir() =='o')
                ouest(cvmman);
            else
                est(cvmman);
        }

        public void sud(CVMMAN cvmman)
        {
            //direction sud

            if ( getLaby().getCellule(getPosY()+1, getPosX())=='x' || getLaby().getCellule(getPosY()+1, getPosX())=='p'){ // il entre dans un mur, on doit absolument changer de direction
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

            if ( getLaby().getCellule(getPosY()-1, getPosX())=='x' || getLaby().getCellule(getPosY()-1, getPosX())=='p') { // il entre dans un mur, on doit absolument changer de direction
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
            if ( getLaby().getCellule(getPosY(), getPosX()+1)=='x'|| getLaby().getCellule(getPosY(), getPosX()+1)=='p') { // il entre dans un mur, on doit absolument changer de direction
                deciderDirectionObligatoireOrienteCVMMan(cvmman);
            }

            else if ((getLaby().getCellule(getPosY(),getPosX()+1)!='x') && ( getLaby().getCellule(getPosY()-1,getPosX()+1) !='x' || getLaby().getCellule(getPosY()+1, getPosX()+1) != 'x' ) ) // il est à une intersection, tente de suivre le CVM-Man
            {
                deciderDirectionObligatoireOrienteCVMMan(cvmman);
                setX (getPosX() +1);
            }
            else  {
                // pas d'intersection, il poursuit son chemin dans sa direction
                if ( getPosX() == 26 && getPosY() == 14 )
                    setX( 0);
                else
                    setX (getPosX()+1);
            }
        }

        public void ouest(CVMMAN cvmman)
        {
            if ( getLaby().getCellule(getPosY(), getPosX()-1)=='x'|| getLaby().getCellule(getPosY(), getPosX()-1)=='p') { // il entre dans un mur, on doit absolument changer de direction
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

        public void deciderDirectionObligatoireOrienteCVMMan(CVMMAN cvmman) {
            // 4 directions possibles

            Vector<Character> dirDispo;

            if ( getDirection() == 's')
            {
                dirDispo = directionsDispo(getPosY()+1, getPosX());
                // est ou ouest ou nord dépend du cvmman, il ne peut pas rebrousser chemin / reculer
                dirDispo.remove(new Character('n'));


            }
            else if ( getDirection() =='n')
            {
                dirDispo = directionsDispo(getPosY()-1, getPosX());
                dirDispo.remove(new Character('s'));
            }
            else if ( getDirection() =='e')
            {

                dirDispo = directionsDispo(getPosY(), getPosX()+1);

                dirDispo.remove(new Character('o'));


            }
            else //si c'est ouest
            {

                dirDispo = directionsDispo(getPosY(), getPosX()-1);
                dirDispo.remove(new Character('e')); //ne pas pouvoir reculer et entrer en transe

            }

            Vector<Character> cibles = determinerDirectionCible( cvmman.getCol(), cvmman.getLigne());
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


