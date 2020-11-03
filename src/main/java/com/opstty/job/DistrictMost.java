package com.opstty.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.opstty.DoubleIntWritable;
import com.opstty.mapper.ConcatMapper;
import com.opstty.mapper.CountDistrictsMapper;
import com.opstty.reducer.ConcatMaxReducer;
import com.opstty.reducer.IntSumReducer;

public class DistrictMost {
	public static void main(String[] args) throws Exception {
		
		/* Job 1: get (district number, number of trees) pairs */
		Configuration conf1 = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf1, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: districtmost <in> [<in>...] <out>");
            System.exit(2);
        }
        Job job1 = Job.getInstance(conf1, "countdistricts");
        job1.setJarByClass(DistrictMost.class);
        job1.setMapperClass(CountDistrictsMapper.class);
        job1.setCombinerClass(IntSumReducer.class);
        job1.setReducerClass(IntSumReducer.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(IntWritable.class);
        for (int i = 0; i < otherArgs.length - 1; ++i) {
            FileInputFormat.addInputPath(job1, new Path(otherArgs[i]));
        }
        FileOutputFormat.setOutputPath(job1,
                new Path(otherArgs[otherArgs.length - 1], "temp"));
        job1.waitForCompletion(true);
        
        /* Job 2: get best (district number, number of trees) pair */
		Configuration conf2 = new Configuration();
        Job job2 = Job.getInstance(conf2, "concatmax");
        job2.setJarByClass(DistrictMost.class);
        job2.setMapperClass(ConcatMapper.class);
        // job2.setCombinerClass(ConcatMaxReducer.class);
        job2.setReducerClass(ConcatMaxReducer.class);
        job2.setMapOutputKeyClass(IntWritable.class);
        job2.setMapOutputValueClass(DoubleIntWritable.class);
        job2.setOutputKeyClass(IntWritable.class);
        job2.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job2,
        		new Path(otherArgs[otherArgs.length - 1], "temp"));
        FileOutputFormat.setOutputPath(job2,
                new Path(otherArgs[otherArgs.length - 1], "out"));
        System.exit(job2.waitForCompletion(true) ? 0 : 1);
	}
}
