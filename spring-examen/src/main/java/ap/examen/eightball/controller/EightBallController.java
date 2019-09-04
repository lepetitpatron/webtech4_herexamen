package ap.examen.eightball.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EightBallController
{
    ArrayList<String> questions;
    ArrayList<String> answers;
    HashSet<String> chosenAnswers;

    public EightBallController()
    {
        questions = new ArrayList<String>();
        answers = new ArrayList<String>();
        chosenAnswers = new HashSet<String>();

        answers.add("It is certain.");
        answers.add("It is decidedly so.");
        answers.add("Without a doubt.");
        answers.add("Yes - definitely.");
        answers.add("You may rely on it.");
        answers.add("As I see it, yes.");
        answers.add("Most likely.");
        answers.add("Outlook good.");
        answers.add("Yes.");
        answers.add("Signs point to yes.");
        answers.add("Reply hazy, try again.");
        answers.add("Ask again later.");
        answers.add("Better not tell you now.");
        answers.add("Cannot predict now.");
        answers.add("Concentrate and ask again.");
        answers.add("Don't count on it.");
        answers.add("My reply is no.");
        answers.add("My sources say no.");
        answers.add("Outlook not so good.");
        answers.add("Very doubtful.");
    }

    @GetMapping("/")
    public String index()
    {
        return "ask";
    }

    @GetMapping("/questions")
    public String showQuestions(Model model)
    {
        ArrayList<String> allAnswers = new ArrayList<String>();

        chosenAnswers.forEach(a -> allAnswers.add(a));

        model.addAttribute("questions", questions);
        model.addAttribute("answers", allAnswers);

        return "answers";
    }

    @PostMapping("/")
    public String getQuestion(@RequestParam("question") String question)
    {
        int randomNumber = getRandomNumber();

        // Negeer
        if (chosenAnswers.contains( answers.get(randomNumber) ))
            return "redirect:questions";

        // Markeer antwoordt als gebruikt & Voeg vraag toe
        chosenAnswers.add( answers.get(randomNumber) );
        questions.add(question);

        return "redirect:questions";
    }

    private static int getRandomNumber()
    {
        final int MIN = 0;
        final int MAX = 24;

		Random r = new Random();
		return r.nextInt((MAX - MIN) + 1) + MIN;
	}
}