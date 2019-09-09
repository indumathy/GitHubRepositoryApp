package com.versioncontrol.repositoryaccess.service;

import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities.Comment;
import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities.PullRequest;
import com.versioncontrol.repositoryaccess.api.data.RepositoryDetail;
import com.versioncontrol.repositoryaccess.service.github.GitHubRemoteServiceCall;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoteServiceCallFacade {

  private final GitHubRemoteServiceCall gitHubRemoteServiceCall;

  // GitHub

  public Optional<RepositoryDetail[]> fetchRepoList(String userName) {
    return gitHubRemoteServiceCall.fetchRepoList(userName);
  }


  public Optional<PullRequest[]> fetchpullRequest(String reponame, String user) {
    return gitHubRemoteServiceCall.fetchpullRequest(reponame, user);
  }

  public Optional<Comment[]> fetchCommentsUrl(String reponame, String user) {
    return gitHubRemoteServiceCall.fetchCommentsUrl(reponame, user);
  }
}
