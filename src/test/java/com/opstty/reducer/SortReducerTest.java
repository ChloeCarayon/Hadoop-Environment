package com.opstty.reducer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
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
public class SortReducerTest {
    @Mock
    private Reducer.Context context;
    private SortReducer sortReducer;

    @Before
    public void setup() {
        this.sortReducer = new SortReducer();
    }

    @Test
    public void testReduce() throws IOException, InterruptedException {
        float key = 13;
        IntWritable v1 = new IntWritable(12);
        Iterable<IntWritable> values = Arrays.asList(v1);
        this.sortReducer.reduce(new FloatWritable(key), values, this.context);
        verify(this.context).write(new FloatWritable(key),v1);
    }
}

