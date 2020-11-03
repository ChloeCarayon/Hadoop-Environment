package com.opstty;

import com.opstty.job.CountSpecies;
import com.opstty.job.DistrictOldest;
import com.opstty.job.ListDistrics;
import com.opstty.job.ListSpecies;
import com.opstty.job.SortHeight;
import com.opstty.job.TallestSpecies;
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
                    "A map/reduce program that displays the list of districts containing trees in the input files.");
            programDriver.addClass("listspecies", ListSpecies.class,
                    "A map/reduce program that displays the list of tree species in the input files.");
            programDriver.addClass("countspecies", CountSpecies.class,
                    "A map/reduce program that counts the number of trees of each species in the input files.");
            programDriver.addClass("tallestspecies", TallestSpecies.class,
                    "A map/reduce program that calculates the height of the tallest tree of each species in the input files.");
            programDriver.addClass("sortheight", SortHeight.class,
                    "A map/reduce program that sorts the trees height from smallest to largest in the input files.");
            programDriver.addClass("districtoldest", DistrictOldest.class,
                    "A map/reduce program that displays the district where the oldest tree is in the input files.");

            exitCode = programDriver.run(argv);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        System.exit(exitCode);
    }
}
