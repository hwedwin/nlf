package nc.liat6.frame.web.tags.script;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * JS∂Œ±Í«©
 * @author 6tail
 *
 */
public class JavaScriptBeginTag extends TagSupport{

	private static final long serialVersionUID = 1617893097279087282L;
	
	@Override
	public int doStartTag() throws JspException{
		JspWriter out = pageContext.getOut();
		try{
			out.write("<textarea style=\"display:none\" name=\"scriptBegin\">");
		}catch(IOException e){
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException{
		JspWriter out = pageContext.getOut();
		try{
			out.write("</textarea>");
		}catch(IOException e){
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

}
