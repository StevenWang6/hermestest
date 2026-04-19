package com.hermes.poll.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollViewModel {
    
    private Long id;
    private String title;
    private String description;
    private String createdAt;
    private Boolean isActive;
    private int optionCount;
    private int totalVotes;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionStat {
        private Long id;
        private String text;
        private int voteCount;
        private double percentage;
    }
    
    private List<OptionStat> optionStats;
    private boolean hasVoted;
}