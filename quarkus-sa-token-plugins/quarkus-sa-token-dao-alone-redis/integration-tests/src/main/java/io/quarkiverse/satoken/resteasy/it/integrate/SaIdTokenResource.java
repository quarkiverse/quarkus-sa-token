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

import cn.dev33.satoken.id.SaIdUtil;
import cn.dev33.satoken.util.SaResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/id/")
@ApplicationScoped
public class SaIdTokenResource {

    // 获取信息
    @POST
    @Path("getInfo")
    public SaResult getInfo(@HeaderParam(SaIdUtil.ID_TOKEN) String token) {
        // 获取并校验id-token
        SaIdUtil.checkToken(token);
        // 返回信息
        return SaResult.data("info=zhangsan");
    }

    // 获取信息2
    @POST
    @Path("getInfo2")
    public SaResult getInfo2() {
        // 获取并校验id-token
        SaIdUtil.checkCurrentRequestToken();
        // 返回信息
        return SaResult.data("info=zhangsan2");
    }
}
