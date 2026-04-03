import React, {useEffect, useState} from 'react';
import {TaskItem} from "./TaskItem.tsx";
import {axiosGetAllTasks, getAllTasks} from "./TaskService.tsx";

import type {Task} from "./TaskType.ts";

export const TaskPage = () => {

    // Here we are creating an array of Task[] items...
    const [tasks, setTasks] = useState<Task[]>([]);


    /// NOTE: Static creation of tasks before we implemented the "axios/fetch"  get all tasks...

    // const refreshData = () => {
    //     const task1: Task = {id: 1, title: 'First Task', description: 'get task component built 1.'};
    //     const task2: Task = {id: 2, title: 'Second Task', description: 'get task component built 2.'};
    //     return ([{task1}, {task2}
    //     ]);
    // }


    // Get Tasks Here!
    const refreshData = () => {
        axiosGetAllTasks().then(setTasks);
    };

    useEffect(() => {
        refreshData();
    }, [])

    return (
        <>
            <h1>Task List</h1>
            <ul>
                {Array.isArray(tasks) ? (tasks.map(task =>
                    <TaskItem
                        key={task.id}
                        initialTask={task}
                    />
                )) : <div>No Tasks found.</div>}
            </ul>

        </>
    );
};