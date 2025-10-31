package com.telusko.QUIZapp.controller;

import com.telusko.QUIZapp.model.Question;
import com.telusko.QUIZapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/question")

public class QuestionController {

    @Autowired
   QuestionService questionService;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return  questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<Map<String, Object>> addQuestion(@RequestBody Question question) {
        questionService.addQuestion(question); // assuming it saves successfully

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Question Added Successfully");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/deleteQuestion/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id){
        return questionService.deleteQuestion(id);
    }

    @PutMapping("/updateQuestion/{id}")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question,@PathVariable Integer id){
        return questionService.updateQuestion(question,id);
    }


}
