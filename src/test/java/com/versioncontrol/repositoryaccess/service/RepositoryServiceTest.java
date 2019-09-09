package com.versioncontrol.repositoryaccess.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.when;

import com.versioncontrol.repositoryaccess.api.data.RepoActivity;
import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities;
import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities.Comment;
import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities.PullRequest;
import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities.RepositoryActivity;
import com.versioncontrol.repositoryaccess.api.data.RepositoryDetail;
import com.versioncontrol.repositoryaccess.api.data.UserLinkedToRepository;
import com.versioncontrol.repositoryaccess.common.RepoConstants;
import com.versioncontrol.repositoryaccess.common.exception.NotFoundException;
import java.util.Optional;
import mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class RepositoryServiceTest {

  @Mock
  private RemoteServiceCallFacade remoteServiceCallFacade;
  @InjectMocks
  private RepositoryService repositoryService;


  @Nested
  @DisplayName("Fetch repo list")
  class fetchUserRepoList {

    RepositoryDetail[] repositoryDetails = new RepositoryDetail[2];
    Optional<RepositoryDetail[]> optionalRepositoryDetails;
    String user = "Indu";

    @Nested
    @DisplayName("with invalid inputs")
    class ErrorCases {

      @Test
      @DisplayName("should return error if the invalid user is given")
      void should_return_error_if_the_invalid_user_is_given() {
        String invalidUser = "  ";
        assertThatThrownBy(
            () -> repositoryService.searchUserRepoList(invalidUser))
            .isInstanceOf(NotFoundException.class)

            .hasMessage(RepoConstants.ERROR_TIPS_NOT_FOUND);
      }

      @Test
      @DisplayName("should return error if no repo list exist for given user ")
      void should_return_error_if_no_repo_list_exist_for_given_user() {
        String invalidUser = "userNotExist";
        UserLinkedToRepository result = repositoryService.searchUserRepoList(invalidUser);

        assertSoftly(softly -> {
              softly.assertThat(result.getRepositoryDetails()).isEmpty();
              softly.assertAll();
            }
        );
      }
    }

    @Nested
    @DisplayName("with valid inputs")
    class NominalCases {

      @BeforeEach
      void setUp() {
        repositoryDetails[0] = RepositoryDetail.builder().name("repo1").html_url("http://api.github/repo1")
            .description("unit test").build();
        repositoryDetails[1] = RepositoryDetail.builder().name("repo2").html_url("http://api.github/repo2")
            .description("unit test - 2").build();
        optionalRepositoryDetails = Optional.of(repositoryDetails);

        when(remoteServiceCallFacade.fetchRepoList(user)).thenReturn(optionalRepositoryDetails);

      }

      @Test
      @DisplayName("should return list of Repo for the given user")
      void should_return_list_of_repo_for_given_user() {

        UserLinkedToRepository result = repositoryService.searchUserRepoList(user);

        assertSoftly(softly -> {
              softly.assertThat(result.getRepositoryDetails().get(0))
                  .isEqualToComparingFieldByField(repositoryDetails[0]);
              softly.assertThat(result.getRepositoryDetails().get(1))
                  .isEqualToComparingFieldByField(repositoryDetails[1]);
              softly.assertAll();
            }
        );
      }
    }
  }

  @Nested
  @DisplayName("Fetch the repo activity details ")
  class fetchRepoActivityDetails {

    String user = "Indu";
    String repoName = "repo1";
    PullRequest[] pullRequests = new PullRequest[4];
    Comment[] comments = new Comment[2];
    Optional<PullRequest[]> pullRequestList;
    Optional<Comment[]> commentList;

    @Nested
    @DisplayName("with valid inputs")
    class NominalCases {

      @BeforeEach
      void setUp() {
        pullRequests[0] = PullRequest.builder().state("open").build();
        pullRequests[1] = PullRequest.builder().state("open").build();
        pullRequests[2] = PullRequest.builder().state("closed").build();
        pullRequests[3] = PullRequest.builder().state("closed").build();
        pullRequestList = Optional.of(pullRequests);

        when(remoteServiceCallFacade.fetchpullRequest(repoName, user)).thenReturn(pullRequestList);

        comments[0] = Comment.builder().body("msg1").build();
        comments[1] = Comment.builder().body("msg1").build();
        commentList = Optional.of(comments);
        when(remoteServiceCallFacade.fetchCommentsUrl(repoName, user)).thenReturn(commentList);

      }

      @Test
      @DisplayName("should return list of Repo for the given user")
      void should_return_list_of_repo_for_given_user() {
        RepositoryActivity repositoryActivity1 = RepositoryActivity.builder()
            .activity(RepoActivity.NUM_PR_OPEN.getDesc()).status(String.valueOf(2))
            .build();
        RepositoryActivity repositoryActivity2 = RepositoryActivity.builder()
            .activity(RepoActivity.NUM_PR_CLOSED_TODAY.getDesc()).status(String.valueOf(2))
            .build();
        RepositoryActivity repositoryActivity3 = RepositoryActivity.builder()
            .activity(RepoActivity.NUM_COMMENTS_OPEN_PR.getDesc()).status(String.valueOf(2))
            .build();
        RepositoryActivities result = repositoryService.fetchRepositoryActivities(repoName, user);

        assertSoftly(softly -> {
              softly.assertThat(result.getRepositoryActivities().get(0))
                  .isEqualToComparingFieldByField(repositoryActivity1);
              softly.assertThat(result.getRepositoryActivities().get(1))
                  .isEqualToComparingFieldByField(repositoryActivity2);
              softly.assertThat(result.getRepositoryActivities().get(2))
                  .isEqualToComparingFieldByField(repositoryActivity3);
              softly.assertAll();
            }
        );
      }
    }
  }
}
