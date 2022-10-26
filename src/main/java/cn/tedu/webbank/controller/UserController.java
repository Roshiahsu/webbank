package cn.tedu.webbank.controller;

import cn.tedu.webbank.pojo.dto.UserAddNewDTO;
import cn.tedu.webbank.pojo.dto.UserLoginDTO;
import cn.tedu.webbank.pojo.entity.User;
import cn.tedu.webbank.security.LoginPrinciple;
import cn.tedu.webbank.service.IUserService;
import cn.tedu.webbank.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Version 1.0
 * @Description
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

    @PostMapping("/login")
    @ApiOperation(value = "2.使用者登入")
    @ApiOperationSupport(order = 200)
    public JsonResult login(@RequestBody UserLoginDTO userLoginDTO){
        log.debug("開始用戶登入業務");
        String jwt = userService.login(userLoginDTO);
        return JsonResult.ok(jwt);
    }

    @ApiOperation(value ="3.存款")
    @ApiOperationSupport(order = 300)
    @PostMapping("/deposit")
    public JsonResult deposit(Long money, @AuthenticationPrincipal LoginPrinciple loginPrinciple){
        log.debug("開始存錢");
        log.debug("Authentication{}",loginPrinciple);
        User user = userService.deposit(money, loginPrinciple);
        return JsonResult.ok(user);
    }

    @ApiOperation(value = "4.領錢")
    @ApiOperationSupport(order = 400)
    @PostMapping("/cashOut")
    public JsonResult cashOut(Long money,@AuthenticationPrincipal LoginPrinciple loginPrinciple){
        log.debug("開始領錢業務");
        User user = userService.cashOut(money, loginPrinciple);
        return JsonResult.ok(user);
    }

    //TODO 餘額查詢
    @ApiOperation(value = "5.餘額查詢")
    @ApiOperationSupport(order = 500)
    @PostMapping("/balance")
    public JsonResult balanceCheck(@AuthenticationPrincipal LoginPrinciple loginPrinciple){
        log.debug("開始餘額查詢");
        User user = userService.balanceCheck(loginPrinciple);
        return JsonResult.ok(user);
    }

    //TODO 修改密碼
    @ApiOperation(value = "6.修改密碼")
    @ApiOperationSupport(order = 600)
    @PostMapping("/passwordUpdate")
    public JsonResult passwordUpdate(String password,@AuthenticationPrincipal LoginPrinciple loginPrinciple){
        log.debug("開始修改密碼");
        userService.passwordUpdate(password, loginPrinciple);
        return JsonResult.ok();
    }

}
