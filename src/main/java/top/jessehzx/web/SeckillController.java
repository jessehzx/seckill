package top.jessehzx.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.jessehzx.dto.Exposer;
import top.jessehzx.dto.SeckillExcution;
import top.jessehzx.dto.SeckillResult;
import top.jessehzx.entity.Seckill;
import top.jessehzx.enums.SeckillStatEnum;
import top.jessehzx.exception.RepeatKillException;
import top.jessehzx.exception.SeckillCloseException;
import top.jessehzx.exception.SeckillException;
import top.jessehzx.service.SeckillService;

import java.util.Date;
import java.util.List;

/**
 * @author jessehzx
 * @date 2018/5/14
 */
@Controller
@RequestMapping("/seckill") // url:模块/资源/{id}/细分 如：/seckill/list
public class SeckillController {

    private final Logger logger = LoggerFactory.getLogger(SeckillController.class);

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        // 获取列表页
        List<Seckill> seckillList = seckillService.getSeckillList();
        model.addAttribute("list", seckillList);
        // list.jsp + model = ModelAndView
        return "list"; // WEB_INF/list.jsp
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        // 假如seckillId为空，重定向到列表页
        if (null == seckillId) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        // 假如seckillId不合法，转发到列表页
        if (null == seckill) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    @ResponseBody // 表示返回的是json格式的数据
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    @ResponseBody
    /* 参数killPhone是从浏览器的cookie中获取到的。
        Spring MVC默认required=true，它要求必传，没有传值就直接报错。
        那我们把它设置为false，就是没有传不让Spring MVC报错，而是交于我们的代码逻辑中去判断 */
    public SeckillResult<SeckillExcution> excutor(@PathVariable("seckillId") Long seckillId,
                                                  @CookieValue(value = "killPhone", required = false) Long killPhone,
                                                  @PathVariable("md5") String md5) {
        // 验证手机号
        if (null == killPhone) {
            return new SeckillResult<SeckillExcution>(false, "未注册");
        }
        SeckillResult<SeckillExcution> result;
        try {
            // 使用存储过程
            SeckillExcution seckillExcution = seckillService.excuteSeckillProcedure(seckillId, killPhone, md5);
            result = new SeckillResult<SeckillExcution>(true, seckillExcution);
        } catch (RepeatKillException e1) {
            SeckillExcution seckillExcution = new SeckillExcution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExcution>(true, seckillExcution);
        } catch (SeckillCloseException e2) {
            SeckillExcution seckillExcution = new SeckillExcution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckillExcution>(true, seckillExcution);
        } catch (SeckillException e) {
            SeckillExcution seckillExcution = new SeckillExcution(seckillId, SeckillStatEnum.INNER_KILL);
            result = new SeckillResult<SeckillExcution>(true, seckillExcution);
        }
        return result;
    }

    @RequestMapping(value = "/time/now", method=RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time() {
        Date data = new Date();
        return new SeckillResult<Long>(true, data.getTime());
    }

}
