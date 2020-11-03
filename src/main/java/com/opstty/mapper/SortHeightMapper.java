package com.opstty.mapper;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SortHeightMapper extends Mapper<Object, Text, FloatWritable, IntWritable> {
    private final static FloatWritable height = new FloatWritable(0);
    private final static IntWritable one = new IntWritable(1);

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
    	String[] line = value.toString().split(";");
        // not the header
        if (!line[0].toString().equals("GEOPOINT")) {
        	if (!line[6].equals("")) {
        	    // height
        		height.set(Float.parseFloat(line[6]));
        	}
        	// our key value couple is for each tree : its species and its height
        	context.write(height, one);
        }
    }
}
