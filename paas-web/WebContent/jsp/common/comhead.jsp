<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%-- <%@ taglib uri="/WEB-INF/tlds/paas.tld" prefix="paas"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!-- meta -->
<meta content='text/html; charset=UTF-8' http-equiv='Content-Type' />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<!-- css -->
<link href="${ctx}/paas/visuals/jqui/style.css" rel="stylesheet" type="text/css">
<link href="${ctx}/paas/visuals/ui.jqgrid.css" rel="stylesheet" type="text/css">
<link href="${ctx}/paas/visuals/style.css" rel="stylesheet" type="text/css">
<link href="${ctx}/paas/visuals/components.css" rel="stylesheet" type="text/css">
<!-- js-->
<script type="text/javascript" src="${ctx}/paas/libs/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/paas/libs/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/paas/libs/jqgrid/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/paas/libs/jqgrid/jquery.jqGrid.min.js"></script>
