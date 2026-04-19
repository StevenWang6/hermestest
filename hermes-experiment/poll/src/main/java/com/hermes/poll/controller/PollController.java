package com.hermes.poll.controller;

import com.hermes.poll.dto.*;
import com.hermes.poll.model.Poll;
import com.hermes.poll.model.PollOption;
import com.hermes.poll.service.PollService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PollController {
    
    private final PollService pollService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    @GetMapping("/")
    public String index(Model model) {
        List<Poll> polls = pollService.getAllActivePolls();
        model.addAttribute("polls", polls);
        return "index";
    }
    
    @GetMapping("/poll/{id}")
    public String viewPoll(@PathVariable Long id, Model model, HttpServletRequest request) {
        Poll poll = pollService.getPollById(id);
        boolean hasVoted = pollService.hasUserVoted(id, request);
        
        PollViewModel viewModel = new PollViewModel();
        viewModel.setId(poll.getId());
        viewModel.setTitle(poll.getTitle());
        viewModel.setDescription(poll.getDescription());
        viewModel.setCreatedAt(poll.getCreatedAt().format(DATE_FORMATTER));
        viewModel.setIsActive(poll.getIsActive());
        viewModel.setOptionCount(poll.getOptions().size());
        viewModel.setTotalVotes(poll.getTotalVotes());
        viewModel.setHasVoted(hasVoted);
        
        // Calculate option statistics
        int totalVotes = poll.getTotalVotes();
        List<PollViewModel.OptionStat> optionStats = poll.getOptions().stream()
                .map(option -> {
                    PollViewModel.OptionStat stat = new PollViewModel.OptionStat();
                    stat.setId(option.getId());
                    stat.setText(option.getText());
                    stat.setVoteCount(option.getVoteCount());
                    stat.setPercentage(option.getPercentage(totalVotes));
                    return stat;
                })
                .collect(Collectors.toList());
        
        viewModel.setOptionStats(optionStats);
        model.addAttribute("poll", viewModel);
        
        return "poll";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("createPollRequest", new CreatePollRequest());
        return "create";
    }
    
    @PostMapping("/create")
    public String createPoll(@Valid @ModelAttribute CreatePollRequest request,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "create";
        }
        
        try {
            Poll poll = new Poll();
            poll.setTitle(request.getTitle());
            poll.setDescription(request.getDescription());
            
            Poll createdPoll = pollService.createPoll(poll, request.getOptionsList());
            
            redirectAttributes.addFlashAttribute("success", "投票创建成功！");
            return "redirect:/poll/" + createdPoll.getId();
        } catch (Exception e) {
            model.addAttribute("error", "创建投票失败: " + e.getMessage());
            return "create";
        }
    }
    
    @PostMapping("/poll/{id}/vote")
    @ResponseBody
    public ApiResponse<String> vote(@PathVariable Long id,
                                   @Valid @RequestBody VoteRequest request,
                                   HttpServletRequest httpRequest) {
        try {
            pollService.vote(id, request.getOptionId(), httpRequest);
            return ApiResponse.success("投票成功！", null);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @GetMapping("/admin")
    public String admin(Model model) {
        List<Poll> polls = pollService.getAllPolls();
        model.addAttribute("polls", polls);
        return "admin";
    }
    
    @PostMapping("/admin/poll/{id}/toggle")
    @ResponseBody
    public ApiResponse<Boolean> togglePoll(@PathVariable Long id) {
        try {
            Poll poll = pollService.togglePollStatus(id);
            return ApiResponse.success("投票状态已更新", poll.getIsActive());
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @PostMapping("/admin/poll/{id}/delete")
    @ResponseBody
    public ApiResponse<String> deletePoll(@PathVariable Long id) {
        try {
            pollService.deletePoll(id);
            return ApiResponse.success("投票已删除", null);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @GetMapping("/api/poll/{id}/stats")
    @ResponseBody
    public ApiResponse<PollStatsResponse> getPollStats(@PathVariable Long id) {
        try {
            Poll poll = pollService.getPollById(id);
            
            List<PollStatsResponse.OptionStat> stats = poll.getOptions().stream()
                    .map(option -> new PollStatsResponse.OptionStat(
                            option.getText(),
                            option.getVoteCount()
                    ))
                    .collect(Collectors.toList());
            
            PollStatsResponse response = new PollStatsResponse(
                    poll.getTitle(),
                    poll.getTotalVotes(),
                    stats
            );
            
            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    // Poll stats response DTO
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PollStatsResponse {
        private String pollTitle;
        private int totalVotes;
        private List<OptionStat> stats;
        
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class OptionStat {
            private String option;
            private int votes;
        }
    }
}