<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link href="${ctx}/css/dailog.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/jqui/ui.jqgrid.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.sha1.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script>
<script type="text/javascript" src="${ctx}/js/buildmanager/createBuild.js"></script>

<body>
<form id="buildForm">   
 	<input name="buildEntity.id" id="buildId" type="hidden" value="${buildEntity.id }" />
 	<input name="buildEntity.type" id="type" type="hidden" value="${buildEntity.type }" />
 	<div class="dailog">
	    <div class="dialog-configure" id="mainDiv">
		    <div class="add-build">
		        <table width="580" border="0" cellspacing="0" cellpadding="0">
				  <tr>
				    <td width="72" height="30" align="right" valign="middle"><strong>*</strong>构建名称：</td>
				    <td width="100" height="30" valign="middle"><input id="buildName" name="buildEntity.name" type="text" value="${buildEntity.name}"/></td>
				    <td width="50" align="right" valign="middle" height="30">描述：</td>
				    <td width="232" valign="middle" height="30" ><textarea name="buildEntity.description" maxlength="120" placeholder="最多输入120个字符" style="width:226px; height:22px; line-height:22px; border:1px solid #dadada; border-radius:5px; resize: none; padding:0px;">${buildEntity.description}</textarea></td>
				  </tr>
				</table>
			</div>
	        <div class="structure-code" id="addRepositoryDiv">
	        	<div class="add-build-title">
	        		<a onclick="addRepository();"><img src="${ctx}/imgs/structure-icon.png" width="22" height="22" /><strong style="font-size:14px; color:#333;">添加代码库</strong></a>
	        		<span><img src="${ctx}/imgs/build-tips.png" width="15" height="15" title="1）、当有多个git库时，代码目录之间不能存在目录包含                 &#13;2）、Dockerfile文件中的文件路径也是以代码目录开头的相对路径 "/></span>
	        	</div>
	        	<s:iterator value="buildEntity.repositoryInfo" status="st">
		            <div class="svn-code-main" style="border-radius:0 5px 5px 5px;" id="repositoryDiv<s:property value='#st.index'/>">
		                <input name="buildEntity.repositoryInfo[<s:property value='#st.index'/>].id" id="id<s:property value='#st.index'/>" type="hidden" value='<s:property value="id"/>' />
		                <input name="buildEntity.repositoryInfo[<s:property value='#st.index'/>].branchName" id="branchName<s:property value='#st.index'/>" type="hidden" value='<s:property value="branchName"/>' />
		                <input id="queryBranchesFlag<s:property value='#st.index'/>" type="hidden"/>
		                <input id="verifyRepositoryInfoFlag<s:property value='#st.index'/>" type="hidden"/>
		                <ul>
		                	<li style="width:450px;"><span style="margin-left:17px;"><strong>*</strong>git仓库：</span><input name="buildEntity.repositoryInfo[<s:property value='#st.index'/>].url" id="url<s:property value='#st.index'/>" type="text" value='<s:property value="url"/>' style="width:333px;" onblur="getRepositoryInfo(<s:property value='#st.index'/>);verifyRepositoryInfo(<s:property value='#st.index'/>)" onmouseover="toolTips(this);"/></li>
		                    <li style="width:250px;"><span style="width:90px;"><strong>*</strong>代码目录：</span><input name="buildEntity.repositoryInfo[<s:property value='#st.index'/>].storePath" id="storePath<s:property value='#st.index'/>" type="text" value='<s:property value="storePath"/>' style="width:120px;" placeholder="代码下载到此目录" onblur="checkCodeStorePath(<s:property value='#st.index'/>);"/></li>
		                    <li><span><strong>*</strong>用户名：</span><input name="buildEntity.repositoryInfo[<s:property value='#st.index'/>].name" id="name<s:property value='#st.index'/>" type="text" value='<s:property value="name"/>' onblur="verifyRepositoryInfo(<s:property value='#st.index'/>)"/></li>
		                    <li style="width:250px;"><span style="width:90px;"><strong>*</strong>分支名称：</span><select id="selectBranchName<s:property value='#st.index'/>" style="width:120px; margin-left:0px;"><option value="">请选择分支名称</option></select></li>
		                    <li style="width:200px;"><span><strong>*</strong>密码：</span><input name="buildEntity.repositoryInfo[<s:property value='#st.index'/>].password" id="password<s:property value='#st.index'/>" type="password" value='<s:property value="password"/>' onblur="verifyRepositoryInfo(<s:property value='#st.index'/>)" autocomplete="off"/></li>
		                    <s:if test="!#st.first">
		                    	<li style="width:68px;"><input name="" type="button" value="删除" onclick="deleteRepository(<s:property value='#st.index'/>)" style="width:55px; height:22px; float:left; background:#666666; border:none; color:#fff; border-radius:5px;" /></li>
		                    </s:if>
		                </ul>
		                <s:if test="#st.last">
		                	<input name="repositoryCount" id="repositoryCount" type="hidden" value="<s:property value='#st.count'/>"/>
		                </s:if>
		             </div>
	             </s:iterator>
	        </div>
	     
	        <table width="480" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td width="108" height="30" valign="middle" align="left"><strong>*</strong>Dockerfile文件：</td>
			    <td width="337" height="30" align="left" valign="middle"><input id="dockerFilePath" name="buildEntity.dockerFilePath" type="text" value="${buildEntity.dockerFilePath}" onmouseover="toolTips(this);" style="width:330px; border-radius:5px; border:1px solid #dadada; height:18px; margin-right:0px;" placeholder="代码目录/Dockerfile"/></td>
			    <td width="35" height="30" valign="middle" align="left"><span><img src="${ctx}/imgs/build-tips.png" width="15" height="15" title="1）、如果Dockerfile文件在工程目录下，则Dockerfile文件填：代码目录/Dockerfile&#13;2）、如果Dockerfile文件在工程目录下的子目录中，则Dockerfile文件填：代码目录/子目录/Dockerfile"/></span></td>
			  </tr>
			</table>
	        <div class="structure-step" id="executeCommandDiv">
	        	<div class="add-build-title">
	        		<a onclick="addExecuteCommand();"><img src="${ctx}/imgs/structure-icon.png" width="22" height="22" /><strong style="font-size:14px; color:#333;">添加构建步骤</strong></a>
	        		<span><img src="${ctx}/imgs/build-tips.png" width="15" height="15" title="执行命令写法：命令名称   参数   脚本文件相对路径&#13;1）、ant编译打包java工程： &#13;    如果代码目录填的是test_code，build.xml在工程根目录下，则执行命令为：ant  -f  test_code/build.xml &#13;2）、make编译C++工程：&#13;    如果代码目录填的是test_code，Makefile在工程根目录下，则执行命令为：make  -f  test_code/Makefile &#13;&#13;注：添加多个构建步骤时，构建步骤是有顺序的，依赖的要写在被依赖的后面。" /></span>
	        	</div>
	        	<s:iterator value="buildEntity.executeCommand" status="st">
			        <div class="step-main" style="border-radius:0 5px 5px 5px;" id="commandDiv<s:property value='#st.index'/>">
			            <div class="name70"><strong>*</strong>执行命令：</div><textarea name="buildEntity.executeCommand" id="command<s:property value='#st.index'/>"  placeholder="例如：ant  -f  test_code/build.xml"><s:property/></textarea>
			            <div class="structure-step-delete"><input name="" type="button" value="删除" onclick="deleteExecuteCommand(<s:property value='#st.index'/>);"/></div>
			        	<s:if test="#st.last">
		                	<input name="commandCount" id="commandCount" type="hidden" value="<s:property value='#st.count'/>"/>
		                </s:if>
			        </div>
	            </s:iterator>
	        </div>
	        
			<div class="bottom"><input name="" type="button" value="确定" onclick="saveBuild();"/></div> 

		</div>
	</div>
</form>
</body>