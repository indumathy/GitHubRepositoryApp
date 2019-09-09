package com.versioncontrol.repositoryaccess.api.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
/*
Enum class to hold values of Repository activity description
 */
@Getter
@AllArgsConstructor
public enum RepoActivity {

  NUM_PR_OPEN("Number of Open PR"), NUM_PR_CLOSED_TODAY("Number of PRs Closed in current Days"), NUM_COMMENTS_OPEN_PR(
      "Number Of Comments in Open PR") , NUM_COMMITS_30_DAYS("Number of Commits in last 30 days"),
  NUM_COMMITERS_30_DAYS("Number of Committers in last 30 days") , TOP_10_COMMITTERS_30_DAYS("Top 10 committers in last 30 days");

  private String desc;
}
