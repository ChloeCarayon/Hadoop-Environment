package com.opstty.mapper;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CountKindsMapper extends Mapper<Object, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
    private Text kind = new Text();

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
    	String[] line = value.toString().split(";");
        // do not take into account the header
        if (!line[0].toString().equals("GEOPOINT")) {
        	kind.set(line[2]);
        	context.write(kind, one);
        }
    }
}
