<#macro html_head title>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <#if title??>
        <title>${title} —— j-final</title>
    <#else >
        <title>j-final</title>
    </#if>
    <meta name="description" content="login">
    <meta name="keywords" content="index">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="icon" type="/image/png" href="/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
    <link rel="stylesheet" href="/css/amazeui.min.css" />
    <link rel="stylesheet" href="/css/admin.css">
    <link rel="stylesheet" href="/css/app.css">
    <link rel="stylesheet" charset="UTF-8" href="/css/amazeui.min.css"/>
    <link rel="stylesheet" charset="UTF-8" href="/css/admin.css">
    <link rel="stylesheet" charset="UTF-8" href="/css/app.css">
    <#nested >
</head>
</#macro>
<#macro html_body>
<body data-type="login">

<div class="am-g myapp-login">
    <#nested>
</div>
</#macro>
<#macro html_script>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/amazeui.min.js"></script>
    <script src="/js/app.js"></script>
    <#nested>
</body>
</#macro>
</html>