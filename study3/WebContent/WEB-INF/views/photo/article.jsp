<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>

<link rel="stylesheet" href="<%=cp%>/res/jquery/css/smoothness/jquery-ui.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/bootstrap/css/bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/bootstrap/css/bootstrap-theme.min.css" type="text/css"/>

<link rel="stylesheet" href="<%=cp%>/res/css/style.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/layout/layout.css" type="text/css"/>
<style type="text/css">
.bbs-article .table {
    margin-top: 15px;
}
.bbs-article .table thead tr, .bbs-article .table tbody tr {
    height: 30px;
}
.bbs-article .table thead tr th, .bbs-article .table tbody tr td {
    font-weight: normal;
    border-top: none;
    border-bottom: none;
}
.bbs-article .table thead tr {
    border-top: #d5d5d5 solid 1px;
    border-bottom: #dfdfdf solid 1px;
} 
.bbs-article .table tbody tr {
    border-bottom: #dfdfdf solid 1px;
}
.bbs-article .table i {
    background: #424951;
    display: inline-block;
    margin: 0 7px 0 7px;
    position: relative;
    top: 2px;
    width: 1px;
    height: 13px;    
}
</style>

<script type="text/javascript" src="<%=cp%>/res/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
function updatePhoto(num) {
	<c:if test="${sessionScope.member.userId==dto.userId}">
	     var url="<%=cp%>/photo/update.do?num="+num+"&page=${page}";
	     location.href=url;
	</c:if>
}

function deletePhoto(num) {
	<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
         if(confirm("게시물을 삭제 하시겠습니까 ?")) {
        	 var url="<%=cp%>/photo/delete.do?num="+num+"&page=${page}";
        	 location.href=url;
         }	
	</c:if>
}
</script>
</head>
<body>

<div>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container" role="main">
    <div class="bodyFrame col-sm-10"  style="float:none; margin-left: auto; margin-right: auto;">

	    <div class="body-title">
	          <h3><span class="glyphicon glyphicon-picture"></span> 포토겔러리 </h3>
	    </div>
	    
	    <div class="alert alert-info">
	        <i class="glyphicon glyphicon-info-sign"></i> 추억의 포토 겔러리를 회원과 공유할 수 있는 공간입니다.
	    </div>
	    
	    <div class="table-responsive" style="clear: both;">
	        <div class="bbs-article">
	            <table class="table">
	                 <thead>
	                     <tr>
	                         <th colspan="2" style="text-align: center;">
	                                 ${dto.subject}
	                         </th>
	                     </tr>
	                <thead>
	                 <tbody>
	                     <tr>
	                         <td style="text-align: left;">
	                             이름 : ${dto.userName}
	                         </td>
	                         <td style="text-align: right;">
	                          ${dto.created}
	                         </td>
	                     </tr>
                         <tr style="border-bottom:none;">
                             <td colspan="2">
                                 <img src="<%=cp%>/uploads/photo/${dto.imageFilename}" style="max-width:100%; height:auto; resize:both;">
                             </td>
                         </tr>
	                     <tr>
	                         <td colspan="2" style="min-height: 30px;">
	                              ${dto.content}
	                         </td>
	                     </tr>
	                </tbody>
	                <tfoot>
	                	<tr>
	                		<td>
		                        <c:if test="${sessionScope.member.userId==dto.userId}">		                		
	                		        <button type="button" class="btn btn-default btn-sm wbtn" onclick="updatePhoto(${dto.num});">수정</button>
	                		    </c:if>
		                        <c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">    
	                		        <button type="button" class="btn btn-default btn-sm wbtn" onclick="deletePhoto(${dto.num});">삭제</button>
	                		    </c:if>
	                		</td>
	                		<td align="right">
	                		    <button type="button" class="btn btn-default btn-sm wbtn"
	                		                onclick="javascript:location.href='<%=cp%>/photo/list.do?page=${page}';"> 목록으로 </button>
	                		</td>
	                	</tr>
	                </tfoot>
	            </table>
	       </div>
	   </div>

    </div>
</div>

<div>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/res/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/res/jquery/js/jquery.ui.datepicker-ko.js"></script>
<script type="text/javascript" src="<%=cp%>/res/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>