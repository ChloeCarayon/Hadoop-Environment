package com.opstty.mapper;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ListKindsMapper extends Mapper<Object, Text, Text, NullWritable> {
    private Text kind = new Text();

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
    	String[] line = value.toString().split(";");
        // do not take into account the header
        if (!line[0].toString().equals("GEOPOINT")) {
        	kind.set(line[2]);
        	context.write(kind, NullWritable.get());
        }
    }
}
