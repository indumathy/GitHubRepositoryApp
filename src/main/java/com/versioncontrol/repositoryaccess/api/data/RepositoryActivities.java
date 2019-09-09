package com.versioncontrol.repositoryaccess.api.data;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryActivities {

  private List<RepositoryActivity> repositoryActivities;

  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class RepositoryActivity {

    private String activity;
    private String status;

  }

  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class PullRequest {

    private String state;
    private LocalDate closed_at;
  }


  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Commit {

    private String committer;
    private String commit;

  }

  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Comment {

    private String commit_id;
    private String body;

  }

}