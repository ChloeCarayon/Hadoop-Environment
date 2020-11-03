package com.opstty.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.opstty.DistrictYearWritable;
import com.opstty.mapper.DistrictOldestMapper;
import com.opstty.reducer.OldestReducer;

public class DistrictOldest {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: districtoldest <in> [<in>...] <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "districtoldest");
        job.setJarByClass(DistrictOldest.class);
        job.setMapperClass(DistrictOldestMapper.class);
        // job.setCombinerClass(OldestReducer.class);
        job.setReducerClass(OldestReducer.class);
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(DistrictYearWritable.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(NullWritable.class);
        for (int i = 0; i < otherArgs.length - 1; ++i) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
        }
        FileOutputFormat.setOutputPath(job,
                new Path(otherArgs[otherArgs.length - 1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
