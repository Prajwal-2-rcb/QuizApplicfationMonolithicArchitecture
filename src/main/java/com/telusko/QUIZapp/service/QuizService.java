package com.telusko.QUIZapp.service;

import com.telusko.QUIZapp.model.Question;
import com.telusko.QUIZapp.model.QuestionWrapper;
import com.telusko.QUIZapp.model.Quiz;
import com.telusko.QUIZapp.model.Response;
import com.telusko.QUIZapp.repo.QuestionDao;
import com.telusko.QUIZapp.repo.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);

        if (questions.size() < numQ) {
            return ResponseEntity.ok("Not enough questions in the database for category: " + category);
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        // ✅ Return the newly created quiz ID in response
        return ResponseEntity.ok("Quiz created successfully with id: " + quiz.getId());
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz=quizDao.findAllById(id);
        List<Question> questionFromDB=quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser=new ArrayList<>();
        for(Question q:questionFromDB)
        {
            QuestionWrapper qw=new QuestionWrapper(q.getId(),
                    q.getQuestionTitle(),q.getOption1(),
                    q.getOption2(),
                    q.getOption3(),
                    q.getOption4());
            questionsForUser.add(qw);
        }
        return ResponseEntity.ok(questionsForUser);

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz=quizDao.findAllById(id).get();
        List<Question> questions=quiz.getQuestions();
        int right=0;
        int i=0;
        for(Response response:responses)
        {
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;
            i++;
        }
        return ResponseEntity.ok(right);
    }
}
