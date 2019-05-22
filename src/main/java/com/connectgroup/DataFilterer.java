package com.connectgroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DataFilterer {


    public static Collection<?> filterByCountry(Reader source, String country)
    {
        List<String> result = Collections.emptyList();
        try {
            BufferedReader br = new BufferedReader(source);
            if (!source.ready())
                return result;
            result = br.lines().skip(1).filter(line -> {
                String[] strArray = line.split(",");
                String countryTemp =  strArray[1];
                return (3 == strArray.length
                        && null != countryTemp  &&  !"".equals(countryTemp.trim()) && country.equals(countryTemp)

                );
            } ).collect(Collectors.toList());
            br.close();
        }catch (IOException e){
            //TODO add logger here ..
        }
        return result;
    }

    public static Collection<String> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
        List<String> result = Collections.emptyList();
        try {
            BufferedReader br = new BufferedReader(source);
            if (!source.ready())
                return result;
            result = br.lines().skip(1).filter(line -> {
                String[] strArray = line.split(",");
                String countryTemp =  strArray[1];
                long responseTime = null != strArray[2] && !"".equals(strArray[2].trim())? Long.parseLong(strArray[2]):0;
                return (3 == strArray.length
                        && null != countryTemp  && !"".equals(countryTemp.trim()) && country.equals(countryTemp)
                        && limit < responseTime
                );

            } ).collect(Collectors.toList());
            br.close();
        }catch (IOException e){
            //TODO log exception here
            e.printStackTrace();
        }
        return result;
    }

    public static Collection<String> filterByResponseTimeAboveAverage(Reader source) {

        List<String> result = Collections.emptyList();

        try {

            BufferedReader br = new BufferedReader(source);
            if (!source.ready())
                return result;

            // I am taking intermediate list for storing file in memory. But if file is big this is not possible.
            // For big file we again need to reset file pointer.
            List<String> list = new ArrayList<>();
            double average = br.lines().skip(1).mapToLong(line -> {
                        String[] strArray = line.split(",");
                        list.add(line);
                        if (3 == strArray.length && null != strArray[2] && "" != strArray[2].trim())
                            return Long.parseLong(strArray[2]);
                        return 0;
                    }

            ).average().getAsDouble();
            br.close();

            result = list.parallelStream().filter(line -> {
                String[] strArray = line.split(",");
                // Here I am assuming line will have all 3 data else we are not going to include it
                return (3>=strArray.length && Integer.parseInt(strArray[2]) > average);
            }).collect(Collectors.toList());


        } catch (IOException e) {
            // here I should have a logger
            e.printStackTrace();
        }

        return result;
    }


}