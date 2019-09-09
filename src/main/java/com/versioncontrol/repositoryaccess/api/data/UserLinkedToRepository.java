package com.versioncontrol.repositoryaccess.api.data;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLinkedToRepository {

  private String userName;

  private List<RepositoryDetail> repositoryDetails;
}
