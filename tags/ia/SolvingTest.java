package ia;

import sokoban.*;
import java.io.File;
import java.io.FilenameFilter;

public class SolvingTest {

	public static void main(String[] args) {
		String dirname="maps";
		File directory = new File(dirname);
		String[] playlist = directory.list(new FilenameFilter() {
																				@Override
																				public boolean accept(File dir, String name) {
																					return name.matches("^map...xsb$") || name.matches("^map..xsb$");
																				}//TODO : trouver une meilleure regexp
																			});
		java.util.Arrays.sort(playlist);
		for (int i=0; i<playlist.length ; i++) {
			System.out.println(playlist[i]);
		}
		for (String map: playlist) {
			try {
			Main test=new Main();
			String[] arg= new String[1];
			arg[0]=dirname +"/"+ map;
			test.main(arg);
			} catch (OutOfMemoryError e) {
				System.gc();
				System.out.println(map + " : Dépassement mémoire");
				continue;
			}
		}
	}
}
