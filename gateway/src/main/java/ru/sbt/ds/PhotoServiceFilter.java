package ru.sbt.ds;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.stereotype.Component;

import java.net.URL;

/**
 * Created by sbt-safonov-vv on 03.05.2017.
 */
@Component
public class PhotoServiceFilter extends ZuulFilter {

    @Autowired
    private EurekaClient discoveryClient;

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    private String urlRedirectVersion1 = "localhost:45421/img?id=100";

    private String urlRedirectVersion2 = "localhost:47000/img?id=100";

    @Override
    public Object run() {
        Applications apps = discoveryClient.getApplications();
        Application application = null;
        Long curVersion = 0L;
        Long version = 1L;
        for (Application app : apps.getRegisteredApplications()){
            if(app.getInstances().get(0) != null && app.getName().toLowerCase().equals("photo-service"))
                try {
                    version = Long.parseLong(app.getInstances().get(0).getMetadata().get("version"));
                    if(version > curVersion){
                        application = app;
                        curVersion = version;
                    }
                } catch (NumberFormatException e){
                    version = 1L;
                }
        }

        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            if(version == 1L)
                ctx.setRouteHost(new URL(urlRedirectVersion1));
            if(version == 2L)
                ctx.setRouteHost(new URL(urlRedirectVersion2));
        } catch (Throwable e){
            System.out.println("went wrong!");
        }
        return null;
    }

}
