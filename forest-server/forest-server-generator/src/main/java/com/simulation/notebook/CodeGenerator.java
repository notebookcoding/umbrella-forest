package com.simulation.notebook;


import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.simulation.notebook.base.service.BaseService;
import com.simulation.notebook.base.service.impl.BaseServiceImpl;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * 生成的代码及时 copy 到本项目业务中
 * forest-server-generator 中下修改用户名、密码、url、等操作严禁提交到Git
 * 代码生成器
 * @author Administrator
 */
public class CodeGenerator {

    /**
     * 文件路径
     */
    private static String GENERATOR_PATH = "/forest-server/forest-server-generator/src/main/java/";
    /**
     * mapper的地址
     */
    private static String XML_PATH = "com/simulation/notebook/mapper";


    public static void main(String[] args) throws IOException {

        Generator properties = readeProperties();

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + GENERATOR_PATH;

        gc.setOutputDir(projectPath);

        gc.setAuthor(properties.getAuthor());
        gc.setOpen(false);
        //mapper 命名方式 默认值：null 例如：%sDao 生成 UserDao
        gc.setMapperName("%sMapper");
        //Mapper xml 命名方式   默认值：null 例如：%sDao 生成 UserDao.xml
        gc.setXmlName("%sMapper");
        //service 命名方式   默认值：null 例如：%sBusiness 生成 UserBusiness
        gc.setServiceName("%sService");
        //service impl 命名方式  默认值：null 例如：%sBusinessImpl 生成 UserBusinessImpl
        gc.setServiceImplName("%sServiceImpl");
        //controller 命名方式    默认值：null 例如：%sAction 生成 UserAction
        gc.setControllerName("%sController");
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(properties.getUrl());
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(properties.getUsername());
        dsc.setPassword(properties.getPassword());
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.simulation.notebook");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                return projectPath + XML_PATH + "/mapper" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.simulation.notebook.base.entity.BaseBean");
        strategy.setEntityLombokModel(true);
        strategy.setChainModel(true);
        strategy.setRestControllerStyle(true);

        // jby 自定义配置
        strategy.setSuperServiceClass(BaseService.class);
        strategy.setSuperServiceImplClass(BaseServiceImpl.class);
        strategy.setSuperMapperClass("com.simulation.notebook.base.mapper.IBaseMapper");

        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id", "create_time", "create_by", "update_time", "update_by", "is_deleted");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 配置文件读取
     * @return Generator 数据对象
     */
    @SneakyThrows
    private static Generator readeProperties() {
        Properties properties = new Properties();
        InputStream in = CodeGenerator.class.getClassLoader().getResourceAsStream("generator.properties");
        properties.load(in);
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String author = properties.getProperty("author");

        if (StringUtils.isBlank(username)) {
            throw new NullPointerException("generator.properties 变量[username]未填写!");
        }
        if (StringUtils.isBlank(password)) {
            throw new NullPointerException("generator.properties 变量[password]未填写!");
        }
        if (StringUtils.isBlank(url)) {
            throw new NullPointerException("generator.properties 变量[url]未填写!");
        }
        if (StringUtils.isBlank(author)) {
            throw new NullPointerException("generator.properties 变量[author]未填写!");
        }
        return new Generator()
                .setUsername(username)
                .setPassword(password)
                .setUrl(url)
                .setAuthor(author);
    }
}
