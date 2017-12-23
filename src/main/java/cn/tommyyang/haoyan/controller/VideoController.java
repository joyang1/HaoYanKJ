package cn.tommyyang.haoyan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by TommyYang on 2017/12/23.
 */
@Controller
@RequestMapping("video")
public class VideoController {

    @RequestMapping(value ="/watchvideo",method= RequestMethod.GET)
    @ResponseBody
    public ModelAndView addarticle(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("video");
        return mv;
    }

}
