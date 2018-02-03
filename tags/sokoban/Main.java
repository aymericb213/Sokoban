package sokoban;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.io.File;

public class Main {

  public static void main (String[] args) throws IOException {
		System.out.println("\033[H\033[2J");
		int nbMaps = new File("sokoban/maps").list().length;
		int nbMoves = 0;
    Board b= new Board();
		Scanner sc= new Scanner(System.in);
		MapReader map = new MapReader("");
		if (args.length>0) {
			if (args[0].equals("-r")) {
				Random r = new Random();
				int n = r.nextInt(nbMaps)+1;
				map.setFile("sokoban/maps/map" + n + ".xsb");
			} else {
				map.setFile("sokoban/maps/" + args[0]);
			}
		} else {
			map.setFile("sokoban/maps/map1.xsb");
		}
		map.readingMap();
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
			if (input.equals("H") || input.equals("h")) {
				nextMove.add(-1);
				nextMove.add(0);
			}
			else if (input.equals("G") || input.equals("g")) {
				nextMove.add(0);
				nextMove.add(-1);
			}
			else if (input.equals("D") || input.equals("d")) {
				nextMove.add(0);
				nextMove.add(1);
			}
			else if (input.equals("B") || input.equals("b")) {
				nextMove.add(1);
				nextMove.add(0);
			}
			else {
				nextMove.add(0);
				nextMove.add(0);
				System.out.println("Les seules commandes valides sont H,G,D,B.");
			}
			((Player)b.player).move(b, nextMove);
			nbMoves+=1;
			System.out.println("\033[H\033[2J");
		}
    System.out.println(b.toString());
		boolean win = true;
		for(Block c : b.listCrate) {
			if (((Crate)c).deadLock){
				System.out.println("Game Over (plus de mouvement possible)");
				win=false;
				break;
			}
		}
		if (win){
			System.out.println("Niveau terminé en " + nbMoves + " déplacements");
		}
  }
}
