package org.example.aop;

import com.alibaba.fastjson.JSONObject;
import org.example.mapper.OperateLogMapper;
import org.example.pojo.OperateLog;
import org.example.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect //切面类
public class LogAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("execution(* org.example.service.*.*(..))")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取目标方法的名字
        String methodName = joinPoint.getSignature().getName();

        // 你可以添加其它的排除条件
        if (methodName.contains("login") || methodName.contains("register") || methodName.contains("Log")) {
            return joinPoint.proceed();  // 排除带有 "exclude" 的方法
        }

        //操作人ID - 当前登录员工ID
        //获取请求头中的jwt令牌, 解析令牌
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);
        String operateUsername = (String) claims.get("username");

        //操作时间
        LocalDateTime operateTime = LocalDateTime.now();

        //操作类名
        String className = joinPoint.getTarget().getClass().getName();

        //操作方法参数
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);

        long begin = System.currentTimeMillis();
        //调用原始目标方法运行
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        //方法返回值
        String returnValue = JSONObject.toJSONString(result);

        //操作耗时
        Long costTime = end - begin;


        //记录操作日志
        OperateLog operateLog = new OperateLog(null, operateUsername, operateTime, className, methodName, costTime);
        operateLogMapper.insertOperateLog(operateLog);

        log.info("AOP记录操作日志: {}", operateLog);

        return result;
    }

}
