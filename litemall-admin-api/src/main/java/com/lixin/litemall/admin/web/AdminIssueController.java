package com.lixin.litemall.admin.web;

import com.lixin.litemall.core.util.ResponseUtil;
import com.lixin.litemall.core.validator.Order;
import com.lixin.litemall.core.validator.Sort;
import com.lixin.litemall.db.domain.LitemallIssue;
import com.lixin.litemall.db.service.LitemallIssueService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/issue")
@Validated
public class AdminIssueController {
    private final Log logger = LogFactory.getLog(AdminIssueController.class);

    @Autowired
    private LitemallIssueService issueService;

    //("admin:issue:list")
    //Desc(menu = {"商场管理", "通用问题"}, button = "查询")
    @GetMapping("/list")
    public Object list(String question,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallIssue> issueList = issueService.querySelective(question, page, limit, sort, order);
        return ResponseUtil.okList(issueList);
    }

    private Object validate(LitemallIssue issue) {
        String question = issue.getQuestion();
        if (StringUtils.isEmpty(question)) {
            return ResponseUtil.badArgument();
        }
        String answer = issue.getAnswer();
        if (StringUtils.isEmpty(answer)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    //("admin:issue:create")
    //Desc(menu = {"商场管理", "通用问题"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody LitemallIssue issue) {
        Object error = validate(issue);
        if (error != null) {
            return error;
        }
        issueService.add(issue);
        return ResponseUtil.ok(issue);
    }

    //("admin:issue:read")
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        LitemallIssue issue = issueService.findById(id);
        return ResponseUtil.ok(issue);
    }

    //("admin:issue:update")
    //Desc(menu = {"商场管理", "通用问题"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody LitemallIssue issue) {
        Object error = validate(issue);
        if (error != null) {
            return error;
        }
        if (issueService.updateById(issue) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok(issue);
    }

    //("admin:issue:delete")
    //Desc(menu = {"商场管理", "通用问题"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody LitemallIssue issue) {
        Integer id = issue.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        issueService.deleteById(id);
        return ResponseUtil.ok();
    }

}
