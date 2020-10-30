package com.opstty.mapper;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TallestSpeciesMapper extends Mapper<Object, Text, Text, FloatWritable> {
    private final static FloatWritable height = new FloatWritable(0);
    private Text word = new Text();

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
    	String[] line = value.toString().split(";");
        
        if (!line[0].toString().equals("GEOPOINT")) {
        	word.set(line[3]);
        	if (!line[6].equals("")) {
        		height.set(Float.parseFloat(line[6]));
        	}
        	
        	context.write(word, height);
        }
    }
}
