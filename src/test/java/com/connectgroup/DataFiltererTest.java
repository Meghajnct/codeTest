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
    public void shouldReturnEmptyCollection_ByCountry_WhenLogFileIsEmpty() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/empty"), "GB").isEmpty());

    }

    @Test
    public void shouldReturnFilteredData_ByCountry_WhenLogFileIsSingleLine() throws FileNotFoundException {
        List<String> expected = new ArrayList<>();
        expected.add("1431592497,GB,200");
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/single-line"), "GB").containsAll(expected));

    }

    @Test
    public void shouldReturnEmpty_ByCountry_WhenLogFileIsSingleLine() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/single-line"), "TB").isEmpty());

    }

    @Test
    public void shouldReturnFilteredData_ByCountry_WhenLogFileIsMultiLine() throws FileNotFoundException {
        List<String> expected = new ArrayList<>();
        expected.add("1433190845,US,539");
        expected.add("1433666287,US,789");
        expected.add("1432484176,US,850");
        assertTrue((DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/multi-lines"))).containsAll(expected));

    }
    
    @Test
    public void shouldReturnEmptyCollection_ByCountryWithResponseLimi_WhenLogFileIsEmpty() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/empty"), "GB", 400).isEmpty());

    }

    @Test
    public void shouldReturnFilteredData_ByCountryWithResponseLimit_WhenLogFileIsSingleLine() throws FileNotFoundException {
        List<String> expected = new ArrayList<>();
        expected.add("1431592497,GB,200");
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/single-line"), "GB", 100).containsAll(expected));

    }

    @Test
    public void shouldReturnEmpty_ByCountryWithResponseLimit_WhenLogFileIsSingleLine() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/single-line"), "TB", 100).isEmpty());

    }

    @Test
    public void shouldReturnFilteredData_ByCountryWithResponseLimit_WhenLogFileIsMultiLine() throws FileNotFoundException {
        List<String> expected = new ArrayList<>();
        expected.add("1433190845,US,539");
        expected.add("1433666287,US,789");
        expected.add("1432484176,US,850");
        assertTrue((DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"), "US", 400)).containsAll(expected));

    }


    @Test
    public void shouldReturnEmptyCollection_WithAverageResponse_WhenLogFileIsEmpty() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/empty")).isEmpty());

    }

    @Test
    public void shouldReturnEmptyCollection_WithAverageResponse_WhenLogFileIsSingleLine() throws FileNotFoundException {
        List<String> expected = new ArrayList<>();
        expected.add("1431592497,GB,200");
        assertTrue(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/single-line")).isEmpty());

    }

    @Test
    public void shouldReturnDataCollection_WithAverageResponse_WhenLogFileHaveData() throws FileNotFoundException {
        List<String> expected = new ArrayList<>();
        expected.add("1433190845,US,539");
        expected.add("1433666287,US,789");
        expected.add("1432484176,US,850");
        assertTrue(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/multi-lines")).containsAll(expected));

    }


    private FileReader openFile(String filename) throws FileNotFoundException {
        return new FileReader(new File(filename));
    }

}