package org.example.portfoliospring1.contoller;

import lombok.RequiredArgsConstructor;
import org.example.portfoliospring1.contoller.response.BaseResponse;
import org.example.portfoliospring1.domain.dto.ProjectDto;
import org.example.portfoliospring1.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/get-projects")
    public BaseResponse<List<ProjectDto>> getProjects() {
        return new BaseResponse<>(projectService.getProjects());
    }
}
