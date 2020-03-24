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
            Kind modify=kindService.findByKindName(kind.getKindName());
            if (kind.getId()!=null){
                //数据库查到的编辑id的对象的原始数据
                Kind origin=kindService.getById(kind.getId());
                //查到的编辑id修改的name在数据库中的对象
                //判断一下,如果name未改变,那么modify和origin的name相同,同时修改成功,
                //如果name改变了,那么modify和origin的name不相同,修改失败
                //如果modify不存在,那么就修改成功
                if (modify!=null){
                    if(origin.getKindName().equals(modify.getKindName())){
                        //原始数据,name未改动
                        kindService.update(kind);
                        attributes.addFlashAttribute("success","修改成功");
                        return "redirect:/admin/kind";
                    }else if (!origin.getKindName().equals(modify.getKindName())){
                        model.addAttribute("msg","修改失败:种类名重复,请重新输入");
                    }
                }else if(modify==null){
                    //修改了name
                    kindService.update(kind);
                    attributes.addFlashAttribute("success","修改成功");
                    return "redirect:/admin/kind";
                }
            }else {
                if (modify!=null){
                    //数据库中有此种类
                    //无id,创建新的kind
                    model.addAttribute("msg","种类名不能重复,请重新输入");
                }else {
                    //数据库中无此种类
                    kindService.save(kind);
                    attributes.addFlashAttribute("success","添加成功");
                    return "redirect:/admin/kind";
                }
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
        Kind k= kindService.getById(new Integer(id));
        List<Product> plist =k.getProduct();
        if (plist.size()>0){
            System.out.println(plist.size());
            attributes.addFlashAttribute("success","删除失败,该种类已被使用");
            return "redirect:/admin/kind";
        }else {
            kindService.delete(new Integer(id));
            attributes.addFlashAttribute("success","删除成功");
            return "redirect:/admin/kind";
        }

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
                Product modify= productService.findByName(product.getName());
                if (product.getId()!=null) {
                    Product origin = productService.getById(product.getId());
                    if (modify!=null && origin.getName().equals(modify.getName())){
                        //如果origin和modify的name相同,则未修改名称
                        product.setLastEditTime(new Date());
                        product.setTodayActiveCount(origin.getTodayActiveCount());
                        product.setActiveCount(origin.getActiveCount());
                        productService.update(product);
                        attributes.addFlashAttribute("success","修改成功");
                        return "redirect:/admin/product";
                    }else if (modify==null){
                        //如果modify不存在,则修改成功
                        product.setLastEditTime(new Date());
                        product.setTodayActiveCount(origin.getTodayActiveCount());
                        product.setActiveCount(origin.getActiveCount());
                        productService.update(product);
                        attributes.addFlashAttribute("success","修改成功");
                        return "redirect:/admin/product";
                    }else if (!origin.getName().equals(modify.getName()) &&modify!=null){
                        //如果origin和modify名称不一样,modify存在,则重名
                        model.addAttribute("msg","添加失败:商品名称不能重复,请重新输入名称");
                    }


                }else {
                    //product没有id,本次提交为新增
                    if (modify!=null){
                        model.addAttribute("msg","添加失败:商品名称不能重复,请重新输入名称");
                    }else {
                        //没有重复名字
                        product.setCreateTime(new Date());
                        product.setLastEditTime(new Date());
                        //设置全新的active数量
                        product.setTodayActiveCount(0);
                        product.setActiveCount(0);
                        productService.save(product);
                        attributes.addFlashAttribute("success","添加成功");
                        return "redirect:/admin/product";
                    }
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
