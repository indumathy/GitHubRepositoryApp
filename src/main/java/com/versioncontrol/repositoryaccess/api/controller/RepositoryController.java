package com.versioncontrol.repositoryaccess.api.controller;

import static com.versioncontrol.repositoryaccess.api.controller.RepositoryController.Urls.API_VERSION;
import static com.versioncontrol.repositoryaccess.api.controller.RepositoryController.Urls.GET_REPO_DETAILS;
import static com.versioncontrol.repositoryaccess.api.controller.RepositoryController.Urls.SEARCH_USER_REPO_LIST;

import com.versioncontrol.repositoryaccess.service.RepositoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
RepositoryController - HTTP requests are handled for Repository app
 */
@Controller
@AllArgsConstructor
@RequestMapping(value = API_VERSION)
@Slf4j
public class RepositoryController {

  private RepositoryService studentService;

  /*
  This request is for fetching repository list for given user
   */
  @GetMapping(SEARCH_USER_REPO_LIST)
  public String searchUserRepoList(@RequestParam("userName") String userName, Model model) {
    log.info("-searchUserRepoList-");
    model.addAttribute("userRepoList", studentService.searchUserRepoList(userName));
    model.addAttribute("user", userName);
    return "index";
  }

  /*
  This request is to fetch repository activity details for given repo of a user
   */
  @GetMapping(GET_REPO_DETAILS)
  public String getRepoDetails(@PathVariable String reponame, @PathVariable String user, Model model) {
    log.info("-getRepoDetails-");
    model.addAttribute("repositoryActivities", studentService.fetchRepositoryActivities(reponame, user));
    model.addAttribute("reponame", reponame);
    model.addAttribute("user", user);
    return "view-repository";
  }

  static class Urls {

    static final String API_VERSION = "/repository";
    static final String SEARCH_USER_REPO_LIST = "/search";
    static final String GET_REPO_DETAILS = "/user/{user}/repository/{reponame}";

    private Urls() {
      throw new UnsupportedOperationException();
    }
  }
}
