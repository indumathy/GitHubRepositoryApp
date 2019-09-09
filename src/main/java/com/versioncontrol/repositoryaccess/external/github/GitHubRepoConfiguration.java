package com.versioncontrol.repositoryaccess.external.github;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
This class loads the info about the configuration mentioned in application.properties
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "api.github")
public class GitHubRepoConfiguration {

  private String searchUserRepositoriesUrl;
  private String fetchPullRequestUrl;
  private String fetchCommentsUrl;

}
