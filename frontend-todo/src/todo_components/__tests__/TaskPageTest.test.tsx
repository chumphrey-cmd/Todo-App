import {render, screen, within} from "@testing-library/react";
import {TaskPage} from "../TaskPage.tsx";
import {expect} from "vitest";
import type {Task} from "../TaskType.ts";

describe('Task Page', () => {

    it('should display title page', () => {
        render(<TaskPage/>);

        expect(screen.getByRole('heading', {name: /Task List/i})).toBeInTheDocument();
    });

    it('should show multiple tasks', () => {

        const task1: Task = {id: 1, title: 'First Task', description: 'get task component built 1.'}

        const task2: Task = {id: 2, title: 'Second Task', description: 'get task component built 2.'}

        // render(<TaskItem initialTask={task1}/>);
        // render(<TaskItem initialTask={task2}/>);

        const tasks = [task1, task2]

        render(<TaskPage/>)
        screen.logTestingPlaygroundURL();
        expect(screen.getByRole('list')).toBeInTheDocument();

        const list = screen.getByRole('list');
        expect(within(list).queryAllByLabelText(/task/i)[0]).toBeInTheDocument();

    });

});