package com.opstty.reducer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
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
public class MaxReducerTest {
    @Mock
    private Reducer.Context context;
    private MaxReducer maxReducer;

    @Before
    public void setup() {
        this.maxReducer = new MaxReducer();
    }

    @Test
    public void testReduce() throws IOException, InterruptedException {
        String key = "key";
        FloatWritable v1 = new FloatWritable(32);
        FloatWritable v2 = new FloatWritable(21);
        FloatWritable v3 = new FloatWritable(42);
        Iterable<FloatWritable> values = Arrays.asList(v1, v2, v3);
        this.maxReducer.reduce(new Text(key), values, this.context);
        verify(this.context).write(new Text(key), new FloatWritable(42));
    }
}
