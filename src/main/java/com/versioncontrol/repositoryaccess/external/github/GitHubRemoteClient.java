package com.versioncontrol.repositoryaccess.external.github;

import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities.Comment;
import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities.PullRequest;
import com.versioncontrol.repositoryaccess.api.data.RepositoryDetail;
import com.versioncontrol.repositoryaccess.external.AbstractRemoteClient;
import com.versioncontrol.repositoryaccess.service.github.GitHubRemoteServiceCall;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/*
Calling external client services
 */
@Component
@Slf4j
public class GitHubRemoteClient extends AbstractRemoteClient<GitHubRepoConfiguration> implements
    GitHubRemoteServiceCall {


  public GitHubRemoteClient(GitHubRepoConfiguration configuration) {
    super(configuration);
  }

  /*
  call github api - to fetch repo list
   */
  @Override
  public Optional<RepositoryDetail[]> fetchRepoList(String userName) {
    log.info("-fetchRepoList-");
    HttpEntity<?> headers = initializeHeaders();
    ResponseEntity<RepositoryDetail[]> response = remoteClient
        .exchange(configuration.getSearchUserRepositoriesUrl(), HttpMethod.GET, headers, RepositoryDetail[].class,
            userName);
    return Optional.ofNullable(response.getBody());
  }

  /*
call github api - to fetch pull request
 */
  @Override
  public Optional<PullRequest[]> fetchpullRequest(String reponame, String user) {
    log.info("-fetchpullRequest-");
    HttpEntity<?> headers = initializeHeaders();
    ResponseEntity<PullRequest[]> response = remoteClient
        .exchange(configuration.getFetchPullRequestUrl(), HttpMethod.GET, headers, PullRequest[].class, user, reponame);
    return Optional.ofNullable(response.getBody());
  }

  /*
call github api - to fetch comments details
 */
  @Override
  public Optional<Comment[]> fetchCommentsUrl(String reponame, String user) {
    HttpEntity<?> headers = initializeHeaders();
    ResponseEntity<Comment[]> response = remoteClient
        .exchange(configuration.getFetchCommentsUrl(), HttpMethod.GET, headers, Comment[].class, user, reponame);
    return Optional.ofNullable(response.getBody());
  }

}

