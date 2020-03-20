package com.qym.controller;

import com.qym.Query.BasicQuery;
import com.qym.dao.RunningAccountDao;
import com.qym.pojo.Account;
import com.qym.pojo.Kind;
import com.qym.pojo.Product;
import com.qym.pojo.RunningAccount;
import com.qym.service.AccountService;
import com.qym.service.KindService;
import com.qym.service.ProductService;
import com.qym.service.RunningAccountService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QueryController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RunningAccountService runningAccountService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private KindService kindService;

    private BasicQuery query=new BasicQuery();

    @RequestMapping({"/admin/index","/admin/index.html"})
    public String adminIndexs(Model model){

        BigDecimal costMoney = new BigDecimal(0);
        BigDecimal incomeMoney=new BigDecimal(0);
        Integer accountCount=0;
        Integer count=0;

        BigDecimal todayIncomeMoney=new BigDecimal(0);

        //查询所有的卡种信息
        List<Product> list=productService.findAll();
        model.addAttribute("plist",list);
//        //先拿到卡种数量的大小
//        int i= list.size();

        //循环算出总支出,总收入
//        for (int j=0;j<i;j++){
//            //BigDecimal加减乘除必须使用对应的方法
//            costMoney=costMoney.add(list.get(j).getCost().multiply(new BigDecimal(list.get(j).getCount())));
//            incomeMoney=incomeMoney.add(list.get(j).getPrice().multiply(new BigDecimal(list.get(j).getActiveCount())));
//        }
        for (Product li:list) {
            costMoney=costMoney.add(li.getCost().multiply(new BigDecimal(li.getCount())));
            incomeMoney=incomeMoney.add(li.getPrice().multiply(new BigDecimal(li.getActiveCount())));
            todayIncomeMoney=todayIncomeMoney.add(li.getPrice().multiply(new BigDecimal(li.getTodayActiveCount())));
            count+=li.getCount();
        }

        //查询账户个数
        List<Account> lista=accountService.findAll();
        accountCount=lista.size();



        query.setCostMoney(costMoney);
        query.setIncomeMoney(incomeMoney);
        query.setAccountCount(accountCount);
        query.setCount(count);
        query.setTodayIncomeMoney(todayIncomeMoney);


        model.addAttribute("query",query);



        //查询账户流水
        List<RunningAccount> lists=runningAccountService.findAllByAccountId(1);
        model.addAttribute("rlist",lists);
        return "admin/index";
    }

    @GetMapping({"/admin/kind","/admin/kind.html"})
    public String kind(Model model){
        List<Kind> list=kindService.findAll();
        model.addAttribute("klist",list);
        return "admin/kind";
    }

    @PostMapping({"/admin/kind-input","/admin/kind-input.html"})
    public String kindInputs(@Valid Kind kind,Model model,RedirectAttributes attributes){
        if (kind.getKindName().equals("")) {
            model.addAttribute("msg","种类名不能为空");
        }else {
            Kind k=kindService.findByKindName(kind.getKindName());
            if (k!=null){
                //数据库中有此种类
                if (kind.getId()==null){
                    //无id,创建新的kind
                    model.addAttribute("msg","种类名不能重复,请重新输入");
                }else{
                    kindService.update(kind);
                    attributes.addFlashAttribute("success","修改成功");
                    return "redirect:/admin/kind";
                }
            }else {
                //数据库中无此种类
                kindService.save(kind);
                attributes.addFlashAttribute("success","添加成功");
                return "redirect:/admin/kind";
            }
        }
        return "admin/kind-input";

    }

    @GetMapping({"/admin/kind-input","/admin/kind-input.html"}) public String kindInput(){
        return "admin/kind-input";
    }

    @GetMapping({"/admin/kind/input/{id}"}) public String kindInputs(@PathVariable String id,Model model){
        Kind k=kindService.getById(new Integer(id));
        model.addAttribute("kind",k);
        return "admin/kind-input";
    }

    @GetMapping({"/admin/kind/delete/{id}"}) public String kindDelete(@PathVariable String id,RedirectAttributes attributes){
        kindService.delete(new Integer(id));
        attributes.addFlashAttribute("success","删除成功");
        return "redirect:/admin/kind";
    }


    @GetMapping({"/admin/product","/admin/product.html"})
    public String product(Model model){
        //查询所有的卡种信息
       List<Product> list=productService.findAll();
       model.addAttribute("plist",list);
        return "admin/product";
    }



    @PostMapping({"/admin/product-input","/admin/product-input.html"})
    public String productInputs(Model model,@Valid Product product,RedirectAttributes attributes){
        List<Kind> list=kindService.findAll();
        model.addAttribute("klist",list);
        if (product.getName().equals("")){
            model.addAttribute("msg","添加失败,商品名称不能为空");
            return "admin/product-input";
        }else {
            Product p= productService.findByName(product.getName());
            if(p!=null){
                //数据库中有此种类
                if (product.getId()==null){
                    model.addAttribute("msg","添加失败,有重复的名称,请重新输入名称");
                }else {
                    product.setId(p.getId());
                    product.setLastEditTime(new Date());
                    product.setTodayActiveCount(p.getTodayActiveCount());
                    product.setActiveCount(p.getActiveCount());
                    productService.update(product);
                    attributes.addFlashAttribute("success","修改成功");
                    return "redirect:/admin/product";
                }
            }else {
                //数据库中无此种类
                product.setCreateTime(new Date());
                product.setLastEditTime(new Date());
                product.setActiveCount(new Integer(0));
                product.setTodayActiveCount(new Integer(0));
                productService.save(product);
                attributes.addFlashAttribute("success","添加成功");
                return "redirect:/admin/product";
            }
            return "admin/product-input";
        }
    }

    @GetMapping("/admin/product/input/{id}")
    public String productEditor(Model model,@PathVariable String id){
        Product p= productService.getById(new Integer(id));
        model.addAttribute("product",p);
        List<Kind> list=kindService.findAll();
        model.addAttribute("klist",list);
        return "admin/product-input";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String productDelete(RedirectAttributes attributes, @PathVariable String id){
        productService.remove(new Integer(id));
        attributes.addFlashAttribute("success","删除成功!");
        return "redirect:/admin/product";
    }


    @GetMapping({"/admin/product-input","/admin/product-input.html"})
    public String productInput(Model model){
        List<Kind> list=kindService.findAll();
        model.addAttribute("klist",list);
        return "admin/product-input";
    }
}
