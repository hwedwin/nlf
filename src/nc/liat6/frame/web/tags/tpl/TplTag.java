package nc.liat6.frame.web.tags.tpl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * TPLÄ£°å¶Î±êÇ©
 * @author 6tail
 *
 */
public class TplTag extends TagSupport{

	private static final long serialVersionUID = 4302360543727474068L;
	
	private String id;
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	@Override
	public int doStartTag() throws JspException{
		JspWriter out = pageContext.getOut();
		try{
			out.write("<textarea id=\"TPL_"+id+"\" style=\"display:none\">");
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
