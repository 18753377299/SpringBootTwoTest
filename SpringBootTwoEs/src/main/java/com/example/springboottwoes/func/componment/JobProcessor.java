package com.example.springboottwoes.func.componment;

import com.example.springboottwoes.func.po.JobInfo;
import com.example.springboottwoes.func.vo.MathSalary;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@Component
public class JobProcessor implements PageProcessor {

    @Autowired
    private SpringDataPipeline springDataPipeline;

    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 100)
    public void process() {
        //访问入口url地址
        String url = "https://search.51job.com/list/000000,000000,0000,01%252C32,9,99,java,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
        Spider.create(new JobProcessor())
                .addUrl(url)
                .setScheduler(new QueueScheduler()
                        .setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
                .thread(5)
                .run();
    }

    public void process(Page page) {
        //获取页面数据
        List<Selectable> nodes = page.getHtml().$("div#resultList div.el").nodes();

        //判断nodes是否为空
        if (nodes.isEmpty()) {
            try {
                //如果为空，表示这是招聘信息详情页保存信息详情
                this.saveJobInfo(page);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            //如果有值，表示这是招聘信息列表页
            for (Selectable node : nodes) {
                //获取招聘信息详情页url
                String jobUrl = node.links().toString();
                //添加到url任务列表中，等待下载
                page.addTargetRequest(jobUrl);

                //获取翻页按钮的超链接
                List<String> listUrl = page.getHtml().$("div.p_in li.bk").links().all();
                //添加到任务列表中
                page.addTargetRequests(listUrl);

            }
        }
    }

    /**
     * 解析页面，获取招聘详情
     *
     * @param
     */
    private void saveJobInfo(Page page) {
        //创建招聘信息对象
        JobInfo jobInfo = new JobInfo();
        Html html = page.getHtml();

        //公司名称
        jobInfo.setCompanyName(html.$("div.tHeader p.cname a", "text").toString());
        //公司地址
        jobInfo.setCompanyAddr(html.$("div.tBorderTop_box:nth-child(3) p.fp", "text").toString());
        //公司信息
        jobInfo.setCompanyInfo(html.$("div.tmsg", "text").toString());
        //职位名称
        jobInfo.setJobName(html.$("div.tHeader > div.in > div.cn > h1", "text").toString());
        //工作地点
        jobInfo.setJobAddr(html.$("div.tHeader > div.in > div.cn > span.lname", "text").toString());
        //职位信息
        jobInfo.setJobInfo(Jsoup.parse(html.$("div.tBorderTop_box:nth-child(2)").toString()).text());
        //工资范围
        String salaryStr = html.$("div.tHeader > div.in > div.cn > strong", "text").toString();
        jobInfo.setSalaryMin(MathSalary.getSalary(salaryStr)[0]);
        jobInfo.setSalaryMax(MathSalary.getSalary(salaryStr)[1]);
        //职位详情url
        jobInfo.setUrl(page.getUrl().toString());
        //职位发布时间
        String time = html.$("div.jtag > div.t1 > span.sp4", "text").regex(".*发布").toString();
        jobInfo.setTime(time.substring(0, time.length() - 2));

        //保存数据
        page.putField("jobInfo", jobInfo);
    }




    private Site site = Site.me()
            .setCharset("UTF-8")//编码
            .setSleepTime(1)//抓取间隔时间
            .setTimeOut(1000*10)//超时时间10s
            .setRetrySleepTime(3000)//重试时间
//        .setHttpProxy(new HttpHost("127.0.0.1",8080))  // 设置代理（暂时失败了）
            .setRetryTimes(3);//重试次数

    public Site getSite() {
        return site;
    }




}

