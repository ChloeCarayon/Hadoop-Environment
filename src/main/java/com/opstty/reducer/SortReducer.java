package com.opstty.reducer;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class SortReducer extends Reducer<FloatWritable, IntWritable, FloatWritable, IntWritable> {

    public void reduce(FloatWritable key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
    	for (IntWritable val : values) {
    		context.write(key, val);
    	}
    }
}
