package com.ibeetl.admin.core.entity;
import lombok.Data;
import java.util.Objects;

@Data
public class CoreGroup {

  private long id;
  private String name;
  private String description;
  private long pid;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CoreGroup coreGroup = (CoreGroup) o;
    return id == coreGroup.id &&
            pid == coreGroup.pid &&
            Objects.equals(name, coreGroup.name) &&
            Objects.equals(description, coreGroup.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, pid);
  }
}
