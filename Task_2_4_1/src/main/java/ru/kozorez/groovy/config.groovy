package ru.kozorez.groovy

import groovy.transform.Field




def course = CourseConfig(
        tasks: [
                Task(id: '2_1_1', name: 'Простые числа', maxPoints: 1, softDeadline: Date.parse('yyyy-MM-dd', '2024-02-16\t'), hardDeadline: Date.parse('yyyy-MM-dd', '2024-02-26')),
                Task(id: '2_3_1', name: 'Змейка', maxPoints: 1, softDeadline: Date.parse('yyyy-MM-dd', '2024-03-29'), hardDeadline: Date.parse('yyyy-MM-dd', '2024-04-05'))
        ],
        groups: [
                Group(name: '1337', students: [
                        Student(username: 'NotWhaleee', fullName: 'Agent Smith', repoUrl: 'https://github.com/NotWhaleee/OOP'),
                ])
        ]/*,
        checkpoints: [
                new Checkpoint(name: 'Checkpoint 1', date: Date.parse('yyyy-MM-dd', '2024-04-01')),
                new Checkpoint(name: 'Checkpoint 2', date: Date.parse('yyyy-MM-dd', '2024-05-20'))
        ],
        settings: new Settings(
                gradingStrategy: 'default',
                grades: ['A': 5, 'B': 4, 'C': 3]
        )*/
)