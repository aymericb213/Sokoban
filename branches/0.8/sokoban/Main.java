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
  * @param args liste des arguments.
	*/
  public static void main (String[] args) {
    FileManagement manageMap = new FileManagement();
    String playerName = "";
    Scanner sc= new Scanner(System.in);
    String[] players = new File("save/players").list();
    System.out.println("Select a player: ");
    for (int i = 0; i<players.length; i++) {
      System.out.println((i+1)+" : "+players[i].split("\\.")[0]);
    }
    String selectPlayer = sc.nextLine();
    int inputPlayer = Integer.parseInt(selectPlayer);
    while (!((0 < inputPlayer) && (inputPlayer < (players.length+1)))) {
      System.out.println("Mauvaise saisie");
      selectPlayer = sc.nextLine();
      inputPlayer = Integer.parseInt(selectPlayer);
    }
    playerName = players[inputPlayer-1];
    playerName = playerName.split("\\.")[0];
    System.out.println("Selected player: "+playerName);
		int nbMoves = 0;
    int levelCourant = 0;
    int nbMapsInDossier = 0;
    File repertoire = new File("maps/");
    File[] f = repertoire.listFiles();
    nbMapsInDossier = f.length;
		int nextMapNb = 0;
    int state = 1;
    String gContinue = "O";
		MapReader map = new MapReader("");
    Random r = new Random();
    Board b= new Board();
    PlayerReader playerLevel = new PlayerReader(playerName);
    int nbMaps = playerLevel.getLevel();
		if (args.length>0) {
			if (args[0].equals("-r")) {
        r = new Random();
        int n = r.nextInt(nbMaps) + 1;
        b.setLevel(n);
				map.setFile("maps/map" + n + ".xsb");
			} else if (args[0].equals("-l")) {
        b.setLevel(playerLevel.getLevel());
        map.setFile("save/"+playerName+".xsb");
        b.createGrid(map.getSaveMap());
        map.readingSaveMap();

      }
      else {
        if (Integer.parseInt(args[0]) > nbMaps) {
          nextMapNb = nbMaps;
          b.setLevel(nbMaps);
          map.setFile("maps/map" + nbMaps + ".xsb");
        } else {
          nextMapNb = Integer.parseInt(args[0]);
          b.setLevel(Integer.parseInt(args[0]));
          map.setFile("maps/map" + args[0] + ".xsb");
        }
			}
		} else {
      nextMapNb = nbMaps;
      b.setLevel(nbMaps);
			map.setFile("maps/map"+nbMaps+".xsb");
		}

		end :
    while (gContinue.equals("O") || gContinue.equals("o")) {
			int lenPrint = map.getFile().length();
			System.out.println("\033[H\033[2J");
      map.readingMap();
      b.createGrid(map.getMap());
  		while (!b.isFinished()) {
        state = 1;
  			System.out.println("================ SOKOBAN =================\n");
  			System.out.println("Niveau " + map.getFile().substring(lenPrint - 5, lenPrint - 4)+"\n");
  			System.out.println("\n" + b.toString());
  			System.out.println("# : mur");
  			System.out.println(". : objectif");
  			System.out.println("$ : caisse (* si placée sur un objectif)");
  			System.out.println("@ : joueur (+ si placé sur un objectif)");
  			System.out.println("\nz,q,s,d : déplacer joueur    Save : sauvegarder    l : charger    a : annuler    r : restart    e : quitter");
  			String input=sc.nextLine();
  			ArrayList<Integer> nextMove = new ArrayList<>();
				if (input.equals("E") || input.equals("e")) {
					break end;
				}
        if (input.equals("L") || input.equals("l")) {
          b.createGrid(manageMap.loadMap(playerName, map).getMap());
          break;
        }
        if (input.equals("Save") || input.equals("save")) {
          manageMap.setSave(b, playerName, playerLevel);
          System.out.println("\033[H\033[2J");
          System.out.println("Fichier sauvegardé");
          continue;
        }
        if (input.equals("A") || input.equals("a")) {
          b.createGrid(manageMap.loadCancel(playerName, map).getCancel());
        }
        if (input.equals("R") || input.equals("r")) {
          map.readingMap();
          b.createGrid(map.getMap());
        }
  			if (input.equals("Z") || input.equals("z")) {
  				nextMove.add(-1);
  				nextMove.add(0);
  			}
  			else if (input.equals("Q") || input.equals("q")) {
  				nextMove.add(0);
  				nextMove.add(-1);
  			}
  			else if (input.equals("D") || input.equals("d")) {
  				nextMove.add(0);
  				nextMove.add(1);
  			}
  			else if (input.equals("S") || input.equals("s")) {
  				nextMove.add(1);
  				nextMove.add(0);
  			}
  			else {
  				nextMove.add(0);
  				nextMove.add(0);
  				System.out.println("Entrez une commande valide.");
  			}
        manageMap.setCancel(b, playerName, playerLevel);
  			((Player)b.player).move(b, nextMove);
  			nbMoves++;
  			System.out.println("\033[H\033[2J");
  		}
      System.out.println(b.toString());

  		for(Block c : b.listCrate) {
  			if (((Crate)c).deadLock){
  				System.out.println("Game Over (plus de mouvement possible) appuyez sur 'entrer' pour recommencer ou e pour quitter");
					String input2 = sc.nextLine();
					if (input2.equals("")) {
  				state = 0;
  				break;
					}
					if (input2.equals("E") || input2.equals("e")) {
						break end;
					}
  			}
        if (!((Crate)c).placed) {
          state = 2;
        }
  		}
      System.out.println(state);
  		if (state == 1){
        if (playerLevel.getLevel() == b.getLevel()) {
          int nivPlayer = playerLevel.getLevel();
          if (nivPlayer == b.getLevel()) {
            PlayerSave playerSave = new PlayerSave(playerName,nivPlayer+1);
            playerSave.savePlayer();
          }
        }
  			System.out.println("Niveau terminé en " + nbMoves + " déplacements");
        nbMoves = 0;
        System.out.println("Lancer le prochain niveau ? (O/N)");
        String input2 = sc.nextLine();
        if (input2.equals("O") || input2.equals("o")) {
          if (args.length > 0 && args[0].equals("-r")) {
    				int temp = r.nextInt(nbMaps)+1;
						map.setFile("maps/map" + temp + ".xsb");
          } else {
            nextMapNb++;
            if (nextMapNb+2 > nbMapsInDossier) {
              System.out.println("Il n'y a plus de map disponible");
							break end;
            } else {
              map.setFile("maps/map" + nextMapNb + ".xsb");
            }
          }
        }
        gContinue = input2;
  		}
    }
  }
}
