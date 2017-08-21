<#include '/WEB-INF/view/common/simple-layout.ftl'>
<@html_head '404'>
    <link rel="stylesheet" charset="UTF-8" href="/css/additional.css">
</@html_head>
<@html_body>
    <div class="myapp-login-logo-block  tpl-login-max">
        <div class="error-div">
            <img src="/img/404.png" alt="404" class="icon" width="400" height="260">
        </div>
        <div class="button-div">
            <button class="go-back">返回上一页</button>
            <button class="go-home">去首页</button>
        </div>
    </div>
</@html_body>
<@html_script>
    <script type="text/javascript" charset="UTF-8" src="/js/additional/error.js"></script>
</@html_script>