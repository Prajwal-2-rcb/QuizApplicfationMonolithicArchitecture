package com.telusko.QUIZapp.service;

import com.telusko.QUIZapp.model.Question;
import com.telusko.QUIZapp.repo.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;


import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return ResponseEntity.ok("Question Added Successfully");
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        questionDao.deleteById(id);
        return ResponseEntity.ok("Question Deleted Successfully");
    }

    public ResponseEntity<String> updateQuestion(Question question, Integer id) {
        Optional<Question> existingQuestion = questionDao.findById(id);

        if (existingQuestion.isPresent()) {
            Question updatedQuestion = existingQuestion.get();

            // Update the fields you want to modify
            updatedQuestion.setQuestionTitle(question.getQuestionTitle());
            updatedQuestion.setOption1(question.getOption1());
            updatedQuestion.setOption2(question.getOption2());
            updatedQuestion.setOption3(question.getOption3());
            updatedQuestion.setOption4(question.getOption4());
            updatedQuestion.setRightAnswer(question.getRightAnswer());
            updatedQuestion.setDifficultylevel(question.getDifficultylevel());
            updatedQuestion.setCategory(question.getCategory());

            questionDao.save(updatedQuestion);
            return ResponseEntity.ok("Question Updated Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Question with ID " + id + " not found");
        }
    }

}
