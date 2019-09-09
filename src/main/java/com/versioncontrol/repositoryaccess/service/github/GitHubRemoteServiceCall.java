package com.versioncontrol.repositoryaccess.service.github;

import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities.Comment;
import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities.PullRequest;
import com.versioncontrol.repositoryaccess.api.data.RepositoryDetail;
import java.util.Optional;


public interface GitHubRemoteServiceCall {

  Optional<RepositoryDetail[]> fetchRepoList(String userName);

  Optional<PullRequest[]> fetchpullRequest(String reponame, String user);

  Optional<Comment[]> fetchCommentsUrl(String reponame, String user);
}
