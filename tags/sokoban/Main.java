package sokoban;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

  public static void main (String[] args) throws IOException {
    /*ArrayList<ArrayList> map = Board.readingMap("sokoban/maps/map1.xsb");
    System.out.println(map);*/
    Board b= new Board("sokoban/maps/map1.xsb");
		Scanner sc= new Scanner(System.in);
    b.readingMap();
		while (!b.isFinished()) {
			System.out.println(b.grid);
			System.out.println("Entrez votre prochain mouvement parmi H (haut), G (gauche), B (bas), D (droite) :");
			String input=sc.nextLine();
			ArrayList<Integer> nextMove = new ArrayList<>();
			if (input=="H") {
				nextMove.add(-1);
				nextMove.add(0);
			}
			else if (input=="G") {
				nextMove.add(0);
				nextMove.add(-1);
			}
			else if (input=="D") {
				nextMove.add(0);
				nextMove.add(1);
			}
			else if (input=="B") {
				nextMove.add(1);
				nextMove.add(0);
			}
			else {
				nextMove.add(0);
				nextMove.add(0);
				System.out.println("Les seules commandes valides sont H,G,D,B.");
			}
			b.player.move(b, nextMove);
		}
		boolean win = true;
		for(Block o : b.listCrate) {
			if (o.deadLock){
				System.out.println("Game Over, you'r so  bad, you lose");
				win=false;
				break;
			}
		}
		if (win){
			System.out.println("Congratulations you win but you were lucky");
		}
  }
}
