package ap.examen.eightball.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ap.examen.eightball.redis.RedisService;

@Controller
public class EightBallController
{
    @Autowired
    private RedisService service;

    ArrayList<String> questions;
    ArrayList<String> allAnswers;
    ArrayList<String> answers;
    HashSet<String> chosenAnswers;

    public EightBallController()
    {
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
        questions = new ArrayList<String>();
        allAnswers = new ArrayList<String>();

        for (String s : this.service.keys("question:*"))
        {
            String[] QA = this.service.getKey(s).split("-");

            questions.add(QA[0]);
            allAnswers.add(QA[1]);
        }

        model.addAttribute("questions", questions);
        model.addAttribute("answers", allAnswers);

        return "answers";
    }

    @PostMapping("/")
    public String getQuestion(@RequestParam("question") String question)
    {
        int randomNumber = getRandomNumber();

        // Antwoord werd reeds gebruikt. Negeer.
        if (chosenAnswers.contains( answers.get(randomNumber) ))
            return "redirect:questions";

        if (this.service.exists("questioncount"))
        {
            this.service.incr("questioncount");
        }
        else
        {
            this.service.setKey("questioncount", "1");
        }

        // Sla de vraag en antwoord op in Redis.
        this.service.setKey(String.format("question:%s", this.service.getKey("questioncount")), question + "-" + answers.get(randomNumber));

        // Markeer antwoordt als gebruikt.
        chosenAnswers.add( answers.get(randomNumber) );
    
        return "redirect:questions";
    }

    private static int getRandomNumber()
    {
        final int MIN = 0;
        final int MAX = 19;

		Random r = new Random();
		return r.nextInt((MAX - MIN) + 1) + MIN;
	}
}