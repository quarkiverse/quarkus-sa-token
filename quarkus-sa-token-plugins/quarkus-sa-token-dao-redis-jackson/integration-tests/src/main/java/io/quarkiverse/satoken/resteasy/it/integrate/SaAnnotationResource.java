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

import cn.dev33.satoken.annotation.SaCheckDisable;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaCheckSafe;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/at/")
@ApplicationScoped
public class SaAnnotationResource {

    // 登录
    @POST
    @Path("login")
    public SaResult login(@QueryParam("id") long id) {
        StpUtil.login(id);
        return SaResult.ok().set("token", StpUtil.getTokenValue());
    }

    // 登录校验
    @SaCheckLogin
    @POST
    @Path("checkLogin")
    public SaResult checkLogin() {
        return SaResult.ok();
    }

    // 角色校验
    @SaCheckRole("admin")
    @POST
    @Path("checkRole")
    public SaResult checkRole() {
        return SaResult.ok();
    }

    // 权限校验
    @SaCheckPermission("art-add")
    @POST
    @Path("checkPermission")
    public SaResult checkPermission() {
        return SaResult.ok();
    }

    // 权限校验 or 角色校验
    @SaCheckPermission(value = "art-add2", orRole = "admin")
    @POST
    @Path("checkPermission2")
    public SaResult checkPermission2() {
        return SaResult.ok();
    }

    // 开启二级认证
    @POST
    @Path("openSafe")
    public SaResult openSafe() {
        StpUtil.openSafe(120);
        return SaResult.ok();
    }

    // 二级认证校验
    @SaCheckSafe
    @POST
    @Path("checkSafe")
    public SaResult checkSafe() {
        return SaResult.ok();
    }

    // 封禁账号
    @POST
    @Path("disable")
    public SaResult disable(@QueryParam("id") long id) {
        StpUtil.disable(id, "comment", 200);
        return SaResult.ok();
    }

    // 服务封禁校验
    @SaCheckDisable("comment")
    @POST
    @Path("checkDisable")
    public SaResult checkDisable() {
        return SaResult.ok();
    }

    // 解封账号
    @POST
    @Path("untieDisable")
    public SaResult untieDisable(@QueryParam("id") long id) {
        StpUtil.untieDisable(id, "comment");
        return SaResult.ok();
    }

}
