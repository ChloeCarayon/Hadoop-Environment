package com.opstty.reducer;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MaxReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {
    private FloatWritable result = new FloatWritable();

    public void reduce(Text key, Iterable<FloatWritable> values, Context context)
            throws IOException, InterruptedException {
        float max = 0;
        for (FloatWritable val : values) {
            // change the value if it is bigger than the max
        	if (val.get() > max) {
        		max = val.get();
        	}
        }
        result.set(max);
        context.write(key, result);
    }
}
