package com.ibeetl.admin.console.web.vo;

import com.ibeetl.admin.core.entity.CoreGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.assertj.core.util.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lwen on 2018/9/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupVO implements Serializable {
    private long id;
    private String name;
    private String description;
    private List<GroupVO> children;

    public static GroupVO transToGroupVO(CoreGroup coreGroup) {
        return new GroupVO(coreGroup.getId(),
                coreGroup.getName(),
                coreGroup.getDescription(),
                Lists.newArrayList());
    }
}
