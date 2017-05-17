package ru.sbt.ds;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.Server;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Comparator;

/**
 * Created by sbt-safonov-vv on 03.05.2017.
 */
@Component
public class PhotoServiceFilter extends ZuulFilter {

    @Autowired
    private EurekaClient discoveryClient;

    PingUrl pinger = new PingUrl();

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



    @Override
    public Object run() {
        Applications apps = discoveryClient.getApplications();
        Application application = null;
        application = apps.getRegisteredApplications()
                .stream()
                .filter(s -> s.getName().toLowerCase().equals("photo-service"))
                .findFirst()
                .get();

        pinger.setSecure(false);
        pinger.setPingAppendString("/img?id=100");

        InstanceInfo target = application.getInstances()
                .stream()
                .sorted(Comparator.comparingLong(s -> Long.parseLong(s.getMetadata().get("version"))))
                .filter(s -> pinger.isAlive(new Server(s.getHostName(), s.getPort())))
                .findFirst()
                .get();

        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            ctx.setRouteHost(new URL(target.getHomePageUrl()));
        } catch (Throwable e){
            System.out.println("went wrong!");
        }
        return null;
    }

}
