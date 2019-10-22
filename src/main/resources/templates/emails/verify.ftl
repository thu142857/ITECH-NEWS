<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Verify your account</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
    <!-- css for email template -->
    <#include "email_css.ftl" >
</head>
<body style="margin: 0; padding: 0;">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600" style="border-collapse: collapse;">
    <tr>
        <td bgcolor="#fff" align="center" style="padding: 40px 0 30px 0;">
            <img src="https://i.ibb.co/VT1Qs2N/logo.png"
                 alt="https://memorynotfound.com"
                 style="display: block;"/>
        </td>
    </tr>
    <tr>
        <td bgcolor="#eaeaea" style="padding: 40px 30px 40px 30px;">
            <p>Hi, ${user.username}. Please click the button below to verify your account.</p>
            <a id="btn-verification" href="${link}">Verify</a>
        </td>
    </tr>
    <tr>
        <td bgcolor="#777777" style="padding: 30px 30px 30px 30px;">
            <p style="color: #010707">itechnews website</p>
        </td>
    </tr>
</table>
</body>
</html>