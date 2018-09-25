package com.ibeetl.admin.console.web.vo;

import com.google.common.collect.Lists;
import com.ibeetl.admin.console.service.GroupService;
import com.ibeetl.admin.core.entity.CoreUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {
    private Long id;
    private String username;
    private String nickName;
    private List<String> group;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private String phone;
    private String email;

    public static UserVO transToVO(CoreUser user) {
        List<String> group = Lists.newLinkedList();
        return new UserVO(user.getId(),
                user.getCode(),
                user.getName(),
                group, Integer.parseInt(user.getState()),
                user.getCreateTime(),
                user.getUpdateTime(),
                user.getPhone(),
                user.getEmail());
    }
}
