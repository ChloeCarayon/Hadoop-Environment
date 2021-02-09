package com.opstty.reducer;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import com.opstty.DoubleIntWritable;

public class ConcatMaxReducer extends Reducer<IntWritable, DoubleIntWritable, IntWritable, IntWritable> {
    private IntWritable district = new IntWritable();
    private IntWritable number = new IntWritable();

    public void reduce(IntWritable key, Iterable<DoubleIntWritable> values, Context context)
            throws IOException, InterruptedException {
    	int max = 0;
    	int currDistrict = 0;
        for (DoubleIntWritable val : values) {
            // change the value if it is bigger than the max
        	if (val.getVal2().get() > max) {
        		max = val.getVal2().get();
        		currDistrict = val.getVal1().get();
        	}
        }
        district.set(currDistrict);
        number.set(max);
        context.write(district, number);
    }
}
