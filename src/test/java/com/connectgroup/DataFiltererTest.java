package com.connectgroup;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class DataFiltererTest {
    @Test
    public void shouldReturnEmptyCollection_WhenLogFileIsEmpty() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/empty"), "GB").isEmpty());
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/empty"), "GB", 400).isEmpty());
        assertTrue(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/empty")).isEmpty());

    }

    @Test
    public void shouldReturnSinglaDataCollection_WhenLogFileIsSingleLine() throws FileNotFoundException {
        List<String> expected = new ArrayList<>();
        expected.add("1431592497,GB,200");
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/single-line"), "GB").containsAll(expected));
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/single-line"), "GB", 100).containsAll(expected));
        assertTrue(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/single-line")).isEmpty());

    }

    @Test
    public void shouldReturnDataCollection_WhenLogFileHaveData() throws FileNotFoundException {
        List<String> expected = new ArrayList<>();
        expected.add("1433190845,US,539");
        expected.add("1433666287,US,789");
        expected.add("1432484176,US,850");
        assertTrue((DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/multi-lines"))).containsAll(expected));


        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "US").containsAll(expected));
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"), "GB", 30).contains("1432917066,GB,37"));
    }

    @Test
    public void shoudReturnNull_WhenFilterDataIsNotThereInMultiLineFile() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "TB").isEmpty());
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"), "TB", 30).isEmpty());
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"), "US", 1000).isEmpty());

    }


    private FileReader openFile(String filename) throws FileNotFoundException {
        return new FileReader(new File(filename));
    }

}