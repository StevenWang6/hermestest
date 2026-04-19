package com.hermes.poll.repository;

import com.hermes.poll.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    
    boolean existsByPollIdAndVoterIp(Long pollId, String voterIp);
    
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.poll.id = :pollId AND v.option.id = :optionId")
    Long countByPollIdAndOptionId(@Param("pollId") Long pollId, @Param("optionId") Long optionId);
    
    List<Vote> findByPollId(Long pollId);
    
    void deleteByPollId(Long pollId);
}