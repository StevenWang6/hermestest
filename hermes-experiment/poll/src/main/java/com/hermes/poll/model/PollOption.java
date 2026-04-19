package com.hermes.poll.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "poll_options")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollOption {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String text;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;
    
    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();
    
    // Helper methods
    public int getVoteCount() {
        return votes != null ? votes.size() : 0;
    }
    
    public double getPercentage(int totalVotes) {
        if (totalVotes == 0) return 0.0;
        return (getVoteCount() * 100.0) / totalVotes;
    }
}