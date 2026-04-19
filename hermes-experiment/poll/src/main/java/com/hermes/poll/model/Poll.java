package com.hermes.poll.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "polls")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Poll {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    // Relationships
    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PollOption> options = new ArrayList<>();
    
    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (isActive == null) {
            isActive = true;
        }
    }
    
    // Helper methods
    public int getTotalVotes() {
        return votes != null ? votes.size() : 0;
    }
    
    public void addOption(PollOption option) {
        options.add(option);
        option.setPoll(this);
    }
    
    public void removeOption(PollOption option) {
        options.remove(option);
        option.setPoll(null);
    }
}