package com.glueball.snoop.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CreateMimes {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		final Map<String, List<String>> mimes = new TreeMap<String, List<String>>();
        final StringBuilder sb = new StringBuilder();

		try(BufferedReader br = new BufferedReader(new FileReader("/home/karesz/workspace/snoop/mime_types_exts1.txt"))) {

	        String line = br.readLine();
	        while (line != null) {
	        	final String[] parts = line.split("\\|");

	        	if (!mimes.containsKey(parts[1])) {
	        		mimes.put(parts[1], new ArrayList<String>());
	        	}
	        	mimes.get(parts[1]).add(parts[0]);

	            line = br.readLine();
	        }
	        System.out.println(sb.toString());
	    }

		try(FileWriter fw = new FileWriter("/home/karesz/workspace/snoop/mime_types_exts_out.txt")) {
			
			for (final String key : mimes.keySet()) {
				sb.append("\t\t\t\t<entry key=").append("\"").append(key).append("\">").append(System.lineSeparator());
				sb.append("\t\t\t\t\t<array>").append(System.lineSeparator());
				for (final String value : mimes.get(key)) {
					sb.append("\t\t\t\t\t\t<value>").append(value).append("</value>").append(System.lineSeparator());
				}
				sb.append("\t\t\t\t\t</array>").append(System.lineSeparator());
				sb.append("\t\t\t\t</entry>").append(System.lineSeparator());
			}

			fw.write(sb.toString());
		}

	}

}
