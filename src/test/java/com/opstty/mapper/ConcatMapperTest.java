package com.opstty.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.opstty.DoubleIntWritable;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ConcatMapperTest {
    @Mock
    private Mapper.Context context;
    private ConcatMapper concatMapper;

    @Before
    public void setup() {
        this.concatMapper = new ConcatMapper();
    }

    @Test
    public void testMap() throws IOException, InterruptedException {
        String value = "12	5";
        this.concatMapper.map(null, new Text(value), this.context);
        
        IntWritable val1 = new IntWritable(12);
        IntWritable val2 = new IntWritable(5);
        verify(this.context, times(1))
                .write(new IntWritable(1), new DoubleIntWritable(val1, val2));
    }
}