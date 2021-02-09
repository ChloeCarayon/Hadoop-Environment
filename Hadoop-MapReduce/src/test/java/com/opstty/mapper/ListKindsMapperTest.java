package com.opstty.mapper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ListKindsMapperTest {
    @Mock
    private Mapper.Context context;
    private ListKindsMapper listKindsMapper;

    @Before
    public void setup() {
        this.listKindsMapper = new ListKindsMapper();
    }

    @Test
    public void testMap() throws IOException, InterruptedException {
        String value = "(48.857140829, 2.29533455314);7;Maclura;pomifera;Moraceae;1935;13.0;;Quai Branly, avenue de La Motte-Piquet, avenue de la Bourdonnais, avenue de Suffren;Oranger des Osages;;6;Parc du Champs de Mars";
        this.listKindsMapper.map(null, new Text(value), this.context);
        
        verify(this.context, times(1))
                .write(new Text("Maclura"), NullWritable.get());
    }
}