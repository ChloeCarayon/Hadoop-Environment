package com.opstty;

import com.opstty.job.CountSpecies;
import com.opstty.job.ListDistrics;
import com.opstty.job.ListSpecies;
import com.opstty.job.WordCount;
import org.apache.hadoop.util.ProgramDriver;

public class AppDriver {
    public static void main(String argv[]) {
        int exitCode = -1;
        ProgramDriver programDriver = new ProgramDriver();

        try {
            programDriver.addClass("wordcount", WordCount.class,
                    "A map/reduce program that counts the words in the input files.");
            programDriver.addClass("listdistricts", ListDistrics.class,
                    "A map/reduce program that displays the list of districts in the input files.");
            programDriver.addClass("listspecies", ListSpecies.class,
                    "A map/reduce program that displays the list of species in the input files.");
            programDriver.addClass("countspecies", CountSpecies.class,
                    "A map/reduce program that counts the number of trees of each species in the input files.");

            exitCode = programDriver.run(argv);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        System.exit(exitCode);
    }
}
