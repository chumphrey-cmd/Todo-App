import {describe, expect} from "vitest";
import App from "../App.tsx";
import {render, screen} from "@testing-library/react";
import {userEvent} from "@testing-library/user-event/dist/cjs/setup/index.js";

describe('basic App.tsx Test!', () => {

    it('should display heading', () => {

        // Arrange
        render(<App/>)

        // Act

        // Assert
        // "/i" regex for case insensitivity!
        expect(screen.getByText(/started/i)).toBeInTheDocument()

        // getByRole
        expect(screen.getByRole('heading', {name: /started/i})).toBeInTheDocument()

        // Excellent tool to assist in rendering your application. You
        screen.logTestingPlaygroundURL()

    });

    it('should count button increment', async () => {


        // Arrange
        render(<App/>)

        // Creating constant variable for button!
        const button = screen.getByRole('button', {name: /count/i});

        // Creating constat variable for user event!
        const user = userEvent.setup();

        //Assert
        expect(button).toBeInTheDocument();
        expect(button).toBeVisible();

        expect(screen.getByRole('button', {name: /0/i}));

        // Simulating Button Clicking
        await user.click(button);

        expect(screen.getByRole('button', {name: /1/i})).toBeVisible();

    });

    it('should redirect the user when the URL is clicked', () => {
        
    });

});