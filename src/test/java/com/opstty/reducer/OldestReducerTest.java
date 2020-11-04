package com.opstty.reducer;

import com.opstty.DoubleIntWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OldestReducerTest {
    @Mock
    private Reducer.Context context;
    private OldestReducer oldestReducer;

    @Before
    public void setup() {
        this.oldestReducer = new OldestReducer();
    }

    @Test
    public void testReduce() throws IOException, InterruptedException {
        int key = 1;
        DoubleIntWritable v1 = new DoubleIntWritable(new IntWritable(12),new IntWritable(1694));
        DoubleIntWritable v2 = new DoubleIntWritable(new IntWritable(11),new IntWritable(1756));
        DoubleIntWritable v3 = new DoubleIntWritable(new IntWritable(9),new IntWritable(1943));
        Iterable<DoubleIntWritable> values = Arrays.asList(v1,v2,v3);
        this.oldestReducer.reduce(new IntWritable(key), values, this.context);
        verify(this.context).write(new IntWritable(12),NullWritable.get());
    }
}
