package com.histsys.middleware;

import com.blade.ioc.annotation.Bean;
import com.blade.mvc.RouteContext;
import com.blade.mvc.hook.WebHook;

@Bean
public class HttpResponseMiddleware implements WebHook {
    @Override
    public boolean before(RouteContext routeContext) {
        return true;
    }

    @Override
    public boolean after(RouteContext context) {
        return true;
    }
}
