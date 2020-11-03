package com.opstty.mapper;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ListSpeciesMapper extends Mapper<Object, Text, Text, NullWritable> {
    private Text species = new Text();

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
    	String[] line = value.toString().split(";");
        // do not take into account the header
        if (!line[0].toString().equals("GEOPOINT")) {
        	species.set(line[3]);
        	context.write(species, NullWritable.get());
        }
    }
}
