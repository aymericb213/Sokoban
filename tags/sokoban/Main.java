package sokoban;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
	* Classe exécutable du package sokoban.
	*/
public class Main {

/**
	* Lit un fichier .xsb pour afficher en console un niveau de Sokoban jouable
	* et le met à jour en fonction des entrées utilisateur.
	* On peut exécuter la classe de trois façons différentes :
	* - si aucun argument n'est donné, on parcourt les niveaux dans l'ordre;
	* - si on donne en argument un numéro de map présente dans le dossier maps, le niveau sélectionné sera affiché;
	* - si l'argument est -r, un niveau est choisi au hasard.
	*/
  public static void main (String[] args) throws IOException {
		int nbMaps = new File("sokoban/maps").list().length;
		int nbMoves = 0;
		int nextMapNb = 0;
    String gContinue = "O";
    Board b= new Board();
		Scanner sc= new Scanner(System.in);
		MapReader map = new MapReader("");
    Random r = new Random();
		if (args.length>0) {
			if (args[0].equals("-r")) {
				int n = r.nextInt(nbMaps)+1;
				map.setFile("sokoban/maps/map" + n + ".xsb");
			} else {
				nextMapNb = Integer.parseInt(args[0]);
				map.setFile("sokoban/maps/map" + args[0] + ".xsb");
			}
		} else {
			map.setFile("sokoban/maps/map1.xsb");
		}
    while (gContinue.equals("O") || gContinue.equals("o")) {
			System.out.println("\033[H\033[2J");
      map.readingMap();
      //il faudrait que ce soit mieux fait
      Save test = new Save (map.getMap());
      test.saveMap();
      //jusque là
      b.createGrid(map.getMap());
  		while (!b.isFinished()) {
  			System.out.println("================ SOKOBAN =================\n");
  			System.out.println("Niveau " + map.getFile().charAt(map.getFile().length() - 5)+"\n");
  			System.out.println("\n" + b.toString());
  			System.out.println("# : mur");
  			System.out.println(". : objectif");
  			System.out.println("$ : caisse (* si placée sur un objectif)");
  			System.out.println("@ : joueur (+ si placé sur un objectif)");
  			System.out.println("\nEntrez votre prochain mouvement parmi H (haut), G (gauche), B (bas), D (droite) :");
  			String input=sc.nextLine();
  			ArrayList<Integer> nextMove = new ArrayList<>();
  			if (input.equals("H") || input.equals("h") || input.equals("z")) {
  				nextMove.add(-1);
  				nextMove.add(0);
  			}
  			else if (input.equals("G") || input.equals("g") || input.equals("q")) {
  				nextMove.add(0);
  				nextMove.add(-1);
  			}
  			else if (input.equals("D") || input.equals("d")) {
  				nextMove.add(0);
  				nextMove.add(1);
  			}
  			else if (input.equals("B") || input.equals("b") || input.equals("s")) {
  				nextMove.add(1);
  				nextMove.add(0);
  			}
  			else {
  				nextMove.add(0);
  				nextMove.add(0);
  				System.out.println("Les seules commandes valides sont H,G,D,B.");
  			}
  			((Player)b.player).move(b, nextMove);
  			nbMoves++;
  			System.out.println("\033[H\033[2J");
  		}
      System.out.println(b.toString());
  		boolean win = true;
  		for(Block c : b.listCrate) {
  			if (((Crate)c).deadLock){
  				System.out.println("Game Over (plus de mouvement possible) appuyez sur 'entrer' pour recommencer");
					String input2 = sc.nextLine();
					if (input2.equals("")) {
  				win=false;
  				break;
					}
  			}
  		}
  		if (win){
  			System.out.println("Niveau terminé en " + nbMoves + " déplacements");
        nbMoves = 0;
        System.out.println("Lancer le prochain niveau ? (O/N)");
        String input2 = sc.nextLine();
        if (input2.equals("O") || input2.equals("o")) {
          if (args[0].equals("-r")) {
    				int temp = r.nextInt(nbMaps)+1;
						map.setFile("sokoban/maps/map" + temp + ".xsb");
          } else {
            nextMapNb++;
            if (nextMapNb > nbMaps) {
              System.out.println("Il n'y a plus de map disponible");
							break;
            } else {
              map.setFile("sokoban/maps/map" + nextMapNb + ".xsb");
            }
          }
        }
        gContinue = input2;
  		}
    }
  }
}
