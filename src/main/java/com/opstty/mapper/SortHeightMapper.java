package com.opstty.mapper;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SortHeightMapper extends Mapper<Object, Text, FloatWritable, IntWritable> {
    private final static FloatWritable height = new FloatWritable();
    private final static IntWritable id = new IntWritable();

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
    	String[] line = value.toString().split(";");
        // ignore the header and lines where height is not provided
        if (!line[0].toString().equals("GEOPOINT") && !line[6].equals("")) {
    	    // height
    		height.set(Float.parseFloat(line[6]));
    		id.set(Integer.parseInt(line[11]));
        	// our key value couple is for each tree : its height and its id
        	context.write(height, id);
        }
    }
}
