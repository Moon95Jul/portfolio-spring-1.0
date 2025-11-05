package org.example.portfoliospring1.service;

import org.example.portfoliospring1.domain.dto.ProjectDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    public List<ProjectDto> getProjects() {
        List<ProjectDto> projects = new ArrayList<>();

        ProjectDto projectDto1 = new ProjectDto("미니게임 천국","간단한 게임 창작 프로젝트");
        ProjectDto projectDto2 = new ProjectDto("포트폴리오 사이트","나를 소개합니다");
        ProjectDto projectDto3 = new ProjectDto("맛집 탐방","나의 맛집들을 소개합니다");

        projects.add(projectDto1);
        projects.add(projectDto2);
        projects.add(projectDto3);

        return projects;
    }
}
