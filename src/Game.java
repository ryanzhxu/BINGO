import java.util.Scanner;
import java.io.IOException;

public class Game {

    int randomNum, guessNum, start, end, trials;
    Interval interval;
    Scanner scanner;

    public Game() throws Exception {
        scanner = new Scanner(System.in);
        interval = new Interval (1, 100);
        this.start = interval.getStart();
        this.end = interval.getEnd();
        this.randomNum = (int) (Math.random() * (end - start + 1) + start);
//        System.out.println(randomNum);
        Go();
    }

    private void Go() throws Exception {
        System.out.println("Welcome to BINGO! A random number has been generated between 1 and 100.");
        while (guessNum != randomNum) {
            trials++;
            try {
                System.out.println("Please enter a guess:");
                Guess();
            } catch (IncorrectAnswerException e) {
                System.out.println("Answer is incorrect. Please try again.");
            } catch (OutOfBoundaryException e) {
                System.out.println("Answer is out of possible boundary. Please try again.");
            } catch (InvalidInputException e) {
                System.out.println("Answer is invalid. Please try again.");
            }
        }
        System.out.println("Thanks for playing BINGO! See you next time!");
    }

    private void Guess() throws Exception {
        if (scanner.hasNextInt()) {
            guessNum = scanner.nextInt();
            if (interval.withinBoundary(guessNum)) {
                if (guessNum != randomNum) {
                    interval.updateInterval(guessNum, randomNum);
                    interval.printInterval();
                    throw new IncorrectAnswerException();
                } else {
                    System.out.println("BINGO! The secret number is " + guessNum + ". And it took you " + trials + " trial(s).");
                    guessNum = randomNum;
                }
            } else throw new OutOfBoundaryException();
        } else throw new InvalidInputException();
    }



}
