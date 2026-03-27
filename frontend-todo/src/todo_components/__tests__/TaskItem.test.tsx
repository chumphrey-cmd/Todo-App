import {render, screen} from "@testing-library/react";
import TaskItem from "../TaskItem.tsx";
import type {Task} from "../TaskType.ts";

describe('Task Item Test', () => {

    it('should display Task Item', () => {

        // Plain Old TypeScript Object (POTO) from TaskType.ts
        const task1: Task = {id: 1, title: 'First Task', description: 'get task component built.'}

        render(<TaskItem initialTask={task1}/>);

        screen.logTestingPlaygroundURL();
        expect(screen.getByRole('listitem', {name: /task/i})).toBeInTheDocument();

        expect(screen.getByText('First Task: get task component built.', {exact: false})).toBeInTheDocument();

    });

});