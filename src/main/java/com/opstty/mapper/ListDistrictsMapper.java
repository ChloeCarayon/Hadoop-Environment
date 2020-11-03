package com.opstty.mapper;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ListDistrictsMapper extends Mapper<Object, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
        // separator for columns is ;
    	String[] line = value.toString().split(";");
    	// only take the the district
        word.set(line[1]);
        // won't take the header
        if (!word.toString().equals("ARRONDISSEMENT")) {
        	context.write(word, one);
        }
    }
}
