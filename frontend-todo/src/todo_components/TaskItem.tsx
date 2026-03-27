// import React from 'react';
import type {Task} from "./TaskType.ts";

    type TaskProps = {
        initialTask: Task
}

// Passing into the Component
export const TaskItem = ({initialTask}: TaskProps) => {

    return (


        // <li aria-label = {"task"} > {initialTask.title} {initialTask.description} </li>
        <li aria-label = {"task"} > {initialTask.id} {initialTask.title}: {initialTask.description} </li>
            
    );
};

export default TaskItem;