package com.scnu.travel.bean;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Aspect
public class LogAop {
    @Autowired
    private HttpServletRequest request;

    //通过这个logger对象可以用来打印日志
    private static Logger logger = LoggerFactory.getLogger(LogAop.class);

    @Pointcut("execution(* com.scnu.travel.controller.backstage.*.*(..))")
    public void PointCut(){}

    @Before("PointCut()")
    public void doBefore(JoinPoint joinPoint){
        //记录访问的时间
        Date date = new Date();
        request.setAttribute("visitTime",date);
    }

    @After("PointCut()")
    public void doAfter(JoinPoint joinPoint){
        Log log = new Log();
        Date visitTime = (Date) request.getAttribute("visitTime");//访问时间
        Date now = new Date();
        int executionTime = (int) (now.getTime() - visitTime.getTime());//访问时长
        String ip = request.getRemoteAddr();//访问ip
        String requestURI = request.getRequestURI();//访问路径
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();//Security的User对象
        if (user instanceof User) {
            String username = ((User) user).getUsername();
            log.setUsername(username);
        }
        log.setExecutionTime(executionTime);
        log.setUrl(requestURI);
        log.setIp(ip);
        log.setVisitTime(visitTime);

        logger.info(log.toString());
    }

    @AfterThrowing(pointcut = "PointCut()",throwing = "ex")
    //参数表示抛出的异常信息
    public void afterThrowing(Throwable ex){
        Log log = new Log();

        Date visitTime = (Date) request.getAttribute("visitTime"); // 访问时间
        Date now = new Date();
        int executionTime = (int) (now.getTime() - visitTime.getTime()); // 访问时长
        String ip = request.getRemoteAddr(); // 访问ip
        String url = request.getRequestURI(); // 访问路径
        // 拿到Security中的User对象
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof User){
            String username = ((User) user).getUsername();
            log.setUsername(username);
        }
        log.setExecutionTime(executionTime);
        log.setUrl(url);
        log.setIp(ip);
        log.setVisitTime(visitTime);

        // 异常信息
        String exMessage = ex.getMessage();
        log.setExceptionMessage(exMessage);

        logger.info(log.toString());
    }
}