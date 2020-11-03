package com.opstty.reducer;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import com.opstty.DoubleIntWritable;

public class OldestReducer extends Reducer<IntWritable, DoubleIntWritable, IntWritable, NullWritable> {
    private IntWritable result = new IntWritable();

    public void reduce(IntWritable key, Iterable<DoubleIntWritable> values, Context context)
            throws IOException, InterruptedException {
        Integer minYear = null;
        Integer district = 0;
        
        for (DoubleIntWritable val : values) {
        	if (minYear == null || minYear > val.getVal2().get()) {
        		minYear = val.getVal2().get();
        		district = val.getVal1().get();
        	}
        }
        result.set(district);
        context.write(result, NullWritable.get());
    }
}
