package cn.tommyyang.haoyan.controller;

/**
 * Created by TommyYang on 2017/12/19.
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/chart")
public class ChartController {
    @RequestMapping(value ="/index",method=RequestMethod.GET)
    @ResponseBody
    public ModelAndView addarticle(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index1");
        return mv;
    }
}
