package ia;

import sokoban.*;
import java.io.File;
import java.io.FilenameFilter;

public class SolvingTest {

	public static void main(String[] args) {
		String dirname="maps";
		File directory = new File(dirname);
		String[] playlist = directory.list(/*new FilenameFilter() {
																				@Override
																				public boolean accept(File dir, String name) {
																					return name.matches("*.xsb$");
																				}
																			}*/);
		System.out.println(playlist.);
		for (String map: playlist) {
			Main test=new Main();
			long start = System.currentTimeMillis();
			String[] arg= new String[1];
			arg[0]=dirname +"/"+ map;
			test.main(arg);
			long time = System.currentTimeMillis()-start;
			System.out.println(map + " : " + time);
		}
	}
}
