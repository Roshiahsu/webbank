package cn.tedu.webbank.controller;

import cn.tedu.webbank.pojo.dto.UserAddNewDTO;
import cn.tedu.webbank.service.IUserService;
import cn.tedu.webbank.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Version 1.0
 * @Description TODO
 * @Date 2022/10/19、下午11:32
 */
@RestController
@RequestMapping("/users")
@Slf4j
@Api(tags = "1.用戶處理模塊")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/addNew")
    @ApiOperation(value = "1.使用者註冊")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@RequestBody UserAddNewDTO userAddNewDTO){
        log.debug("開始新增用戶");
        userService.insert(userAddNewDTO);
        return JsonResult.ok();
    }

}
