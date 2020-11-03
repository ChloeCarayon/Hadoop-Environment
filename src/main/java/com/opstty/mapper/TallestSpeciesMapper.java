package com.opstty.mapper;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TallestSpeciesMapper extends Mapper<Object, Text, Text, FloatWritable> {
	private Text species = new Text();
    private final static FloatWritable height = new FloatWritable(0);

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
    	String[] line = value.toString().split(";");
        // ignore the header and lines where height is not provided
        if (!line[0].toString().equals("GEOPOINT") && !line[6].equals("")) {
        	// species
        	species.set(line[3]);
    	    // height
    		height.set(Float.parseFloat(line[6]));
        	// our key value couple is for each tree : its species and its height
        	context.write(species, height);
        }
    }
}
