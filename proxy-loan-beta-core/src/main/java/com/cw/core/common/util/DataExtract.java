package com.cw.core.common.util;
/*
 * 描       述:  &lt;描述&gt;
 * 修  改   人:  ${user}
 * 修  改 时 间:  ${date}
 * &lt;修改描述:&gt;
 */

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataExtract {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /** 网页爬虫拉取数据
     *&lt;功能简述&gt;
     *&lt;功能详细描述&gt;
     * ${tags} [参数说明]
     *
     * @return ${return_type} [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void getNetDataImportDb(Long productId){
        try{
           //袋鼠达人 50
           //泰升金所 46
           String sql="SELECT id,background_url as backgroundUrl,page_no as pageNo from cw_product_url where product_id= "+productId;
           List<Product> rs=jdbcTemplate.query(sql,new BeanPropertyRowMapper(Product.class));
           for(Product product : rs){
                String url = product.getBackgroundUrl();
                 System.out.println("  url = "+url);
                 if(productId==50){
                     /**袋鼠达人链接地址*/
                    url = "http://mobile.zhejiangzhou.cn/index.php?s="+url.replaceAll("http://www.zhejiangzhou.cn","")
                          .replaceAll("=","/").replaceAll("\\?","/");

                 }
                 if(productId==64) {
                     if (url.indexOf("taishengjinsuo.com") != -1) {
                         /** 泰升金所有2个后台地址不一样 */
                         url = "http://www.taishengjinsuo.com/index.php?s=" + url.replaceAll("http://www.taishengjinsuo.com", "")
                                 .replaceAll("=", "/").replaceAll("\\?", "/");
                     } else {

                         /***泰升金所链接地址 **/
                         url = "http://www.ajt2.com/index.php?s=" + url.replaceAll("http://www.ajt2.com", "")
                                 .replaceAll("=", "/").replaceAll("\\?", "/");


                     }
                 }
                 if(productId==74){
                    /***华亿工匠链接地址 **/
                    url = "http://www.lyxhuayi.com/index.php?s="+url.replaceAll("http://www.lyxhuayi.com","")
                           .replaceAll("=","/").replaceAll("\\?","/");
                 }

                String totalRecord = getTotalDataRecord(url);
                System.out.println(url+"===total==="+totalRecord);
                int totalPage = new BigDecimal(totalRecord).divide(new BigDecimal(10)).setScale(BigDecimal.ROUND_HALF_UP).intValue()+1;
                System.out.println("totalPage = "+totalPage);
                Integer pageNo = product.getPageNo();
                if(pageNo==null){
                    pageNo=1;
                }else {
                    pageNo = pageNo+1;
                }
                for(int i=pageNo;i<=totalPage;i++) {
                    String currentUrl= url+"/p/"+i+".html";
                    System.out.println(i+"  currentUrl = "+currentUrl);
                    List<Product> productList = getURLInfo(currentUrl, "utf-8");
                    //保存数据到数据库
                    saveDownloadData(productList);

                }
                //执行完成sql数据
                jdbcTemplate.execute("update cw_product_url set page_no="+(totalPage+1)+" where id="+product.getId());
            }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public List<Product> getURLInfo(String urlInfo,String charset) throws Exception {
        //读取目的网页URL地址，获取网页源码
        URL url = new URL(urlInfo);
        HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();
        InputStream is = httpUrl.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            //这里是对链接进行处理
            line = line.replaceAll("</?a[^>]*>", "");
            //这里是对样式进行处理
            line = line.replaceAll("<(\\w+)[^>]*>", "<$1>");
            sb.append(line);
        }
        is.close();
        br.close();
        //获得网页源码
        return getDataStructure(urlInfo);
    }

    public void saveDownloadData(List<Product> productList){
        try{

           for(Product product:productList) {
               jdbcTemplate.execute("insert into cw_download_data(name,phone,register_date,product) " +
                       "values('" + product.getName() + "','" + product.getPhone() + "','" + product.getRegisterDate() + "','"+
                       product.getProduct()+"')");
           }
       }catch (Exception e){
           e.printStackTrace();
       }

    }
    //获取总的用户数据
    private static String getTotalDataRecord(String str) {
       //运用正则表达式对获取的网页源码进行数据匹配，提取我们所要的数据，在以后的过程中，我们可以采用httpclient+jsoup,
       //现在暂时运用正则表达式对数据进行抽取提取
       String result ="";
       try {
           Parser parser = new Parser(str);
           parser.setEncoding("UTF-8");
           NodeFilter nodeFilter = new AndFilter(new TagNameFilter("div"),
                   new AndFilter(new HasAttributeFilter[]{new HasAttributeFilter("class", "fr sum-user")}));
           NodeList nodeLists =parser.parse(nodeFilter);

           result = nodeLists.elementAt(0).getChildren().elementAt(3).toHtml().replaceAll("个","").trim();
       }catch (Exception e){
           e.printStackTrace();
       }

       return result;
   }

    private List<Product> getDataStructure(String str) {
        //运用正则表达式对获取的网页源码进行数据匹配，提取我们所要的数据，在以后的过程中，我们可以采用httpclient+jsoup,
        //现在暂时运用正则表达式对数据进行抽取提取
        List<Product> productList = new ArrayList<>();
        try {
            Parser parser = new Parser(str);
            parser.setEncoding("UTF-8");
            NodeFilter nodeFilter = new TagNameFilter("ul");
            NodeList nodeLists =parser.parse(nodeFilter);

            for(int i=1;i<nodeLists.size();i++) {
                Node node = nodeLists.elementAt(i);
                NodeList nodeList = node.getChildren();
                Product product = new Product();
                NodeList liNodeList = nodeList.extractAllNodesThatMatch(new TagNameFilter("li"));

                String name = liNodeList.elementAt(1).toHtml().replaceAll("<li class=\"name fl\">","").replaceAll("</li>","").replaceAll("<li>","");
                String phone = liNodeList.elementAt(2).toHtml().replaceAll("<li class=\"phone fl\">","").replaceAll("</li>","").replaceAll("<li>","");
                String registerDate = liNodeList.elementAt(4).toHtml().replaceAll("<li class=\"time fl\">","").replaceAll("</li>","").replaceAll("<li>","");

                product.setName(name);
                product.setPhone(phone);
                product.setRegisterDate(registerDate);
                product.setProduct("hygj");
                productList.add(product);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return productList;
    }
}
class Product {
    private Integer id;
    private String name;//产品型号
    private String phone;//产品数量
    private String registerDate;//产品报价
    private String product;
    private Integer pageNo;
    private String backgroundUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }
}