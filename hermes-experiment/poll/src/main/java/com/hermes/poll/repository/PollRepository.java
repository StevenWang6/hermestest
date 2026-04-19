package com.hermes.poll.repository;

import com.hermes.poll.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    
    List<Poll> findByIsActiveTrueOrderByCreatedAtDesc();
    
    List<Poll> findAllByOrderByCreatedAtDesc();
    
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.poll.id = :pollId")
    Long countVotesByPollId(@Param("pollId") Long pollId);
    
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.option.id = :optionId")
    Long countVotesByOptionId(@Param("optionId") Long optionId);
}