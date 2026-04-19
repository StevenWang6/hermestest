package com.hermes.poll.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequest {
    
    @NotNull(message = "请选择一个投票选项")
    private Long optionId;
}