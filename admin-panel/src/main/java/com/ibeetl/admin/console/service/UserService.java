package com.ibeetl.admin.console.service;

import com.ibeetl.admin.console.dao.UserDao;
import com.ibeetl.admin.console.domain.ResultDO;
import com.ibeetl.admin.console.exception.DeletedException;
import com.ibeetl.admin.console.exception.NoResourceException;
import com.ibeetl.admin.console.template.ServiceTemplate;
import com.ibeetl.admin.console.web.dto.UserExcelExportData;
import com.ibeetl.admin.console.web.query.UserRoleQuery;
import com.ibeetl.admin.core.conf.PasswordConfig.PasswordEncryptService;
import com.ibeetl.admin.core.entity.CoreDict;
import com.ibeetl.admin.core.entity.CoreUser;
import com.ibeetl.admin.core.entity.CoreUserRole;
import com.ibeetl.admin.core.file.FileService;
import com.ibeetl.admin.core.rbac.tree.OrgItem;
import com.ibeetl.admin.core.service.BaseService;
import com.ibeetl.admin.core.service.CoreDictService;
import com.ibeetl.admin.core.service.CorePlatformService;
import com.ibeetl.admin.core.util.PlatformException;
import com.ibeetl.admin.core.util.enums.CoreDictType;
import com.ibeetl.admin.core.util.enums.DelFlagEnum;
import com.ibeetl.admin.core.util.enums.GeneralStateEnum;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.engine.PageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService extends BaseService<CoreUser> {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDao userDao;
    @Autowired
    FileService fileService;
    @Autowired
    PasswordEncryptService passwordEncryptService;
    @Autowired
    CoreDictService dictService;
    @Autowired
    CorePlatformService platformService;
    @Autowired
    GroupService groupService;

    /**
     * 根据条件查询
     *
     * @param query
     */
    public ResultDO<List<CoreUser>> queryByCondition(PageQuery<CoreUser> query) {
        return new ServiceTemplate<List<CoreUser>>(logger) {

            @Override
            protected List<CoreUser> invoke() {
//                List<CoreUser> pageQueryList = userDao.queryByCondition(query).getList();
                return userDao.queryByCondition(query).getList();
            }

            @Override
            public LogInfo logInfo() {
                return new ServiceTemplate.LogInfo("[ " + this.getClass().getName() + "] --> error occurred ! ");
            }
        }.execute();
    }

    /**
     * 插入一条用户数据
     *
     * @param user
     */
    public void saveUser(CoreUser user) {
        CoreUser query = new CoreUser();
        query.setUsername(user.getUsername());
        CoreUser dbUser = userDao.templateOne(query);
        if (dbUser != null) {
            throw new PlatformException("保存用户信息失败,用户已经存在");
        }
        user.setCreateTime(new Date());
        user.setStatus(GeneralStateEnum.ENABLE.getValue());
        user.setPassword(passwordEncryptService.password(user.getPassword()));
        user.setDelFlag(DelFlagEnum.NORMAL.getValue());
        userDao.insert(user, true);
        if (StringUtils.isNotEmpty(user.getAttachmentId())) {
            //更新附件详细信息,关联到这个用户
            fileService.updateFile(user.getAttachmentId(), User.class.getSimpleName(), String.valueOf(user.getId()));
        }


    }

    /**
     * 根据用户id查询一条数据
     *
     * @param userId
     */
    public CoreUser queryUserById(Long userId) {
        return userDao.unique(userId);
    }

    /**
     * 更新用户 只更新不为空的字段
     *
     * @param user
     * @return
     */
    public int updateSysUser(CoreUser user) {
        return userDao.updateTemplateById(user);
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     */
    public void delSysUser(Long userId) {
        CoreUser user = queryUserById(userId);
        if (user == null) {
            throw new NoResourceException("用户不存在!");
        }
        if (user.getDelFlag() == DelFlagEnum.DELETED.getValue()) {
            throw new DeletedException("用户已被删除!");
        }
        user = new CoreUser();
        user.setId(userId);
        user.setDelFlag(DelFlagEnum.DELETED.getValue());
        userDao.updateTemplateById(user);
    }

    /**
     * 批量删除用户 <br/>
     * 软删除 标记用户删除状态
     *
     * @param userIds 用户id
     */
    public void batchDelSysUser(List<Long> userIds) {
        try {
            userDao.batchDelUserByIds(userIds);
        } catch (Exception e) {
            throw new PlatformException("批量删除用户失败", e);
        }
    }

    /**
     * 批量停用用户 <br/>
     * 标记用户状态 停用
     *
     * @param userIds 用户id
     */
    public void batchUpdateUserState(List<Long> userIds, GeneralStateEnum stateEnum) {
        userDao.batchUpdateUserState(userIds, stateEnum);
    }

    /**
     * 重置用户密码
     *
     * @param id
     * @param password
     */
    public int resetPassword(Long id, String password) {
        CoreUser user = new CoreUser();
        user.setId(id);
        user.setPassword(passwordEncryptService.password(password));
        user.setUpdateTime(new Date());
        return userDao.updateTemplateById(user);
    }

    public List<CoreUserRole> getUserRoles(UserRoleQuery roleQuery) {
        return userDao.queryUserRole(roleQuery.getUserId(), roleQuery.getOrgId(), roleQuery.getRoleId());
    }

    public void deleteUserRoles(List<Long> ids) {
        // 考虑到这个操作较少使用，就不做批处理优化了
        for (Long id : ids) {
            sqlManager.deleteById(CoreUserRole.class, id);
        }

    }

    public void saveUserRole(CoreUserRole userRole) {

        long queryCount = sqlManager.templateCount(userRole);

        if (queryCount > 0) {
            throw new PlatformException("已存在用户角色关系");
        }
        sqlManager.insert(userRole);
    }

    public List<UserExcelExportData> queryExcel(PageQuery<CoreUser> query) {
        PageQuery<CoreUser> pageQuery = userDao.queryByCondition(query);
        List<CoreUser> list = pageQuery.getList();
        OrgItem orgRoot = platformService.buildOrg();
        List<UserExcelExportData> items = new ArrayList<>();
        for (CoreUser user : list) {
            UserExcelExportData userItem = new UserExcelExportData();
            userItem.setCode(user.getUsername());
            userItem.setId(user.getId());
            userItem.setName(user.getNickname());
            CoreDict dict = dictService.findCoreDict(CoreDictType.USER_STATE, user.getStatus());
            userItem.setStateText(dict.getName());

            if (StringUtils.isNotEmpty(user.getJobType1())) {
                dict = dictService.findCoreDict("job_type", user.getJobType1());
                userItem.setJobType1Text(dict.getName());
            }

            String orgName = orgRoot.findChild(user.getOrgId()).getName();
            userItem.setOrgText(orgName);
            items.add(userItem);

        }
        return items;

    }

}
