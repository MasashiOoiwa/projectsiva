<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/plain"%>
　
<%
　　　// キャッシュしないようにHTTPヘッダを出力
　　　response.setHeader("progma","no-cache");
　　　response.setHeader("Cache-Control","no-cache");
　　　
　　　out.print( "result[id]" );
%>