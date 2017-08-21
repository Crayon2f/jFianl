package com.ivan.jfinal.core;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.ivan.jfinal.controller.AdminController;
import com.ivan.jfinal.controller.IndexController;
import com.ivan.jfinal.kit.StringKit;
import com.jfinal.config.*;
import com.jfinal.core.Const;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
//import com.jfinal.template.Engine;

public class Config extends JFinalConfig {

//    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    private static final String config_file = "config.properties";
    private static final String upload_path = "/WEB-INF/upload";

    @Override
    public void configConstant(Constants me) {

//        me.setBaseViewPath("/WEB-INF/view");
        me.setError404View("/WEB-INF/view/error/404.ftl");
        me.setError500View("/WEB-INF/view/error/500.ftl");
        me.setError401View("/WEB-INF/view/error/401.ftl");
        me.setDevMode(true);
        me.setViewType(ViewType.FREE_MARKER);
        me.setBaseUploadPath(upload_path);
        me.setMaxPostSize(Const.DEFAULT_MAX_POST_SIZE * 100);
        me.setViewExtension(".ftl");
    }

    public void configRoute(Routes routes) {

        routes.setBaseViewPath("/WEB-INF/view");
        routes.add("/", IndexController.class);
        routes.add("/admin", AdminController.class);
    }

    public void configEngine(Engine engine) {

        engine.setDevMode(true);
//        engine.addSharedFunction("/WEB-INF/view/common/layout.ftl");
    }

    public void configPlugin(Plugins plugins) {

        DruidPlugin druidPlugin = createDruidPlugin();
        if (null != druidPlugin) {
            plugins.add(druidPlugin);
            ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
            arp.setShowSql(true);
//            _MappingKit.mapping(arp);
            plugins.add(arp);
        }
    }

    public void configInterceptor(Interceptors interceptors) {

    }

    public void configHandler(Handlers handlers) {

    }

    /**
     * 创建数据库链接
     *
     * @return
     */
    private static DruidPlugin createDruidPlugin() {

        Prop prop = PropKit.use(config_file);
        String url = prop.get("mysql.url");
        String userName= prop.get("mysql.user-name");
        String password = prop.get("mysql.password");
        if (StringKit.isBlank(url) || StringKit.isBlank(userName) || StringKit.isBlank(password)) {
//            logger.info("init db false, the url or username or password is blank!!");
            System.out.println("init db false, the url or username or password is blank!!");
            return null;
        }
        DruidPlugin druidPlugin = new DruidPlugin(url.trim(), userName.trim(), password.trim(), StringKit.trim(prop.get("mysql.driver-class")));
        // StatFilter提供JDBC层的统计信息
        druidPlugin.addFilter(new StatFilter());
        // WallFilter的功能是防御SQL注入攻击
        WallFilter wallDefault = new WallFilter();
        druidPlugin.addFilter(wallDefault);
        druidPlugin.setInitialSize(prop.getInt("db-initialSize", 5));
        druidPlugin.setMinIdle(prop.getInt("db-minIdle", 5));
        druidPlugin.setMaxActive(prop.getInt("db-maxActive", 5));

        return druidPlugin;
    }
}