package com.versioncontrol.repositoryaccess.api.data;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
This class holds data about the Repository information
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryDetail {

  private String name;

  private String html_url;

  private String description;

  private LocalDateTime created_at;

}