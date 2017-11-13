package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static com.company.Utils.parseTime;

public class ScheduleParser {
    Schedule schedule = new Schedule(0);


    public ScheduleParser(File file) {
        ArrayList<String> linesOfScheduleFile = readLinesOfFile(file);
        schedule.setPeriods(buildScheduleHashMap(linesOfScheduleFile));
    }

    public ScheduleParser() {
        schedule.setPeriods(defaultSchedule());
    }

    public static LinkedHashMap<String, Period> defaultSchedule() {
        LinkedHashMap<String, Period> periods = new LinkedHashMap<>();

        periods.put("A", new Period(parseTime("8:19"), parseTime("9:07")));
        periods.put("B", new Period(parseTime("9:11"), parseTime("9:59")));
        periods.put("C", new Period(parseTime("10:03"), parseTime("10:51")));
        periods.put("D", new Period(parseTime("10:55"), parseTime("11:43")));
        periods.put("E", new Period(parseTime("11:47"), parseTime("12:35")));
        periods.put("Lunch", new Period(parseTime("12:35"), parseTime("13:01")));
        periods.put("F", new Period(parseTime("13:01"), parseTime("13:53")));
        periods.put("G", new Period(parseTime("13:57"), parseTime("14:45")));

        return periods;
    }

    public static ArrayList<String> readLinesOfFile(File file) {
        ArrayList<String> lineList = new ArrayList<>();
        try {

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while ((line = reader.readLine()) != null) {
                lineList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineList;
    }

    public static LinkedHashMap<String, Period> buildScheduleHashMap(ArrayList<String> linesOfScheduleFile) {
        LinkedHashMap<String, Period> scheduleHashMap = defaultSchedule();

        for (String line : linesOfScheduleFile) {
            String[] parts = new String[2];
            String[] times = parts[1].split(" - ");
            if ((!line.isEmpty() && !line.startsWith("//")) || !line.contains(":")) { //ignore empty lines and commented out lines and lines that don't have colons in them
                parts[0] = line.substring(0, line.indexOf(":"));
                parts[1] = line.substring(line.indexOf(":"), line.length());
                System.out.println(parts[0] + " | " + parts[1]);
                scheduleHashMap.put(parts[0], new Period(parseTime(times[0]), parseTime(times[1])));
            } else {
                if (line.startsWith("NAME - ")) {
                    //todo put stuff to get name here
                }
            }
        }
        return scheduleHashMap;
    }

    public LinkedHashMap<String, Period> getScheduleHashMap() {
        return schedule.getPeriods();
    }
}
