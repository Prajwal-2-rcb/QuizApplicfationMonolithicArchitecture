package com.telusko.QUIZapp.repo;

import com.telusko.QUIZapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizDao extends JpaRepository<Quiz,Integer> {
    Optional<Quiz> findAllById(Integer id);
}
