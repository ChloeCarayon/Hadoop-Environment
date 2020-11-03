package com.opstty.mapper;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ListDistrictsMapper extends Mapper<Object, Text, Text, NullWritable> {
    private Text district = new Text();

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
        // separator for columns is ;
    	String[] line = value.toString().split(";");
        // won't take the header
        if (!line[0].toString().equals("GEOPOINT")) {
        	district.set(line[1]);
        	context.write(district, NullWritable.get());
        }
    }
}
