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
        // we have only one key with all the different values

        Integer minYear = null;
        Integer district = 0;
        // we have to compare them to obtain the oldest tree = the minimum value for the year
        for (DoubleIntWritable val : values) {
        	if (minYear == null || minYear > val.getVal2().get()) {
        		minYear = val.getVal2().get();
        		district = val.getVal1().get();
        	}
        }
        // we are only interested in the district information
        result.set(district);
        context.write(result, NullWritable.get());
    }
}
