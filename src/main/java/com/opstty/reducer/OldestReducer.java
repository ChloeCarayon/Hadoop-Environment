package com.opstty.reducer;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import com.opstty.DistrictYearWritable;

public class OldestReducer extends Reducer<IntWritable, DistrictYearWritable, IntWritable, NullWritable> {
    private IntWritable result = new IntWritable();

    public void reduce(IntWritable key, Iterable<DistrictYearWritable> values, Context context)
            throws IOException, InterruptedException {
        Integer minYear = null;
        Integer district = 0;
        
        for (DistrictYearWritable val : values) {
        	if (minYear == null || minYear > val.getYear().get()) {
        		minYear = val.getYear().get();
        		district = val.getDistrict().get();
        	}
        }
        result.set(district);
        context.write(result, NullWritable.get());
    }
}
