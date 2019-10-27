package com.example.pierreleveille.cvm_man;

import java.util.Vector;

public class Fantome {
        private int x; // colonne
        private int y; // ligne
        private char direction ; // 's', 'n', 'e', 'o'
        private Labyrinthe laby;



        public Fantome( int x, int y,char direction, Labyrinthe laby) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.laby = laby;
        }

        public void recommencer ()
        {
            setX(14);
            setY(13);
            setDirection ('n');
        }

        public int getPosX() { return x; }

        public int getPosY() {
            return y;
        }

        public char getDirection() {
            return direction;
        }

        public void setX(int posX) {
            this.x = posX;
        }

        public void setY(int posY) {
            this.y = posY;
        }

        public void setDirection(char direction) {
            this.direction = direction;
        }



        public Labyrinthe getLaby() {
            return laby;
        }


        public void setJeu(Labyrinthe laby) {
            this.laby = laby;
        }



        public Vector<Character> directionsDispo (int x , int y)
        {
            Vector<Character> dirDispo = new Vector<Character>();

            if ( laby.getCellule(x,y+1) != 'x' && laby.getCellule(x, y+1) != 'p')
                dirDispo.add('s');

            if ( laby.getCellule( x,y-1) != 'x'&& laby.getCellule(x, y-1) != 'p' )
                dirDispo.add('n');

            if ( laby.getCellule(x+1,y) != 'x'&& laby.getCellule(x+1,y) != 'p')
                dirDispo.add('e');

            if ( laby.getCellule(x-1,y) != 'x'&& laby.getCellule(x-1,y) != 'p')
                dirDispo.add('o');

            return dirDispo;
        }



        public  Vector<Character> determinerDirectionCible ( int ligneCible, int colonneCible )
        {
            Vector<Character> cibles = new Vector<Character>();
            if ( ligneCible < getPosY() )
            {
                if ( colonneCible < getPosX())
                {

                    cibles.add('n');
                    cibles.add('o');
                }
                else if ( colonneCible == getPosX())
                    cibles.add('n');
                else
                {
                    cibles.add('n');
                    cibles.add('e');
                }
            }
            else if ( ligneCible == getPosY())
            {
                if ( colonneCible < getPosX())
                {
                    cibles.add('o');
                }
                else
                    cibles.add('e');
            }
            else //ligneCible > getLigne()
            {
                if ( colonneCible < getPosX())
                {
                    //no
                    cibles.add('s');
                    cibles.add('o');
                }
                else if ( colonneCible == getPosX())
                    cibles.add('s');
                else
                {
                    cibles.add('s');
                    cibles.add('e');
                }
            }
            return cibles;
        }
}


