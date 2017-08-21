<#include '/WEB-INF/view/common/simple-layout.ftl'>
<@html_head 'login'>

</@html_head>
<@html_body>
    <div class="myapp-login-logo-block  tpl-login-max">
        <div class="myapp-login-logo-text">
            <div class="myapp-login-logo-text">
                Amaze UI<span> Login</span> <i class="am-icon-skyatlas"></i>

            </div>
        </div>
        <div class="login-font">
            <i>Log In </i> or <span> Sign Up</span>
        </div>
        <div class="am-u-sm-10 login-am-center">
            <form class="am-form">
                <fieldset>
                    <div class="am-form-group">
                        <input type="email" class="" id="doc-ipt-email-1" placeholder="输入电子邮件">
                    </div>
                    <div class="am-form-group">
                        <input type="password" class="" id="doc-ipt-pwd-1" placeholder="设置个密码吧">
                    </div>
                    <p><button class="am-btn am-btn-default">登录</button></p>
                </fieldset>
            </form>
        </div>
    </div>
</@html_body>
<@html_script>

</@html_script>