package ap.examen.eightball.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EightBallController
{
    ArrayList<String> answers;

    public EightBallController()
    {
        answers = new ArrayList<String>();

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
    @ResponseBody
    public String index()
    {
        return answers.toString();
    }
}