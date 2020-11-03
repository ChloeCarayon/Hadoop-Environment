package com.opstty.mapper;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.opstty.DoubleIntWritable;

public class DistrictOldestMapper extends Mapper<Object, Text, IntWritable, DoubleIntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private final static IntWritable district = new IntWritable();
    private final static IntWritable year = new IntWritable();
    private final static DoubleIntWritable districtYear = new DoubleIntWritable();

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
    	String[] line = value.toString().split(";");
        // ignore the header and lines where year is not provided
        if (!line[0].toString().equals("GEOPOINT") && !line[5].equals("")) {
    		district.set(Integer.parseInt(line[1]));
    		year.set(Integer.parseInt(line[5]));
    		districtYear.setVal1(district);
    		districtYear.setVal2(year);

    		// all output will have the same key but different values
        	context.write(one, districtYear);
        }
    }
}
