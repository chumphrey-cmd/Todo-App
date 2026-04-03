import type {Task} from "../TaskType.ts";
import {http, HttpResponse} from "msw";
import {setupServer} from "msw/node";
import {axiosGetAllTasks, getAllTasks} from "../TaskService.tsx";
import {afterAll, afterEach, beforeAll} from "vitest";

describe('Task Service', () => {


    // axios.defaults.baseURL = "http://localhost:8080";

    const server = setupServer();
    beforeAll(() => server.listen());
    afterAll(() => server.close());
    afterEach(() => server.resetHandlers());

    it('should get all tasks', async () => {

        const expected: Task [] = [
            {id: 1, title: 'First Task', description: 'get task component built 1.'},
            {id: 2, title: 'Second Task', description: 'get task component built 2.'}
        ];

        server.use(http.get('api/v1/task', () => HttpResponse.json(expected, {status: 200})))

        expect(await axiosGetAllTasks()).toStrictEqual(expected);
        expect(await getAllTasks()).toStrictEqual(expected);
    });

});