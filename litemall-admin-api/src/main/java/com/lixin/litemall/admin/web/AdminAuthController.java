package com.lixin.litemall.admin.web;

import com.lixin.litemall.admin.service.LogHelper;
import com.lixin.litemall.admin.vo.LoginVo;
import com.lixin.litemall.common.api.CommonResult;
import com.lixin.litemall.core.util.JacksonUtil;
import com.lixin.litemall.core.util.ResponseUtil;
import com.lixin.litemall.db.domain.LitemallAdmin;
import com.lixin.litemall.db.service.LitemallAdminService;
import com.lixin.litemall.db.service.LitemallPermissionService;
import com.lixin.litemall.db.service.LitemallRoleService;
import com.lixin.litemall.security.util.JwtTokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/admin/auth")
@Validated
public class AdminAuthController {
    private final Log logger = LogFactory.getLog(AdminAuthController.class);

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    private LitemallAdminService adminService;

    @Autowired
    private LitemallRoleService roleService;
    @Autowired
    private LitemallPermissionService permissionService;
    @Autowired
    private LogHelper logHelper;
    @Autowired
    private ApplicationContext context;
    private HashMap<String, String> systemPermissionsMap = null;

    /*
     *  { username : value, password : value }
     */
    @PostMapping("/login")
    public CommonResult<LoginVo> login(@RequestBody String body, HttpServletRequest request) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return CommonResult.badArgument();
        }
        adminService.verifyPassWord(username, password);
        // 重置jwt
        String token = jwtTokenUtil.generateToken(username);
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);

        return CommonResult.success(loginVo);

    }

    /*
     *
     */
/*
    @PostMapping("/logout")
    public Object logout() {
        Subject currentUser = SecurityUtils.getSubject();

        logHelper.logAuthSucceed("退出");
        currentUser.logout();
        return ResponseUtil.ok();
    }*/


    @GetMapping("/info")
    public Object info(@RequestParam("token") String token) {
        String name = jwtTokenUtil.getUserNameFromToken(token);

        LitemallAdmin admin = adminService.findAdmin(name).get(0);

        Map<String, Object> data = new HashMap<>();
        data.put("name", admin.getUsername());
        data.put("avatar", admin.getAvatar());

        Integer[] roleIds = admin.getRoleIds();
        Set<String> roles = roleService.queryByIds(roleIds);
        Set<String> permissions = permissionService.queryByRoleIds(roleIds);
        data.put("roles", roles);
        // NOTE
        // 这里需要转换perms结构，因为对于前端而已API形式的权限更容易理解
        data.put("perms", toApi(permissions));
        return ResponseUtil.ok(data);
    }

    private Collection<String> toApi(Set<String> permissions) {
        if (systemPermissionsMap == null) {
            systemPermissionsMap = new HashMap<>();
            final String basicPackage = "com.lixin.litemall.admin";
            /*List<Permission> systemPermissions = PermissionUtil.listPermission(context, basicPackage);
            for (Permission permission : systemPermissions) {
                //String perm = permission.getRequiresPermissions().value()[0];
                String api = permission.getApi();
                /// systemPermissionsMap.put(perm, api);
            }*/
        }

        Collection<String> apis = new HashSet<>();
        for (String perm : permissions) {
            String api = systemPermissionsMap.get(perm);
            apis.add(api);

            if (perm.equals("*")) {
                apis.clear();
                apis.add("*");
                return apis;
                //                return systemPermissionsMap.values();

            }
        }
        return apis;
    }

    @GetMapping("/401")
    public Object page401() {
        return ResponseUtil.unlogin();
    }

    @GetMapping("/index")
    public Object pageIndex() {
        return ResponseUtil.ok();
    }

    @GetMapping("/403")
    public Object page403() {
        return ResponseUtil.unauthz();
    }
}
