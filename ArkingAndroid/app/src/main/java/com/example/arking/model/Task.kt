package com.example.arking.model

data class TaskRoot(val headers: List<TaskHeader>)


data class TaskHeader (
    val id: Int,
    val name: String,
    val categories: List<TaskCategory>,
    val tests: List<Test>
)

data class TaskCategory (
    val id: Int,
    val name: String,
    val tasks: List<Task>
)

data class Task(
    val id: Int,
    val name: String
)

data class Test(
    val id:Int,
    val name: String,
    val items: List<TestItem>
)

data class TestItem(
    val id: Int,
    val name: String,
    val description: String
)