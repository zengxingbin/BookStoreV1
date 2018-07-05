<%@page language="java" contentType="text/html;charset=gb2312"%>
<%@page import="com.yeepay.*"%>

<html>
	<head>
		<title>To YeePay Page</title>
	</head>
	<body>
		<form name="yeepay" action='${yeePayReq.nodeAuthorizationURL }' method='POST' target="_blank">
			<input type='hidden' name='p0_Cmd'   value='${yeePayReq.p0_Cmd }'>
			<input type='hidden' name='p1_MerId' value='${yeePayReq.p1_MerId }'>
			<input type='hidden' name='p2_Order' value='${yeePayReq.p2_Order }'>
			<input type='hidden' name='p3_Amt'   value='${yeePayReq.p3_Amt }'>
			<input type='hidden' name='p4_Cur'   value='${yeePayReq.p4_Cur }'>
			<input type='hidden' name='p5_Pid'   value='${yeePayReq.p5_Pid }'>
			<input type='hidden' name='p6_Pcat'  value='${yeePayReq.p6_Pcat }'>
			<input type='hidden' name='p7_Pdesc' value='${yeePayReq.p7_Pdesc }'>
			<input type='hidden' name='p8_Url'   value='${yeePayReq.p8_Url }'>
			<input type='hidden' name='p9_SAF'   value='${yeePayReq.p9_SAF }'>
			<input type='hidden' name='pa_MP'    value='${yeePayReq.pa_MP }'>
			<input type='hidden' name='pd_FrpId' value='${yeePayReq.pd_FrpId }'>
			<input type="hidden" name="pr_NeedResponse"  value="${yeePayReq.pr_NeedResponse }">
			<input type='hidden' name='hmac'     value='${yeePayReq.hmac }'>
		</form>
	</body>
	
	<script type="text/javascript">
		document.forms[0].submit();
	</script>
</html>
