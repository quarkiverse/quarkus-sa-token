/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.quarkiverse.satoken.resteasy.it.integrate;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/rt/")
@ApplicationScoped
public class RouterResource {

    @POST
    @Path("getInfo")
    public SaResult getInfo() {
        return SaResult.ok();
    }

    @POST
    @Path("getInfo{var:.*}")
    public SaResult getInfo2() {
        return SaResult.ok();
    }

    // 读url
    @POST
    @Path("getInfo_101")
    public SaResult getInfo_101() {
        return SaResult.data(SaHolder.getRequest().getUrl());
    }

    // 读Cookie
    @POST
    @Path("getInfo_102")
    public SaResult getInfo_102() {
        return SaResult.data(SaHolder.getRequest().getCookieValue("x-token"));
    }

    // 测试转发
    @POST
    @Path("getInfo_103")
    public SaResult getInfo_103() {
        SaHolder.getRequest().forward("/rt/getInfo_102");
        return SaResult.ok();
    }

    // 空接口
    @POST
    @Path("getInfo_200")
    public SaResult getInfo_200() {
        return SaResult.ok();
    }

    @POST
    @Path("getInfo_201")
    public SaResult getInfo_201() {
        return SaResult.ok();
    }

    @POST
    @Path("getInfo_202")
    public SaResult getInfo_202() {
        return SaResult.ok();
    }

    @POST
    @Path("login")
    public SaResult login(@QueryParam("id") long id) {
        StpUtil.login(id);
        return SaResult.ok().set("token", StpUtil.getTokenValue());
    }
}
