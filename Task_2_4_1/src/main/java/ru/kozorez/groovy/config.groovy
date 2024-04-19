package ru.kozorez.groovy

import groovy.transform.Field

class CourseConfig {
    List<Task> tasks
    List<Group> groups
    List<Checkpoint> checkpoints
    Settings settings
}

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

class Checkpoint {
    String name
    Date date
}

class Settings {
    String gradingStrategy
    Map<String, Integer> grades
}

def course = new CourseConfig(
        tasks: [
                new Task(id: '2_1_1', name: 'Простые числа', maxPoints: 1, softDeadline: Date.parse('yyyy-MM-dd', '2024-02-16\t'), hardDeadline: Date.parse('yyyy-MM-dd', '2024-02-26')),
                new Task(id: '2_3_1', name: 'Змейка', maxPoints: 1, softDeadline: Date.parse('yyyy-MM-dd', '2024-03-29'), hardDeadline: Date.parse('yyyy-MM-dd', '2024-04-05'))
        ],
        groups: [
                new Group(name: '1337', students: [
                        new Student(username: 'NotWhaleee', fullName: 'Agent Smith', repoUrl: 'https://github.com/NotWhaleee/OOP'),
                ])
        ],
        checkpoints: [
                new Checkpoint(name: 'Checkpoint 1', date: Date.parse('yyyy-MM-dd', '2024-04-01')),
                new Checkpoint(name: 'Checkpoint 2', date: Date.parse('yyyy-MM-dd', '2024-05-20'))
        ],
        settings: new Settings(
                gradingStrategy: 'default',
                grades: ['A': 5, 'B': 4, 'C': 3]
        )
)

return course
