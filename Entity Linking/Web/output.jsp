<%@page import="TitleMapper.BinarySearchID"%>
<%@page import="TitleMapper.BinarySearchTitle"%>
<%@page import="Query.SentenceParser"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@page import="Query.Result"%>
<%@page import="Query.Anchor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>OutputTag</title>
</head>
<body>
<%
	SentenceParser parser=(SentenceParser)session.getAttribute("inputText");
	Result result=(Result)session.getAttribute("finalresults");
	if(parser==null||result==null)
	{
		response.sendRedirect("index.jsp");
	}
	BinarySearchID idSearcher=(BinarySearchID)session.getServletContext().getAttribute("searcher");
	out.println(parser.st);
	out.println("<br>");
	out.println("<hr>");
	out.println("<br>");
	
	session.removeAttribute("inputText");
	session.removeAttribute("finalresults");
	int start=0;
	for(int i=0;i<result.lists.size();i++)
	{
		Anchor anchor=result.lists.get(i);
		String title=idSearcher.Search(anchor.finalPage);

		int currentStart=anchor.globalStart;
		int currentEnd=anchor.globalEnd;
		if(start<currentStart)
			out.print(parser.st.subSequence(start,currentStart));
		//
		if(title!=null&&!anchor.isMarked)
		{
			String val=	(" <a href=\"http://en.wikipedia.org/wiki/"+title+"\">"+parser.st.subSequence(currentStart,currentEnd)+"</a>");
			out.print(val);
			start=currentEnd;
		}
		else
		{
			out.print(parser.st.subSequence(currentStart,currentEnd));
			start=currentEnd;
		}
	}
	if(start<parser.st.length())
		out.print(parser.st.subSequence(start,parser.st.length()));
	
	out.flush();
%>
</body>
</html>