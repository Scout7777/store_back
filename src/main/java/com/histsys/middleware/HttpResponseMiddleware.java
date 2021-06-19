package com.histsys.middleware;

import com.blade.ioc.annotation.Bean;
import com.blade.mvc.RouteContext;
import com.blade.mvc.hook.WebHook;
import com.blade.mvc.http.Body;
import com.blade.mvc.http.Response;
import com.blade.mvc.http.StringBody;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Strings;

import java.io.BufferedWriter;

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
