package cn.tommyyang.haoyan.controller;

/**
 * Created by TommyYang on 2017/12/19.
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tommyyang.haoyan.websocket.hello.HelloHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@Controller
@RequestMapping("/chart")
public class ChartController {

    private final static Logger logger = LoggerFactory.getLogger(ChartController.class);

    @Autowired
    private HelloHandler helloHandler;

    @RequestMapping(value ="/index",method=RequestMethod.GET)
    @ResponseBody
    public ModelAndView addarticle(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index1");
        return mv;
    }

    @RequestMapping(value = "/message", method = RequestMethod.GET)
    @ResponseBody
    public String sendMessage(HttpServletRequest req, HttpServletResponse resp){
        double rand = Math.ceil(Math.random()*100);
        try {
            helloHandler.broadcast(new TextMessage("Websocket测试消息" + rand));
        } catch (IOException e) {
            logger.error("broadcast error:\n", e);
        }
        return "message";
    }
}
