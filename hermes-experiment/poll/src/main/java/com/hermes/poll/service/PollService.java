package com.hermes.poll.service;

import com.hermes.poll.model.Poll;
import com.hermes.poll.model.PollOption;
import com.hermes.poll.model.Vote;
import com.hermes.poll.repository.PollRepository;
import com.hermes.poll.repository.PollOptionRepository;
import com.hermes.poll.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PollService {
    
    private final PollRepository pollRepository;
    private final PollOptionRepository pollOptionRepository;
    private final VoteRepository voteRepository;
    
    public List<Poll> getAllActivePolls() {
        return pollRepository.findByIsActiveTrueOrderByCreatedAtDesc();
    }
    
    public List<Poll> getAllPolls() {
        return pollRepository.findAllByOrderByCreatedAtDesc();
    }
    
    public Poll getPollById(Long id) {
        return pollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poll not found with id: " + id));
    }
    
    @Transactional
    public Poll createPoll(Poll poll, List<String> options) {
        // Save poll first
        Poll savedPoll = pollRepository.save(poll);
        
        // Add options
        for (String optionText : options) {
            PollOption option = new PollOption();
            option.setText(optionText);
            option.setPoll(savedPoll);
            savedPoll.addOption(option);
        }
        
        return pollRepository.save(savedPoll);
    }
    
    @Transactional
    public Vote vote(Long pollId, Long optionId, HttpServletRequest request) {
        Poll poll = getPollById(pollId);
        
        if (!poll.getIsActive()) {
            throw new RuntimeException("Poll is not active");
        }
        
        // Check if user has already voted (by IP)
        String voterIp = getClientIp(request);
        if (voteRepository.existsByPollIdAndVoterIp(pollId, voterIp)) {
            throw new RuntimeException("You have already voted in this poll");
        }
        
        // Find the option
        PollOption option = poll.getOptions().stream()
                .filter(opt -> opt.getId().equals(optionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid option"));
        
        // Create vote
        Vote vote = new Vote();
        vote.setPoll(poll);
        vote.setOption(option);
        vote.setVoterIp(voterIp);
        vote.setVotedAt(LocalDateTime.now());
        
        return voteRepository.save(vote);
    }
    
    @Transactional
    public Poll togglePollStatus(Long pollId) {
        Poll poll = getPollById(pollId);
        poll.setIsActive(!poll.getIsActive());
        return pollRepository.save(poll);
    }
    
    @Transactional
    public void deletePoll(Long pollId) {
        // Delete votes first
        voteRepository.deleteByPollId(pollId);
        pollRepository.deleteById(pollId);
    }
    
    public Map<Long, Integer> getVoteCountsByPollId(Long pollId) {
        Poll poll = getPollById(pollId);
        return poll.getOptions().stream()
                .collect(Collectors.toMap(
                    PollOption::getId,
                    PollOption::getVoteCount
                ));
    }
    
    public int getTotalVotesForPoll(Long pollId) {
        return getPollById(pollId).getTotalVotes();
    }
    
    public boolean hasUserVoted(Long pollId, HttpServletRequest request) {
        String voterIp = getClientIp(request);
        return voteRepository.existsByPollIdAndVoterIp(pollId, voterIp);
    }
    
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    // Initialize sample data
    @Transactional
    public void initializeSampleData() {
        if (pollRepository.count() == 0) {
            // Sample poll 1
            Poll poll1 = new Poll();
            poll1.setTitle("你最喜欢的编程语言是什么？");
            poll1.setDescription("选择你最喜欢或最常用的编程语言");
            poll1.setCreatedAt(LocalDateTime.now());
            poll1.setIsActive(true);
            
            poll1 = pollRepository.save(poll1);
            
            String[] options1 = {"Python", "JavaScript", "Java", "C++", "Go", "Rust"};
            for (String option : options1) {
                PollOption opt = new PollOption();
                opt.setText(option);
                opt.setPoll(poll1);
                pollOptionRepository.save(opt);
            }
            
            // Sample poll 2
            Poll poll2 = new Poll();
            poll2.setTitle("你更喜欢哪种开发方式？");
            poll2.setDescription("选择你偏好的软件开发方式");
            poll2.setCreatedAt(LocalDateTime.now());
            poll2.setIsActive(true);
            
            poll2 = pollRepository.save(poll2);
            
            String[] options2 = {"敏捷开发", "瀑布模型", "DevOps", "测试驱动开发(TDD)", "行为驱动开发(BDD)"};
            for (String option : options2) {
                PollOption opt = new PollOption();
                opt.setText(option);
                opt.setPoll(poll2);
                pollOptionRepository.save(opt);
            }
        }
    }
}