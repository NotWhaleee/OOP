
package ru.kozorez.groovy

import groovy.transform.Field
import groovy.util.ConfigSlurper
import java.util.Date


class Task {
    String id
    String name
    int maxPoints
    Date softDeadline
    Date hardDeadline
}

class Group {
    String name
    List<Student> students
}

class Student {
    String username
    String fullName
    String repoUrl
}


/*class Checkpoint {
    String name
    Date date
}

class Settings {
    String gradingStrategy
    Map<String, Integer> grades
}*/


// ... (ваш существующий код для Task, Group, Student)

def courseConfig(Closure cl) {
    cl.delegate = CourseConfig
    cl.call()
}

class CourseConfig {
    List<Task> tasks
    List<Group> groups

    void tasks(List<Task> tasks){
        this.tasks = tasks
    }

    void group(List<Group> groups){
        this.groups = groups
    }
}

def loadCourseConfig(String filePath) {
    def config = new ConfigSlurper().parse(new File(filePath).toURL())
    CourseConfig courseConfig = new CourseConfig()

    if (config.tasks) {
        courseConfig.tasks = config.tasks.collect { task ->
            new Task(
                    id: task.id,
                    name: task.name,
                    maxPoints: task.maxPoints,
                    softDeadline: Date.parse('yyyy-MM-dd', task.softDeadline),
                    hardDeadline: Date.parse('yyyy-MM-dd', task.hardDeadline)
            )
        }
    }

    if (config.groups) {
        courseConfig.groups = config.groups.collect { group ->
            new Group(
                    name: group.name,
                    students: group.students.collect { student ->
                        new Student(
                                username: student.username,
                                fullName: student.fullName,
                                repoUrl: student.repoUrl
                        )
                    }
            )
        }
    }

    return courseConfig
}

// Пример использования
def configFilePath = "config.groovy"
CourseConfig courseConfig = loadCourseConfig(configFilePath)


