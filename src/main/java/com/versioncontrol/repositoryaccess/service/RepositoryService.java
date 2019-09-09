package com.versioncontrol.repositoryaccess.service;

import static java.util.Collections.emptyList;

import com.versioncontrol.repositoryaccess.api.data.RepoActivity;
import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities;
import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities.Comment;
import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities.PullRequest;
import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities.RepositoryActivity;
import com.versioncontrol.repositoryaccess.api.data.RepositoryDetail;
import com.versioncontrol.repositoryaccess.api.data.UserLinkedToRepository;
import com.versioncontrol.repositoryaccess.common.RepoConstants;
import com.versioncontrol.repositoryaccess.common.exception.NotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService {

  @Autowired
  private RemoteServiceCallFacade remoteServiceCallFacade;

  public UserLinkedToRepository searchUserRepoList(String user) {

    validateUserName(user);

    List<RepositoryDetail> repositoryDetailList = populateRepositoryList(user);

    return UserLinkedToRepository.builder().repositoryDetails(repositoryDetailList).userName(user).build();

  }

  private List<RepositoryDetail> populateRepositoryList(String user) {

    Optional<RepositoryDetail[]> repositoryDetails = remoteServiceCallFacade.fetchRepoList(user);

    return (repositoryDetails.isPresent() ? Arrays.asList(repositoryDetails.get()) : emptyList());
  }

  private void validateUserName(String user) {

    if (!StringUtils.isNotBlank(user)) {
      throw new NotFoundException(RepoConstants.INVALID_REQUEST, RepoConstants.ERROR_TIPS_NOT_FOUND);
    }

  }

  public RepositoryActivities fetchRepositoryActivities(String repoName, String user) {

    List<PullRequest> repositoryDetailList = getPullRequestList(repoName, user);

    List<RepositoryActivity> repositoryActivities = Arrays.asList(
        RepositoryActivity.builder()
            .activity(RepoActivity.NUM_PR_OPEN.getDesc())
            .status(String.valueOf(getOpenPullRequestCount(repositoryDetailList))).build(),

        RepositoryActivity.builder()
            .activity(RepoActivity.NUM_PR_CLOSED_TODAY.getDesc())
            .status(String.valueOf(getClosedPullRequestCount(repositoryDetailList))).build(),

        RepositoryActivity.builder()
            .activity(RepoActivity.NUM_COMMENTS_OPEN_PR.getDesc())
            .status(String.valueOf(getCommentsCount(repoName, user))).build());

    return RepositoryActivities.builder().repositoryActivities(repositoryActivities).build();
  }

  private Long getCommentsCount(String reponame, String user) {
    Optional<Comment[]> comments = remoteServiceCallFacade.fetchCommentsUrl(reponame, user);
    return Long.valueOf((comments.isPresent() ? Arrays.asList(comments.get()).size() : 0));
  }

  private Long getClosedPullRequestCount(List<PullRequest> repositoryDetailList) {
    return repositoryDetailList.stream().filter(pullRequest -> (pullRequest.getState().equals("closed")))
          .count();
  }

  private Long getOpenPullRequestCount(List<PullRequest> repositoryDetailList) {
    return repositoryDetailList.stream().filter(pullRequest -> pullRequest.getState().equals("open"))
          .count();
  }

  private List<PullRequest> getPullRequestList(String reponame, String user) {
    Optional<PullRequest[]> pullRequests = remoteServiceCallFacade.fetchpullRequest(reponame, user);
    return (pullRequests.isPresent() ? Arrays.asList(pullRequests.get()) : emptyList());
  }
}
