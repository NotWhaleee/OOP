/*
package ru.kozorez;

import groovy.util.ConfigObject;
import ru.kozorez.groovy.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import groovy.util.ConfigSlurper;

public class ConfigParser {
    private CourseConfig course;

    public ConfigParser(String filePath) {
        try {
            URL configUrl = new File(filePath).toURI().toURL();
            ConfigSlurper configSlurper = new ConfigSlurper();
            ConfigObject configObject = configSlurper.parse(configUrl);
            this.course = convertToCourseConfig(configObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CourseConfig convertToCourseConfig(ConfigObject configObject) {
        List<Map<String, Object>> tasks = (List<Map<String, Object>>) configObject.get("tasks");
        List<Map<String, Object>> groups = (List<Map<String, Object>>) configObject.get("groups");
        List<Map<String, Object>> checkpoints = (List<Map<String, Object>>) configObject.get("checkpoints");
        Map<String, Object> settingsMap = (Map<String, Object>) configObject.get("settings");

        // Convert tasks
        List<Task> taskList = tasks.stream().map(taskMap -> {
            return new Task(
                    (String) taskMap.get("id"),
                    (String) taskMap.get("name"),
                    (int) taskMap.get("maxPoints"),
                    (String) taskMap.get("softDeadline"),
                    (String) taskMap.get("hardDeadline")
            );
        }).collect(Collectors.toList());

        // Convert groups
        List<Group> groupList = groups.stream().map(groupMap -> {
            List<Map<String, Object>> students = (List<Map<String, Object>>) groupMap.get("students");
            List<Student> studentList = students.stream().map(studentMap -> {
                return new Student(
                        (String) studentMap.get("username"),
                        (String) studentMap.get("fullName"),
                        (String) studentMap.get("repoUrl")
                );
            }).collect(Collectors.toList());
            return new Group((String) groupMap.get("name"), studentList);
        }).collect(Collectors.toList());

        // Convert checkpoints
        List<Checkpoint> checkpointList = checkpoints.stream().map(checkpointMap -> {
            return new Checkpoint(
                    (String) checkpointMap.get("name"),
                    (String) checkpointMap.get("date")
            );
        }).collect(Collectors.toList());

        // Convert settings
        Settings settings = new Settings(
                (String) settingsMap.get("gradingStrategy"),
                (Map<String, Integer>) settingsMap.get("grades")
        );

        return new CourseConfig(taskList, groupList, checkpointList, settings);
    }

    public CourseConfig getCourse() {
        return course;
    }
}*/
