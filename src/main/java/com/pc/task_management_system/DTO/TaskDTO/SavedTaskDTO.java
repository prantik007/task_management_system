package com.pc.task_management_system.DTO.TaskDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavedTaskDTO {
    private long id;
    private String name;
    private long projectId;
}
