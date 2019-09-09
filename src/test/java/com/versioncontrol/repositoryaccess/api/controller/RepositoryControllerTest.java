package com.versioncontrol.repositoryaccess.api.controller;

import static com.versioncontrol.repositoryaccess.api.controller.RepositoryController.Urls.API_VERSION;
import static com.versioncontrol.repositoryaccess.api.controller.RepositoryController.Urls.GET_REPO_DETAILS;
import static com.versioncontrol.repositoryaccess.api.controller.RepositoryController.Urls.SEARCH_USER_REPO_LIST;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.versioncontrol.repositoryaccess.api.data.ErrorDetail;
import com.versioncontrol.repositoryaccess.api.data.RepoActivity;
import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities;
import com.versioncontrol.repositoryaccess.api.data.RepositoryActivities.RepositoryActivity;
import com.versioncontrol.repositoryaccess.api.data.RepositoryDetail;
import com.versioncontrol.repositoryaccess.api.data.UserLinkedToRepository;
import com.versioncontrol.repositoryaccess.common.RepoConstants;
import com.versioncontrol.repositoryaccess.common.exception.NotFoundException;
import com.versioncontrol.repositoryaccess.service.RepositoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RepositoryController.class)
public class RepositoryControllerTest {


  @Autowired
  private MockMvc mvc;
  @Autowired
  private ObjectMapper jsonMapper;
  @MockBean
  private RepositoryService repositoryService;

  @Nested
  @DisplayName("search list of repositories for Input user")
  class searchUserRepo {

    String user = "Indu";

    @Nested
    @DisplayName("with invalid inputs")
    class ErrorCases {

      @Test
      @DisplayName("should return error if input user conatins only empty string")
      void should_return_error_if_input_user_conatins_only_empty_string() throws Exception {

        String invalidUser = "    ";

        doThrow(new NotFoundException(RepoConstants.INVALID_REQUEST, RepoConstants.ERROR_TIPS_NOT_FOUND))
            .when(repositoryService)
            .searchUserRepoList(invalidUser);
        ErrorDetail errorInfo = ErrorDetail.builder().errorCode(RepoConstants.INVALID_REQUEST)
            .errorMessage(RepoConstants.ERROR_TIPS_NOT_FOUND).errorLevel(RepoConstants.INFO)
            .errorType(RepoConstants.FUNCTIONAL).documentationUrl(RepoConstants.ERROR_DOC_URL)
            .tips(RepoConstants.ERROR_TIPS_NOT_FOUND).build();

        mvc.perform(get(API_VERSION + SEARCH_USER_REPO_LIST)
            .param("userName", invalidUser))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(model().attribute("error", errorInfo));
        ;
      }
    }

    @Nested
    @DisplayName("with valid inputs")
    class NominalCases {

      @Test
      @DisplayName("should return list of Repositories for valid user")
      void should_return_list_of_Repositories_for_valid_user() throws Exception {

        UserLinkedToRepository userLinkedToRepository = UserLinkedToRepository.builder().userName(user)
            .repositoryDetails(
                Arrays.asList(RepositoryDetail.builder().name("repo1").html_url("http://api.github/repo1")
                    .description("unit test").build())).build();
        when(repositoryService.searchUserRepoList(user))
            .thenReturn(userLinkedToRepository);

        mvc.perform(get(API_VERSION + SEARCH_USER_REPO_LIST)
            .param("userName", user))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(model().attribute("userRepoList", userLinkedToRepository));
        ;
      }
    }

    @Nested
    @DisplayName("Fetch Repository activity details")
    class fetchRepoDetails {

      String user = "Indu";
      String repoName = "Repo1";

      @Nested
      @DisplayName("with valid inputs")
      class NominalCases {

        @Test
        @DisplayName("should return Repository activity details fot the given user and selected Repo")
        void should_return_repository_activity_details_for_the_given_user_and_selected_repo() throws Exception {

          List<RepositoryActivity> repositoryActivities = Arrays.asList(
              RepositoryActivity.builder().activity(RepoActivity.NUM_PR_OPEN.getDesc()).status(String.valueOf(2))
                  .build(),
              RepositoryActivity.builder().activity(RepoActivity.NUM_PR_CLOSED_TODAY.getDesc())
                  .status(String.valueOf(3))
                  .build(),
              RepositoryActivity.builder().activity(RepoActivity.NUM_COMMENTS_OPEN_PR.getDesc())
                  .status(String.valueOf(4))
                  .build());
          RepositoryActivities repositoryActivitiesObj = RepositoryActivities.builder()
              .repositoryActivities(repositoryActivities).build();
          when(repositoryService.fetchRepositoryActivities(repoName, user))
              .thenReturn(repositoryActivitiesObj);

          mvc.perform(get(API_VERSION + GET_REPO_DETAILS, user, repoName)
              .contentType(APPLICATION_JSON_UTF8))
              .andDo(print())
              .andExpect(status().isOk())
              .andExpect(model().attribute("repositoryActivities", repositoryActivitiesObj))
              .andExpect(model().attribute("reponame", repoName))
              .andExpect(model().attribute("user", user));
          ;
        }
      }
    }
  }
}