package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import core.StaticGraph;

public class GrGraphReader {
	
	public static StaticGraph readGraph(String filepath) throws Exception {
		long start = System.currentTimeMillis();
		
		File file = new File(filepath);
		BufferedReader in = new BufferedReader(new FileReader(file));
		
		String st;
		String[] stParts;
		
		int v = -1;
		
		ArrayList<int[]> edges = new ArrayList<>();
		
		while((st = in.readLine()) != null) {
			char firstChar = st.charAt(0);
			
			if(firstChar == 'c') continue;
			
			stParts = st.split(" ");
			
			if(firstChar == 'p') {
				if(v > -1) throw new Exception("found more than one line that starts with 'p' in the file");
												
				v = Integer.parseInt(stParts[2]);				
				continue;
			}
			
			edges.add(new int[] {Integer.parseInt(stParts[0])-1,Integer.parseInt(stParts[1])-1});
		}
		
		in.close();
		
		StaticGraph graph = new StaticGraph(v);
		
		for (int[] edge : edges)
			graph.addEdge(edge);

		System.out.println(v + " vertices loaded in " + (System.currentTimeMillis()-start) + " ms");
		
		return graph;
	}
	
	public static void main(String[] args) throws Exception {
		
		StaticGraph graph = readGraph("graphs/e/exact_001.gr");
		
		System.out.println("graph loaded");
	}

}
