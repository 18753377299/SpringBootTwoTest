package com.example.common.activiti.tool;

import com.example.common.activiti.ActivitiUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Api(value="Activiti用户组工具类")
@Component("activitiIdentityTool")
public class ActivitiIdentityTool {

    @ApiOperation(value="创建Activiti用户",notes="act_id_user表")
    public void addUser(){
        User user1 =ActivitiUtils.getIdentityService().newUser("user1");
        user1.setFirstName("张三");
        user1.setLastName("张");

        ActivitiUtils.getIdentityService().saveUser(user1);
    }
    @ApiOperation(value="查询Activiti用户")
    public void queryUser(){
      User user = ActivitiUtils.getIdentityService().createUserQuery().userId("user1").singleResult();
        System.out.println("{}==>"+user.getFirstName());
    }

    @ApiOperation(value="创建Activiti用户组:ACT_ID_GROUP（用户组信息表）",
            notes = "Activiti中的用户组信息相当于权限系统当中的角色，" +
            "用户可以属于多个用户组，用户组也可以包含多个用户，同一个用户组当中的用户具有相同的权限")
    public void addGroup(){
        Group group1 =ActivitiUtils.getIdentityService().newGroup("group1");
        group1.setName("员工组");
        group1.setType("员工组");
        ActivitiUtils.getIdentityService().saveGroup(group1);
    }
    @ApiOperation(value="通过ID查询用户组")
    public void queryGroup(){
        Group group1 =ActivitiUtils.getIdentityService().createGroupQuery()
                .groupId("group1").singleResult();
    }
    @ApiOperation(value="创建Activiti（用户-用户组）关系",notes="ACT_ID_MEMBERSHIP（用户与用户组关系信息表）")
    public void createUserGroupShip(){
        ActivitiUtils.getIdentityService().createMembership("user1","group1");
    }
    @ApiOperation(value="查询Activiti（用户组下的用户）")
    public void queryUserByGroup(){
        List<User> usersInGroup = ActivitiUtils.getIdentityService().createUserQuery()
                .memberOfGroup("group1").list();
        usersInGroup.forEach(user->{
            System.out.println(user.getFirstName());
        });
    }
    @ApiOperation(value="查询Activiti（用户对应的用户组）")
    public void queryGroupByUser(String user){
        List<Group> groupToUser=ActivitiUtils.getIdentityService().createGroupQuery()
                .groupMember(user).list();
        groupToUser.forEach(group -> {
            System.out.println("{}=====>"+group.getName());
        });
    }



}
