package com.opstty.mapper;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.opstty.DoubleIntWritable;

public class ConcatMapper extends Mapper<Object, Text, IntWritable, DoubleIntWritable> {
	private final static IntWritable one = new IntWritable(1);
	private final static IntWritable district = new IntWritable();
	private final static IntWritable number = new IntWritable();
	private final static DoubleIntWritable districtCount = new DoubleIntWritable();

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
    	String[] line = value.toString().split("\t");
    	
    	district.set(Integer.parseInt(line[0]));
    	number.set(Integer.parseInt(line[1]));
    	districtCount.setVal1(district);
    	districtCount.setVal2(number);
    	
    	context.write(one, districtCount);
    }
}
