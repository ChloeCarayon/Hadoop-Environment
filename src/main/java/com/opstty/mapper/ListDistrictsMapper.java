package com.opstty.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class ListDistrictsMapper extends Mapper<Object, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
    	int count = 0;
        StringTokenizer itr = new StringTokenizer(value.toString(), ";");
        while (itr.hasMoreTokens() && count != 2) {
            word.set(itr.nextToken());
            count++;
        }
        if (!word.toString().equals("ARRONDISSEMENT")) {
        	context.write(word, one);
        }
    }
}
