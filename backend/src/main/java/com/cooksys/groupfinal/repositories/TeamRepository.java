package com.cooksys.groupfinal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.groupfinal.entities.Announcement;
import com.cooksys.groupfinal.entities.Team;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

Optional<Team> findByIdAndDeletedFalse(Long id);

Optional<Team> findById(Long id);
}