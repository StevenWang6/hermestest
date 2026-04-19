package com.hermes.poll.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePollRequest {
    
    @NotBlank(message = "投票标题不能为空")
    @Size(max = 200, message = "标题不能超过200个字符")
    private String title;
    
    @Size(max = 1000, message = "描述不能超过1000个字符")
    private String description;
    
    @NotBlank(message = "投票选项不能为空")
    private String options; // Newline separated options
    
    public List<String> getOptionsList() {
        return List.of(options.split("\\r?\\n"))
                .stream()
                .map(String::trim)
                .filter(opt -> !opt.isEmpty())
                .toList();
    }
}