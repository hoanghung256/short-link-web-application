package com.example.shortlinkapplication.repository;

import com.example.shortlinkapplication.entity.Project;
import com.example.shortlinkapplication.entity.Url;
import com.example.shortlinkapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

  List<Project> findProjectByUserID(User userID);

  Project findByProjectID(Integer projectID);
}
